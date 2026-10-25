import argparse
import json
import re
from pathlib import Path
from extract_legacy import extract, strip_comments
from asset_references import asset_references, asset_path

MOD = 'kuayue'


def remap(value):
    if isinstance(value, dict):
        return {key.replace('kuayue:', MOD + ':').replace('.kuayue.', '.' + MOD + '.'): remap(item) for key, item in value.items()}
    if isinstance(value, list):
        return [remap(item) for item in value]
    if isinstance(value, str):
        if value.startswith('22:'): value = 'kuayue:' + value.split(':', 1)[1]
        if value == 'minecraft:texture': value = 'minecraft:block/andesite'
        value = value.replace('kuayue:block/overhead_line/block/', 'kuayue:block/overhead_line/')
        value = value.replace('kuayue:', MOD + ':').replace('.kuayue.', '.' + MOD + '.')
        if value == 'forge:obj': value = 'neoforge:obj'
        return value.replace('//', '/') if value.startswith(MOD + ':') else value
    return value


def merge(left, right):
    if isinstance(left, dict) and isinstance(right, dict):
        return {key: merge(left[key], value) if key in left else value for key, value in right.items()} | {key: value for key, value in left.items() if key not in right}
    if isinstance(left, list) and isinstance(right, list):
        return left + right
    return right


def load_modules(source):
    base = source / 'resources/generated'
    modules = {file.parent for file in base.rglob('module.yml')}
    if not modules:
        base = source / 'modules'
        modules = {file.parent for file in base.glob('*/resources/**/module.toml')}
    assets, recipes, tags = {}, {}, {}
    for module in sorted(modules):
        for file in sorted(module.rglob('*')):
            if not file.is_file() or file.name in {'module.yml', 'module.toml'}:
                continue
            if any(parent in modules and parent != module for parent in file.parents if parent != base and parent != source):
                continue
            rel = file.relative_to(module).as_posix()
            if not rel.startswith(('assets/', 'data/')):
                rel = 'assets/kuayue/' + rel
            if '/recipes/' in rel and file.suffix == '.json':
                recipes[rel] = json.loads(file.read_text(encoding='utf-8-sig'))
            elif rel.startswith('data/kuayue/tags/blocks/') and file.suffix == '.json':
                data = json.loads(file.read_text(encoding='utf-8-sig'))
                name = rel.removeprefix('data/kuayue/tags/blocks/').removesuffix('.json')
                tags[name] = merge(tags[name], data) if name in tags else data
            elif rel.startswith('assets/kuayue/'):
                if file.suffix == '.json':
                    data = json.loads(file.read_text(encoding='utf-8-sig'))
                    assets[rel] = merge(assets[rel], data) if rel in assets else data
                else:
                    assets[rel] = file.read_bytes()
    return assets, recipes, tags


