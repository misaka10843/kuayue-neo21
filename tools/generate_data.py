import copy
import json
from collections import defaultdict
from pathlib import Path

MOD = 'kuayue'
PREFIX = MOD + ':'
GENERATED = Path('src/generated/resources')


def output_ids(recipe):
    values = recipe.get('results', [recipe.get('result')])
    return {value if isinstance(value, str) else value.get('id', value.get('item'))
            for value in values if isinstance(value, (str, dict))} - {None}


def ingredient_ids(value):
    if isinstance(value, list):
        for item in value:
            yield from ingredient_ids(item)
    elif isinstance(value, dict):
        if 'item' in value:
            yield value['item']
        for key, item in value.items():
            if key not in ('result', 'results', 'transitional_item', 'item'):
                yield from ingredient_ids(item)


def modernize(recipe):
    recipe = copy.deepcopy(recipe)
    names = {'transitionalItem': 'transitional_item', 'keepHeldItem': 'keep_held_item',
             'acceptMirrored': 'accept_mirrored', 'processingTime': 'processing_time',
             'showNotification': 'show_notification', 'heatRequirement': 'heat_requirement'}
    for old, new in names.items():
        if old in recipe:
            recipe[new] = recipe.pop(old)
    for key in ['node', 'paste_node_to_result', 'conditions']:
        recipe.pop(key, None)
    if 'sequence' in recipe:
        recipe['sequence'] = [modernize(step) for step in recipe['sequence'] if not step['type'].endswith(':blueprint_deploy')]
    for key in ['result', 'transitional_item']:
        if key not in recipe:
            continue
        result = recipe[key]
        if isinstance(result, str):
            recipe[key] = {'id': result, 'count': recipe.pop('count', 1)}
        elif isinstance(result, dict) and 'item' in result:
            result['id'] = result.pop('item')
    for result in recipe.get('results', []):
        if 'item' in result:
            result['id'] = result.pop('item')
    if 'sequence' in recipe:
        transitional = recipe['transitional_item']['id']
        for step in recipe['sequence']:
            if not step.get('ingredients') or not step.get('results'):
                raise ValueError('Invalid assembly step: ' + str(step))
            step['ingredients'][0] = {'item': transitional}
            step['results'][0] = {'id': transitional}
    def tags(value):
        if isinstance(value, dict):
            if 'fluidTag' in value:
                value['type'] = 'neoforge:tag'
                value['tag'] = value.pop('fluidTag').replace('forge:', 'c:')
            elif 'fluid' in value:
                value['type'] = 'neoforge:single'
            if value.get('nbt') == {}:
                value.pop('nbt')
            for key, item in list(value.items()):
                if key == 'tag' and item.startswith('forge:'):
                    value[key] = item.replace('forge:', 'c:')
                else:
                    tags(item)
        elif isinstance(value, list):
            for item in value:
                tags(item)
    tags(recipe)
    return recipe


def save(path, value):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')


def shaped(result, pattern, key, count=1):
    return {'type': 'minecraft:crafting_shaped', 'category': 'misc', 'pattern': pattern,
            'key': {symbol: {'item': item} for symbol, item in key.items()},
            'result': {'id': PREFIX + result, 'count': count}}


