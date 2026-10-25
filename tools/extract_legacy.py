import argparse
import json
import re
from pathlib import Path


def strip_comments(text):
    return re.sub(r'"(?:\\.|[^"\\])*"|/\*[\s\S]*?\*/|//[^\n]*',
                  lambda m: m[0] if m[0].startswith('"') else '', text)


def extract(source):
    initial = source / 'src/main/java/willow/train/kuayue/initial'
    files = sorted((initial / 'panel').glob('*.java'))
    files += [initial / 'AllDecoBlocks.java']
    records = []
    for file in files:
        text = strip_comments(file.read_text(encoding='utf-8-sig'))
        pattern = r'new\s+(PanelRegistration|SlabRegistration|SkirtRegistration|BlockReg)(?:<([^>]+)>)?\("([^"]+)"\)'
        for match in re.finditer(pattern, text):
            end = text.index('.submit(', match.end())
            definition = text[match.start():end]
            if 'Pantograph' in definition:
                continue
            records.append({'id': match[3], 'family': file.stem,
                            'registration': match[1], 'type': match[2],
                            'definition': definition})
    records.append({'id': 'cr_logo', 'family': 'AllBlocks',
                    'registration': 'BlockReg', 'type': 'Block', 'definition': ''})
    return records


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--source', type=Path, required=True)
    parser.add_argument('--output', type=Path, required=True)
    args = parser.parse_args()
    records = extract(args.source)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(records, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    counts = {}
    for record in records:
        counts[record['family']] = counts.get(record['family'], 0) + 1
    print(json.dumps({'blocks': len(records), 'families': counts}, indent=2))


if __name__ == '__main__':
    main()
