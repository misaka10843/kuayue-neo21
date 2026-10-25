import argparse
import hashlib
import json
import re
import subprocess
from pathlib import Path

from extract_legacy import strip_comments


ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / 'src/main/java'
PREFIX = 'com.misaka10843.kuayue_neo21'
ALIASES = {
    'SlabBlock': 'TrainSlabBlock',
    'LadderBlock': 'TrainLadderBlock',
    'EndFaceBlock': 'TrainEndfaceBlock',
    'CustomRenderedEndFaceBlock': 'CustomRenderedEndfaceBlock',
    'CustomRenderedEndFaceEntity': 'CustomRenderedEndfaceEntity',
    'DF21HeadEarlyBlock': 'DF21HeadEarlyBlock',
}


def inventory(source):
    local = {p.stem: p for p in (JAVA / Path(*PREFIX.split('.')) / 'legacy').rglob('*.java')}
    paths = sorted((source / 'modules/train-parts/src/main/java').rglob('*.java'))
    paths += sorted((source / 'modules/core/src/main/java/cn/kycraft/kuayue/utils').glob('*.java'))
    mapping = {}
    pairs = []
    for path in paths:
        text = path.read_text(encoding='utf-8-sig')
        name = ALIASES.get(path.stem, path.stem)
        target = local.get(name)
        if not target:
            continue
        package = re.search(r'package ([\w.]+);', text)[1]
        target_package = re.search(r'package ([\w.]+);', target.read_text(encoding='utf-8-sig'))[1]
        mapping[package + '.' + path.stem] = target_package + '.' + name
        pairs.append((path, target))
    return mapping, pairs


def adapt(text, mapping):
    text = strip_comments(text)
    text = re.sub(r'^import cn\.kycraft\.kuayue\.parts\.core\.panel\.\*;\s*', '', text, flags=re.M)
    for original, target in sorted(mapping.items(), key=lambda x: -len(x[0])):
        text = re.sub(re.escape(original) + r'\b', lambda _: target, text)
    text = text.replace('import cn.kycraft.kuayue.KuaYue;', f'import {PREFIX}.KuaYueneo21;')
    text = text.replace('KuaYue.MODID', 'KuaYueneo21.MODID')
    text = re.sub(r'import cn\.kycraft\.kuayue\.parts\.core\.panel\.(?:PanelBlockItem|SlabBlockItem);',
                  f'import {PREFIX}.content.VehicleBlockItem;', text)
    text = re.sub(r'\b(?:PanelBlockItem|SlabBlockItem)\b', 'VehicleBlockItem', text)
    text = re.sub(r'\b(CustomRenderedEndFaceBlock|CustomRenderedEndFaceEntity|SlabBlock|LadderBlock|EndFaceBlock)\b',
                  lambda m: ALIASES[m[0]], text)
    text = text.replace('CompanyRegistry.', 'AllBlocks.').replace('CustomRenderedRegistry.', 'AllBlocks.')
    text = re.sub(r'import cn\.kycraft\.kuayue\.parts\.core\.panel\.(?:company\.CompanyRegistry|custom_rendered\.CustomRenderedRegistry);',
                  f'import {PREFIX}.legacy.initial.AllBlocks;', text)
    text = text.replace('.getEntry()', '.instance()')
    text = text.replace('CUSTOM_RENDERED_END_FACE_ENTITY', 'CUSTOM_RENDERED_ENDFACE_ENTITY')
    text = re.sub(r'(AllBlocks\.[A-Z_]*ENTITY)\.instance\(\)', r'\1.getType()', text)
    text = text.replace('protected AABB createRenderBoundingBox()', 'public AABB getRenderBoundingBox()')
    text = re.sub(r'\n[ \t]*\n(?:[ \t]*\n)+', '\n\n', text)
    return text.strip() + '\n'


