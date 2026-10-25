import json
import re
import argparse
import zipfile
import zlib
from pathlib import Path
from generate_data import MOD, PREFIX, output_ids
from asset_references import asset_references, asset_path, verify_png


def references(value):
    if isinstance(value, dict):
        for item in value.values():
            yield from references(item)
    elif isinstance(value, list):
        for item in value:
            yield from references(item)
    elif isinstance(value, str):
        yield value


def dependencies(recipe):
    transitional = recipe.get('transitional_item', {}).get('id')
    def ingredient(value):
        if isinstance(value, list):
            return [choice for item in value for choice in ingredient(item)]
        if 'item' in value:
            return [value['item']]
        if 'tag' in value:
            return ['#' + value['tag']]
        return ['fluid:' + value.get('fluid', value.get('fluid_tag', 'external'))]
    values = list(recipe.get('key', {}).values()) + recipe.get('ingredients', [])
    if 'ingredient' in recipe:
        values.append(recipe['ingredient'])
    groups = [ingredient(value) for value in values]
    for step in recipe.get('sequence', []):
        groups.extend(group for group in dependencies(step) if transitional not in group)
    return groups


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--jar', type=Path)
    args = parser.parse_args()
    roots = [Path('src/main/resources'), Path('src/generated/resources')]
    assets = {}
    errors = []
    for root in roots:
        for file in (root / 'assets' / MOD).rglob('*'):
            if file.is_file():
                key = file.relative_to(root).as_posix()
                if key in assets:
                    errors.append('Duplicate asset: ' + key)
                assets[key] = file
    for key, file in assets.items():
        if file.suffix == '.json' and '/lang/' not in key:
            value = json.loads(file.read_text(encoding='utf-8-sig'))
            for kind, ref in asset_references(value):
                if ref.startswith('kuayueneo21:') or ref == 'forge:obj':
                    errors.append('Legacy reference in ' + key + ': ' + ref)
                if not ref.startswith(PREFIX):
                    continue
                if asset_path(kind, ref) not in assets:
                    errors.append('Missing ' + kind + ' in ' + key + ': ' + ref)
        elif file.suffix == '.png':
            try:
                verify_png(file.read_bytes())
            except (ValueError, zlib.error) as error:
                errors.append('Invalid image ' + key + ': ' + str(error))
        elif file.suffix in ['.obj', '.mtl']:
            text = file.read_text(encoding='utf-8-sig')
            if 'kuayueneo21:' in text:
                errors.append('Legacy OBJ/MTL namespace: ' + key)
            if file.suffix == '.obj':
                for material in re.findall(r'^mtllib\s+([^\r\n]+)', text, re.M):
                    if (str(Path(key).parent / material.strip()).replace('\\', '/')) not in assets:
                        errors.append('Missing material library: ' + key + ' -> ' + material)
            else:
                for texture in re.findall(r'^map_Kd\s+' + PREFIX + r'([^\r\n]+)', text, re.M):
                    if 'assets/' + MOD + '/textures/' + texture.strip() + '.png' not in assets:
                        errors.append('Missing OBJ texture: ' + key + ' -> ' + texture)
    blocks = json.loads(Path('docs/legacy-blocks.json').read_text(encoding='utf-8'))
    bogeys = json.loads(Path('docs/legacy-bogeys.json').read_text(encoding='utf-8'))
    materials = json.loads(Path('src/porting/materials.json').read_text(encoding='utf-8'))
    targets = {PREFIX + entry['id'] for entry in blocks + bogeys}
    items = targets | {PREFIX + id for id in materials}
    for item in sorted(items):
        id = item.removeprefix(PREFIX)
        if 'assets/' + MOD + '/models/item/' + id + '.json' not in assets:
            errors.append('Missing item model: ' + item)
    for item in sorted(targets):
        if 'assets/' + MOD + '/blockstates/' + item.removeprefix(PREFIX) + '.json' not in assets:
            errors.append('Missing blockstate: ' + item)
    recipes = {}
    for file in Path('src/generated/resources/data').rglob('recipe/*.json'):
        recipes[file.as_posix()] = json.loads(file.read_text(encoding='utf-8'))
    for file in Path('src/generated/resources/data').rglob('*.json'):
        if '/recipe/' in file.as_posix():
            recipes[file.as_posix()] = json.loads(file.read_text(encoding='utf-8'))
    reachable = set()
    def available(item):
        if item.startswith('#' + PREFIX):
            return False
        return not item.startswith(PREFIX) or item in reachable
    changed = True
    while changed:
        changed = False
        for recipe in recipes.values():
            if all(any(available(choice) for choice in group) for group in dependencies(recipe)):
                outputs = output_ids(recipe)
                if not outputs <= reachable:
                    reachable |= outputs
                    changed = True
    for target in sorted(targets - reachable):
        errors.append('Unreachable block: ' + target)
    for path, recipe in recipes.items():
        for group in dependencies(recipe):
            for item in group:
                if item.startswith(PREFIX) and item not in items:
                    errors.append('Unregistered ingredient in ' + path + ': ' + item)
        for value in references(recipe):
            if any(word in value for word in ['blueprint_deploy', 'blueprint_group', 'hxd3d_pantograph', 'overhead_line_wire']):
                errors.append('Excluded recipe dependency: ' + path + ': ' + value)
    transitional = {recipe['transitional_item']['id'] for recipe in recipes.values() if 'transitional_item' in recipe}
    report = {'obtainableBlocks': len(targets), 'materials': len(materials), 'assetFiles': len(assets),
              'recipes': len(recipes), 'reachableBlocks': len(targets & reachable),
              'verifiedPngTextures': sum(file.suffix == '.png' for file in assets.values()),
              'unreachableMaterials': sorted({PREFIX + id for id in materials} - reachable - transitional),
              'failures': sorted(set(errors))}
    if args.jar:
        with zipfile.ZipFile(args.jar) as jar:
            entries = set(jar.namelist())
            for asset in assets:
                if asset not in entries:
                    errors.append('Asset missing from JAR: ' + asset)
            if any('.cache/' in entry for entry in entries):
                errors.append('Datagen cache was packaged in the JAR')
            recipe_count = sum('/recipe/' in entry and entry.endswith('.json') for entry in entries)
            loot_count = sum('/loot_table/blocks/' in entry and entry.endswith('.json') for entry in entries)
            if recipe_count != len(recipes) or loot_count != len(targets):
                errors.append('Packaged recipe/loot counts differ from the source catalogs')
            for path in ['META-INF/licenses/NeoKuayue-LICENSE.txt', 'META-INF/licenses/NeoKuayue-LICENSE-RESOURCECS.txt', 'porting/materials.json']:
                if path not in entries:
                    errors.append('Required packaged file is missing: ' + path)
            report['jar'] = str(args.jar)
            report['packagedRecipes'] = recipe_count
            report['packagedLootTables'] = loot_count
            report['failures'] = sorted(set(errors))
    destination = Path('build/reports/static-port-audit.json')
    destination.parent.mkdir(parents=True, exist_ok=True)
    destination.write_text(json.dumps(report, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    print(json.dumps(report, ensure_ascii=False, indent=2))
    if errors:
        raise SystemExit(1)


if __name__ == '__main__':
    main()