def main():
    blocks = json.loads(Path('docs/legacy-blocks.json').read_text(encoding='utf-8'))
    bogeys = json.loads(Path('docs/legacy-bogeys.json').read_text(encoding='utf-8'))
    target = {PREFIX + record['id'] for record in blocks + bogeys}
    old = json.loads(Path('src/porting/legacy-recipes.json').read_text(encoding='utf-8'))
    recipes, by_output = {}, defaultdict(list)
    for path, recipe in sorted(old.items()):
        if recipe['type'].endswith(':blueprint_deploy'):
            continue
        recipe = modernize(recipe)
        if 'sequence' in recipe and not recipe['sequence']:
            continue
        if any('blueprint' in value or 'fluid' in value for value in ingredient_ids(recipe)):
            continue
        name = path.split('/recipes/', 1)[1].removesuffix('.json')
        recipes[name] = recipe
        for output in output_ids(recipe):
            by_output[output].append(name)
    pending, needed, chosen = list(target), set(target), set()
    while pending:
        item = pending.pop()
        for name in by_output[item]:
            if name in chosen:
                continue
            chosen.add(name)
            for ingredient in ingredient_ids(recipes[name]):
                if ingredient.startswith(PREFIX) and ingredient not in needed:
                    needed.add(ingredient)
                    pending.append(ingredient)
    selected = {name: recipes[name] for name in sorted(chosen)}
    materials = needed - target
    supplied = set().union(*(output_ids(r) for r in selected.values())) if selected else set()
    missing = target - supplied
    blanks = {
        'vehicle_panel_blank': shaped('vehicle_panel_blank', ['III', 'III'], {'I': 'create:iron_sheet'}, 8),
        'vehicle_floor_blank': shaped('vehicle_floor_blank', ['III', 'WWW'], {'I': 'create:iron_sheet', 'W': 'minecraft:oak_planks'}, 8),
        'vehicle_roof_blank': shaped('vehicle_roof_blank', [' I ', 'III'], {'I': 'create:iron_sheet'}, 6),
        'vehicle_interior_blank': shaped('vehicle_interior_blank', ['WW', 'II'], {'W': 'minecraft:white_wool', 'I': 'create:iron_sheet'}, 4),
        'vehicle_bogey_blank': shaped('vehicle_bogey_blank', ['ISI', 'RCR', 'ISI'], {'I': 'create:iron_sheet', 'S': 'create:shaft', 'R': 'create:railway_casing', 'C': 'create:andesite_alloy'}, 2),
        'head_cr200j_circuit': shaped('head_cr200j_circuit', [' R ', 'CPC', ' R '], {'R': 'minecraft:redstone', 'C': 'create:copper_sheet', 'P': 'create:precision_mechanism'}),
        'head_cr200j_mould': shaped('head_cr200j_mould', ['III', ' B ', 'III'], {'I': 'create:iron_sheet', 'B': 'create:brass_ingot'}),
    }
    supplements = []
    for record in blocks + bogeys:
        item = PREFIX + record['id']
        if item not in missing:
            continue
        kind = record.get('type', 'Bogey')
        if 'Bogey' in kind:
            blank = 'vehicle_bogey_blank'
        elif record.get('family') == 'AllDecoBlocks' and any(word in kind for word in ['Seat', 'Shelf', 'Table', 'Boiler', 'Light']):
            blank = 'vehicle_interior_blank'
        elif 'carport' in record['id'] or 'roof' in record['id']:
            blank = 'vehicle_roof_blank'
        elif 'floor' in record['id']:
            blank = 'vehicle_floor_blank'
        else:
            blank = 'vehicle_panel_blank'
        materials.add(PREFIX + blank)
        selected['finishing/' + record['id']] = {'type': 'minecraft:stonecutting', 'ingredient': {'item': PREFIX + blank}, 'result': {'id': item, 'count': 1}}
        supplements.append({'output': item, 'blank': PREFIX + blank})
    transitional = {recipe['transitional_item']['id'] for recipe in selected.values() if 'transitional_item' in recipe}
    missing_materials = []
    for item in sorted(materials):
        id = item.removeprefix(PREFIX)
        if id in blanks:
            selected['materials/' + id] = blanks[id]
        elif item not in supplied and item not in transitional:
            missing_materials.append(item)
    if missing_materials:
        raise ValueError('Missing material production: ' + ', '.join(missing_materials))
    unique_recipes = {}
    fingerprints = set()
    for name in sorted(selected, key=lambda value: (len(Path(value).parts), value)):
        fingerprint = json.dumps(selected[name], sort_keys=True)
        if fingerprint in fingerprints:
            continue
        fingerprints.add(fingerprint)
        unique_recipes[name] = selected[name]
    selected = unique_recipes
    recipe_root = GENERATED / 'data' / MOD / 'recipe'
    expected_recipes = {(recipe_root / (name + '.json')).resolve() for name in selected}
    for path in recipe_root.rglob('*.json'):
        if path.resolve() not in expected_recipes:
            if not path.resolve().is_relative_to(recipe_root.resolve()):
                raise ValueError('Generated recipe escapes output directory: ' + str(path))
            path.unlink()
    for name, recipe in selected.items():
        save(GENERATED / 'data' / MOD / 'recipe' / (name + '.json'), recipe)
    for record in blocks + bogeys:
        id = record['id']
        save(GENERATED / 'data' / MOD / 'loot_table/blocks' / (id + '.json'), {
            'type': 'minecraft:block', 'pools': [{'rolls': 1, 'entries': [{'type': 'minecraft:item', 'name': PREFIX + id}],
                                                'conditions': [{'condition': 'minecraft:survives_explosion'}]}]})
    for record in bogeys:
        id = record['id']
        if not (Path('src/main/resources/assets') / MOD / 'models/item' / (id + '.json')).exists():
            save(GENERATED / 'assets' / MOD / 'models/item' / (id + '.json'), {
                'parent': 'minecraft:builtin/entity', 'gui_light': 'front',
                'display': {'gui': {'rotation': [30, 45, 0], 'translation': [0, -2, 0], 'scale': [0.25, 0.25, 0.25]},
                            'ground': {'scale': [0.2, 0.2, 0.2]},
                            'fixed': {'scale': [0.2, 0.2, 0.2]},
                            'firstperson_righthand': {'scale': [0.25, 0.25, 0.25]},
                            'thirdperson_righthand': {'scale': [0.2, 0.2, 0.2]}}})
    save(GENERATED / 'data/minecraft/tags/block/mineable/pickaxe.json', {'replace': False, 'values': sorted(target)})
    save(GENERATED / 'data/minecraft/tags/block/needs_stone_tool.json', {'replace': False, 'values': sorted(target)})
    block_tags = json.loads(Path('src/porting/block-tags.json').read_text(encoding='utf-8'))
    extension_ids = {'company_panel_block': 'company_panel_block', 'company_door_block': 'company_panel_door',
                     'company_sliding_door': 'company_panel_sliding_door', 'company_floor': 'company_panel_floor', 'company_carport': 'company_panel_carport'}
    for name, tag in block_tags.items():
        values = []
        for value in tag.get('values', []):
            id = value if isinstance(value, str) else value['id']
            if id.startswith(PREFIX) and id.removeprefix(PREFIX) in extension_ids:
                id = PREFIX + extension_ids[id.removeprefix(PREFIX)]
            if id in target or id in {PREFIX + value for value in extension_ids.values()} or id.startswith('#' + PREFIX) and id.removeprefix('#' + PREFIX) in block_tags:
                values.append(id)
        save(GENERATED / 'data' / MOD / 'tags/block' / (name + '.json'), {'replace': False, 'values': sorted(set(values))})
    save(Path('src/porting/materials.json'), sorted(item.removeprefix(PREFIX) for item in materials))
    save(GENERATED / 'porting/materials.json', sorted(item.removeprefix(PREFIX) for item in materials))
    for item in materials:
        id = item.removeprefix(PREFIX)
        model = Path('src/main/resources/assets') / MOD / 'models/item' / (id + '.json')
        generated_model = GENERATED / 'assets' / MOD / 'models/item' / (id + '.json')
        if not model.exists():
            texture = 'create:item/precision_mechanism' if 'circuit' in id else 'create:item/iron_sheet'
            save(generated_model, {'parent': 'minecraft:item/generated', 'textures': {'layer0': texture}})
        elif generated_model.exists():
            generated_model.unlink()
    for old_id in ['panel_extension', 'door_extension', 'sliding_door_extension', 'floor_extension', 'roof_extension']:
        old_state = GENERATED / 'assets' / MOD / 'blockstates' / (old_id + '.json')
        if old_state.exists():
            old_state.unlink()
    for id in list(extension_ids.values()) + ['company_door_block', 'company_sliding_door']:
        save(GENERATED / 'assets' / MOD / 'blockstates' / (id + '.json'), {'variants': {'': {'model': 'minecraft:block/air'}}})
    for locale in ['en_us', 'zh_cn']:
        language = json.loads((Path('src/porting/languages') / (locale + '.json')).read_text(encoding='utf-8'))
        tab_names = {'carriages': '客车与货车', 'locomotives': '机车', 'interiors': '内饰与设备', 'bogeys': '转向架', 'materials': '车辆制造材料'}
        blank_names = {'vehicle_panel_blank': '车辆板材毛坯', 'vehicle_floor_blank': '车辆地板毛坯', 'vehicle_roof_blank': '车辆车顶毛坯', 'vehicle_interior_blank': '内饰毛坯', 'vehicle_bogey_blank': '转向架毛坯'}
        for id, name in tab_names.items():
            language['itemGroup.' + MOD + '.' + id] = '跨越：' + name if locale == 'zh_cn' else 'KuaYue: ' + id.title()
        for record in blocks + bogeys:
            id = record['id']
            language.setdefault('block.' + MOD + '.' + id, id.replace('_', ' ').upper() if 'renderer' in record else id.replace('_', ' ').title())
        if locale == 'zh_cn':
            bogey_names = {'pk209p': 'PK-209P', 'cw2': 'CW-2', 'sw220k': 'SW-220K', 'sw160': 'SW-160',
                           'mkz': 'MKZ', 'tkz1': 'TKZ1', 'tkz2': 'TKZ2', 'zk6': 'ZK6', 'qj': '前进型主动轮组'}
            for record in bogeys:
                id = record['id']
                base = id.split('_')[0]
                name = '前进型导轮组' if id.startswith('qj_guide') else bogey_names.get(base, base.upper())
                variants = []
                if base == 'pk209p': variants.append('无轴发' if 'no_motor' in id else '轴发')
                elif 'motor' in id: variants.append('动力')
                if 'backward' in id: variants.append('反向')
                if id.endswith('_a'): variants.append('安山岩版')
                if id.endswith('_s'): variants.append('标准轨')
                language['block.' + MOD + '.' + id] = name + ('（' + '，'.join(variants) + '）' if variants else '')
        for material in sorted(materials):
            id = material.removeprefix(PREFIX)
            language.setdefault('item.' + MOD + '.' + id, blank_names.get(id, id.replace('_', ' ').title()) if locale == 'zh_cn' else id.replace('_', ' ').title())
        group_names = {'carriage': '客车转向架', 'freight_carriage': '货车转向架', 'meter_carriage': '米轨客车转向架',
                       'andesite_carriage': '安山岩版客货车转向架', 'loco': '机车转向架', 'meter_loco': '米轨机车转向架',
                       'asymmetry_loco': '单侧机车转向架', 'andesite_loco': '安山岩版机车转向架'}
        for group in sorted({record['group'] for record in bogeys}):
            language['bogey.style.' + MOD + '.' + group] = group_names[group] if locale == 'zh_cn' else group.replace('_', ' ').title()
        save(GENERATED / 'assets' / MOD / 'lang' / (locale + '.json'), language)
    save(Path('docs/recipe-generation.json'), {'recipes': len(selected), 'materials': sorted(materials), 'supplements': supplements})
    print(json.dumps({'recipes': len(selected), 'materials': len(materials), 'supplement_count': len(supplements)}, indent=2))


if __name__ == '__main__':
    main()