def adapt_registration(text, name):
    entries = []
    for match in re.finditer(r'public static final (?:BlockReg|PanelReg|SlabReg|SkirtReg)<([^>]+)>\s+(\w+)\s*=\s*([\s\S]*?);', text):
        kind, field, value = match.groups()
        if 'Pantograph' in kind:
            continue
        value = re.sub(r'\.withDefaultBlockItem\("[^"]+"\)', '', value)
        value = re.sub(r'\.setParent\([\w.]+\)', '', value).strip()
        value = re.sub(r'^(?:BlockReg\.of|new (?:PanelReg|SlabReg|SkirtReg)<>?)\("([^"]+)",\s*',
                       lambda m: f'new PortRegistration<{kind}>("{m[1]}").factory(', value)
        entries.append(f'    public static final PortRegistration<{kind}> {field} =\n            {value}.strengthAndTool(1.5f, 3f).submit();')
    imports = sorted(set(re.findall(r'^import (?!lib\.kasuga|io\.micronaut|cn\.kycraft|static )[^;]+;', text, re.M)))
    imports.append(f'import {PREFIX}.content.PortRegistration;')
    package = re.search(r'package [\w.]+;', text)[0]
    return package + '\n\n' + '\n'.join(imports) + f'\n\npublic final class {name} {{\n' + '\n\n'.join(entries) + '\n\n    public static void register() {}\n}\n'


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--source', required=True, type=Path)
    parser.add_argument('--record', action='store_true')
    args = parser.parse_args()
    mapping, pairs = inventory(args.source)
    out = ROOT / 'build/official-review'
    out.mkdir(parents=True, exist_ok=True)
    rows = []
    for source, target in pairs:
        adapted = adapt(source.read_text(encoding='utf-8-sig'), mapping)
        target_package = re.search(r'package ([\w.]+);', target.read_text(encoding='utf-8-sig'))[1]
        adapted = re.sub(r'^package [\w.]+;', f'package {target_package};', adapted)
        imports = []
        for qualified in sorted(set(mapping.values())):
            package, name = qualified.rsplit('.', 1)
            if package != target_package and re.search(r'\b' + name + r'\b', adapted) and f'import {qualified};' not in adapted:
                imports.append(f'import {qualified};')
        if imports:
            adapted = adapted.replace(f'package {target_package};', f'package {target_package};\n\n' + '\n'.join(imports))
        if 'VehicleBlockItem' in adapted and f'import {PREFIX}.content.VehicleBlockItem;' not in adapted:
            adapted = adapted.replace(f'package {target_package};', f'package {target_package};\n\nimport {PREFIX}.content.VehicleBlockItem;')
        if 'public static final RegistryGroup GROUP_' in adapted:
            adapted = adapt_registration(adapted, target.stem)
        candidate = out / target.name
        candidate.write_text(adapted, encoding='utf-8')
        rows.append({'source': source.relative_to(args.source).as_posix(),
                     'target': target.relative_to(ROOT).as_posix(),
                     'sourceSha256': hashlib.sha256(source.read_bytes()).hexdigest(),
                     'remainingImports': re.findall(r'^import (?:lib\.kasuga|io\.micronaut|cn\.kycraft)[^;]+;', adapted, re.M)})
    (out / 'inventory.json').write_text(json.dumps(rows, indent=2) + '\n', encoding='utf-8')
    if args.record:
        retained = {
            'CompanyTrainPanel': 'Official item forwarding merged; preserve owned-root removal and assembly guards; avoid null BaseEntityBlock codec.',
            'CompanyTrainDoor': 'Keep public constructor for deferred registration.',
            'TrainPanelBlock': 'Remove UnModeledBlockProperty and deferred editor state; preserve safe extension lifecycle.',
            'TrainDoorBlock': 'Preserve server authority, extension ownership, synchronized open state and modern sound events.',
            'TrainSmallWindowBlock': 'Merge official held-item entry point; preserve owned extension synchronization and server authority.',
            'HeadBlock': 'Keep height cycling required by selected CR200J resources.',
            'HeightSlabBlock': 'Keep vanilla slab state needed by existing selected content.',
            'DF11ChimneyBlock': 'Keep smoke and lit behavior absent from this official checkout.',
            'DF11GChimneyBlock': 'Keep smoke and lit behavior absent from this official checkout.',
            'DF5ChimneyBlock': 'Keep smoke and lit behavior absent from this official checkout.',
            'JY290ACBlock': 'Keep hinge and shape behavior matching selected resources.',
            'JY290DoorBlock': 'Keep train-specific door collision geometry.',
            'MeterCarriageEndFaceBlock': 'Keep dedicated single-sliding renderer and entity type.',
            'DoubleRotateDoorBlock': 'Keep dedicated double-rotation renderer and entity type.',
            'TrainPanelShapes': 'Equivalent shapes; keep separate CarportHingeSlabBlock rather than nested alias.',
        }
        for row in rows:
            name = Path(row['target']).stem
            row.pop('remainingImports')
            row['adaptation'] = retained.get(name, 'Adapted official implementation; replace registrations and initialization with native APIs.')
            if name == 'I5Panel':
                row['adaptation'] += ' Restore four functional DF5 doors: official hinge panels lack OPEN required by official blockstates.'
            if name == 'CustomRenderedDoorBlock':
                row['adaptation'] += ' Fix copy constructor to preserve open offset.'
        revision = subprocess.run(['git', '-C', str(args.source), 'rev-parse', 'HEAD'],
                                  check=True, capture_output=True, text=True).stdout.strip()
        snapshot = {'source': 'KuaYue', 'minecraft': '1.21.1',
                    'revision': revision,
                    'classes': rows}
        (ROOT / 'src/porting/official-source.json').write_text(json.dumps(snapshot, indent=2) + '\n', encoding='utf-8')
    print(json.dumps({'mappedClasses': len(rows), 'reviewDirectory': str(out)}, indent=2))


if __name__ == '__main__':
    main()
