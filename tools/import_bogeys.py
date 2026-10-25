import argparse
import json
import re
from pathlib import Path
from extract_legacy import strip_comments

PACKAGE = 'com.misaka10843.kuayue_neo21'


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--source', type=Path, required=True)
    args = parser.parse_args()
    groups = []
    records = []
    for name in ['AllCarriageBogeys', 'AllLocoBogeys']:
        file = args.source / 'src/main/java/willow/train/kuayue/initial/create' / (name + '.java')
        text = strip_comments(file.read_text(encoding='utf-8-sig'))
        sizes = dict(re.findall(r'(\w+)\s*=\s*new BogeySizeReg\("([^"]+)"\)', text))
        pattern = r'new BogeyGroupReg\("([^"]+)", "([^"]+)"\)([\s\S]*?)\.submit\('
        for match in re.finditer(pattern, text):
            group = match[1]
            declarations = []
            for entry in re.finditer(r'\.bogey\((\w+)\.getSize\(\), ([\w.]+)::new, [\w.]+\.asResource\("([^"]+)"\)\)', match[3]):
                size, renderer, id = entry.groups()
                size = sizes[size]
                record = {'id': id, 'group': group, 'size': size, 'renderer': renderer,
                          'meter': group.startswith('meter_'), 'asymmetry': group.startswith('asymmetry_')}
                records.append(record)
                declarations.append(f'        group.add("{id}", "{size}", () -> () -> new {renderer}().sizeRenderer());')
            if declarations:
                groups.append(f'        group = new BogeyGroup("{group}", {str(group.startswith("meter_")).lower()}, {str(group.startswith("asymmetry_")).lower()});\n' + '\n'.join(declarations) + '\n        group.register();')
    output = Path('src/main/java') / Path(*PACKAGE.split('.')) / 'content/bogey/VehicleBogeys.java'
    text = f'''package {PACKAGE}.content.bogey;

import {PACKAGE}.content.PortEntityType;
import {PACKAGE}.legacy.block.bogey.carriage.renderer.*;
import {PACKAGE}.legacy.block.bogey.loco.renderer.*;

public final class VehicleBogeys {{
    public static final PortEntityType<VehicleBogeyEntity> ENTITY = new PortEntityType<>("bogey", VehicleBogeyEntity::new, VehicleBogeyBlock.class::isInstance);

    public static void initialize() {{
        BogeyGroup group;
''' + '\n'.join(groups) + '\n    }\n}\n'
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(text, encoding='utf-8')
    Path('docs/legacy-bogeys.json').write_text(json.dumps(records, indent=2) + '\n', encoding='utf-8')
    print(f'Imported {len(records)} bogeys across {len(groups)} style groups')


if __name__ == '__main__':
    main()
