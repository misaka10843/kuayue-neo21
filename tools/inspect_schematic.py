import argparse
import collections
import gzip
import io
import json
import struct
from pathlib import Path


class NbtReader:
    def __init__(self, data):
        self.stream = io.BytesIO(data)

    def read(self, count):
        data = self.stream.read(count)
        if len(data) != count:
            raise ValueError('Truncated NBT')
        return data

    def number(self, pattern):
        return struct.unpack('>' + pattern, self.read(struct.calcsize(pattern)))[0]

    def string(self):
        return self.read(self.number('H')).decode('utf-8')

    def payload(self, kind):
        if kind in range(1, 7):
            return self.number({1: 'b', 2: 'h', 3: 'i', 4: 'q', 5: 'f', 6: 'd'}[kind])
        if kind == 8:
            return self.string()
        if kind == 10:
            result = {}
            while tag := self.number('B'):
                name = self.string()
                result[name] = self.payload(tag)
            return result
        if kind in (7, 9, 11, 12):
            item_kind = self.number('B') if kind == 9 else {7: 1, 11: 3, 12: 4}[kind]
            count = self.number('i')
            if count < 0 or count > len(self.stream.getbuffer()):
                raise ValueError('Invalid NBT array length')
            return [self.payload(item_kind) for _ in range(count)]
        raise ValueError('Unsupported NBT tag: ' + str(kind))


def load(path):
    data = path.read_bytes()
    if data.startswith(b'\x1f\x8b'):
        data = gzip.decompress(data)
    reader = NbtReader(data)
    if reader.number('B') != 10:
        raise ValueError('NBT root is not a compound')
    reader.string()
    return reader.payload(10)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('source', type=Path)
    parser.add_argument('--output', type=Path)
    args = parser.parse_args()
    data = load(args.source)
    palette = data.get('palette', [])
    counts = collections.Counter(block['state'] for block in data.get('blocks', []))
    summary = {'source': str(args.source), 'size': data.get('size'), 'blocks': len(data.get('blocks', [])),
               'palette': [{'index': i, 'count': counts[i], **state} for i, state in enumerate(palette)]}
    if args.output:
        args.output.parent.mkdir(parents=True, exist_ok=True)
        args.output.write_text(json.dumps(data, ensure_ascii=False, indent=2) + '\n', encoding='utf-8')
    print(json.dumps(summary, ensure_ascii=True, indent=2))


if __name__ == '__main__':
    main()
