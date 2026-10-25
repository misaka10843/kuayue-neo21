import gzip
import struct
from pathlib import Path


def named(kind, name, payload):
    encoded = name.encode('utf-8')
    return bytes([kind]) + struct.pack('>H', len(encoded)) + encoded + payload


def main():
    size = named(9, 'size', bytes([3]) + struct.pack('>iiii', 3, 32, 16, 16))
    palette = named(9, 'palette', bytes([10]) + struct.pack('>i', 1)
                    + named(8, 'Name', struct.pack('>H', 13) + b'minecraft:air') + b'\0')
    empty = bytes([10]) + struct.pack('>i', 0)
    data = named(10, '', named(3, 'DataVersion', struct.pack('>i', 3955)) + size + palette
                 + named(9, 'blocks', empty) + named(9, 'entities', empty) + b'\0')
    target = Path('src/generated/resources/data/kuayue/structure/schematic_room.nbt')
    target.parent.mkdir(parents=True, exist_ok=True)
    target.write_bytes(gzip.compress(data, mtime=0))


if __name__ == '__main__':
    main()