def strings(value):
    if isinstance(value, dict):
        for item in value.values():
            yield from strings(item)
    elif isinstance(value, list):
        for item in value:
            yield from strings(item)
    elif isinstance(value, str):
        yield value


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--source', type=Path, required=True)
    parser.add_argument('--official-source', type=Path)
    parser.add_argument('--prune', action='store_true')
    args = parser.parse_args()
    records = extract(args.source)
    ids = {r['id'] for r in records}
    bogey_ids = {r['id'] for r in json.loads(Path('docs/legacy-bogeys.json').read_text(encoding='utf-8'))}
    ids.update(bogey_ids)
    materials = set(json.loads(Path('src/porting/materials.json').read_text(encoding='utf-8')))
    assets, recipes, tags = load_modules(args.source)
    official_assets = {}
    if args.official_source:
        official_assets, official_recipes, official_tags = load_modules(args.official_source)
        assets.update(official_assets)
        recipes.update(official_recipes)
        tags.update(official_tags)
    assets = {key: remap(value) if isinstance(value, dict) else value for key, value in assets.items()}
    wall_states = assets['assets/kuayue/blockstates/platform_wall.json']['variants']
    for variant in list(wall_states):
        if variant in {'facing=up', 'facing=down'}:
            del wall_states[variant]
    assets['assets/kuayue/models/block/track/standard_gauge_track/track.json'] = {'parent': 'kuayue:block/track/standard/obj_track'}
    assets['assets/kuayue/models/block/empty.json'] = {'textures': {'particle': 'minecraft:block/andesite'}, 'elements': []}
    assets['assets/kuayue/textures/block/track/standard_gauge_track/track.png'] = assets['assets/kuayue/textures/block/track/standard_gauge_track/standard_track.png']
    for side in ['left', 'right']:
        old = f'assets/kuayue/models/block/df11g/door/frame_door_equip_df11g_{side}.json'
        new = f'assets/kuayue/models/block/df11g/door/frame_door_cabin_df11g_{side}.json'
        assets[new] = json.loads(json.dumps(assets[old]).replace('df11g_equip_door', 'df11g_cabin_door'))
    for number in range(1, 4):
        model = assets[f'assets/kuayue/models/block/deco/vacuum_toilet_{number}.json']
        model['textures']['particle'] = f'kuayue:block/vacuum_toilet_{number}'
    output = Path('src/main/resources/assets') / MOD
    selected, missing_states = {}, []
    for id in sorted(ids | materials):
        prefix = 'assets/kuayue/'
        for kind in ['blockstates', 'models/item']:
            if kind == 'models/item' and id in bogey_ids:
                continue
            candidates = {key: value for key, value in assets.items() if key.startswith(prefix + kind + '/') and key.endswith('/' + id + '.json')}
            direct = prefix + kind + '/' + id + '.json'
            if not candidates:
                if kind == 'blockstates' and id in ids:
                    missing_states.append(id)
                continue
            chosen = direct if direct in candidates else sorted(candidates)[0]
            selected[direct] = candidates[chosen]
    # Partial models are referenced from Java rather than from blockstate JSON.
    model_roots = set()
    model_roots.add('assets/kuayue/models/block/empty.json')
    for record in records:
        definition = record['definition']
        for path in re.findall(r'"([a-z0-9_/-]+)"', definition):
            candidate = 'assets/kuayue/models/block/' + path + '.json'
            if candidate in assets:
                model_roots.add(candidate)
    model_roots.update(key for key in assets if '/models/block/bogey/' in key)
    model_roots.update(key for key in assets if '/models/block/track/' in key)
    model_roots.update(key for key in assets if '/models/block/door/platform_door/' in key)
    model_roots.update(key for key in assets if '/models/block/carriage/carriage_marshalled_cr200j/head/' in key)
    queue = list(selected) + sorted(model_roots)
    for root in model_roots:
        if root in assets:
            selected[root] = assets[root]
    missing_refs = set()
    while queue:
        key = queue.pop()
        value = selected.get(key)
        if isinstance(value, bytes) and key.endswith(('.obj', '.mtl')):
            text = value.decode('utf-8-sig')
            if key.endswith('.obj'):
                refs = [str(Path(key).parent / match.strip()).replace('\\', '/') for match in re.findall(r'^mtllib\s+(.+)$', text, re.M)]
            else:
                refs = ['assets/kuayue/textures/' + match.strip() + '.png' for match in re.findall(r'^map_Kd\s+kuayue:(.+)$', text, re.M)]
            for ref in refs:
                if ref in assets and ref not in selected:
                    selected[ref] = assets[ref]
                    queue.append(ref)
                elif ref not in assets:
                    missing_refs.add(ref)
            continue
        if not isinstance(value, dict):
            continue
        for kind, ref in asset_references(value):
            if not ref.startswith('kuayue:'):
                continue
            candidate = asset_path(kind, ref)
            found = candidate if candidate in assets else None
            if found:
                if found not in selected:
                    selected[found] = assets[found]
                    queue.append(found)
                metadata = found + '.mcmeta'
                if metadata in assets:
                    selected[metadata] = assets[metadata]
            else:
                missing_refs.add(ref)
    for key, value in assets.items():
        if '/lang/' in key:
            language = {k: v for k, v in value.items() if not k.startswith(('block.kuayue.', 'item.kuayue.')) or k.split('.', 2)[2] in ids | materials}
            target = Path('src/porting/languages') / key.split('/')[-1]
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_text(json.dumps(remap(language), ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    for key, value in selected.items():
        target = output / key.removeprefix('assets/kuayue/')
        target.parent.mkdir(parents=True, exist_ok=True)
        if isinstance(value, bytes):
            if key.endswith(('.obj', '.mtl')):
                target.write_text(remap(value.decode('utf-8-sig')), encoding='utf-8')
            else:
                target.write_bytes(value)
        else:
            target.write_text(json.dumps(remap(value), ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    if args.prune:
        expected = {(output / key.removeprefix('assets/kuayue/')).resolve() for key in selected}
        for file in output.rglob('*'):
            if file.is_file() and file.resolve() not in expected:
                if not file.resolve().is_relative_to(output.resolve()):
                    raise ValueError('Output path escapes imported assets: ' + str(file))
                file.unlink()
    recipe_source = Path('src/porting/legacy-recipes.json')
    recipe_source.parent.mkdir(parents=True, exist_ok=True)
    recipe_source.write_text(json.dumps(remap(recipes), ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    Path('src/porting/block-tags.json').write_text(json.dumps(remap(tags), ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    report = {'blocks': len(ids), 'asset_files': len(selected), 'missing_blockstates': missing_states, 'missing_asset_references': sorted(missing_refs)}
    if args.official_source:
        report['primarySource'] = 'KuaYue 1.21.1'
        report['officialAssetFiles'] = len(set(selected) & official_assets.keys())
        report['fallbackAssetFiles'] = len(set(selected) - official_assets.keys())
    Path('docs/resource-import.json').write_text(json.dumps(report, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    print(json.dumps(report, ensure_ascii=False, indent=2))


if __name__ == '__main__':
    main()
