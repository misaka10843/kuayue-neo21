import argparse
import json
import re
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
FORBIDDEN = (b'lib/kasuga/', b'lib.kasuga.', b'io/micronaut/', b'io.micronaut.')


def verify_jar(path):
    failures = []
    with zipfile.ZipFile(path) as jar:
        for name in jar.namelist():
            if name.endswith('.jar'):
                failures.append('Unexpected bundled JAR: ' + name)
            if name.endswith('.class'):
                data = jar.read(name)
                if any(token in name.encode() or token in data for token in FORBIDDEN):
                    failures.append('Forbidden class reference: ' + name)
        metadata = jar.read('META-INF/neoforge.mods.toml').decode()
        for mod in ('kasuga_lib', 'kasuga', 'micronaut'):
            if re.search(r'modId\s*=\s*[\x22\x27]' + mod + r'[\x22\x27]', metadata):
                failures.append('Forbidden mod dependency: ' + mod)
        for license in ('KuaYue-LICENSE.txt', 'KuaYue-LICENSE-RESOURCES.txt'):
            if 'META-INF/licenses/' + license not in jar.namelist():
                failures.append('Missing upstream license: ' + license)
    return failures


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--jar', type=Path)
    args = parser.parse_args()
    failures = []
    java_files = list((ROOT / 'src/main/java').rglob('*.java'))
    for path in java_files:
        if any(token in path.read_bytes() for token in FORBIDDEN):
            failures.append('Forbidden source reference: ' + str(path.relative_to(ROOT)))
    for name in ('build.gradle', 'settings.gradle'):
        text = (ROOT / name).read_text(encoding='utf-8')
        if re.search(r'(?:implementation|runtimeOnly|compileOnly|annotationProcessor|includeBuild|id)\s*\(?[\x22\x27](?:lib\.kasuga|io\.micronaut|builder\.kasuga|[^\x22\x27]*KasugaLib)', text):
            failures.append('Forbidden build declaration: ' + name)
    if args.jar:
        failures.extend(verify_jar(args.jar))
    report = {'checkedJavaSources': len(java_files), 'jar': str(args.jar) if args.jar else None, 'failures': failures}
    output = ROOT / 'build/reports/library-independence.json'
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(json.dumps(report, indent=2) + '\n', encoding='utf-8')
    print(json.dumps(report, indent=2))
    raise SystemExit(bool(failures))


if __name__ == '__main__':
    main()
