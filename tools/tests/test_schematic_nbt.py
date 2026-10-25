import gzip
import struct
import sys
import tempfile
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from inspect_schematic import NbtReader, load


def string(value):
    data = value.encode('utf-8')
    return struct.pack('>H', len(data)) + data


class SchematicNbtTests(unittest.TestCase):
    def test_gzip_compound_and_integer_list(self):
        raw = b'\x0a\x00\x00' + b'\x08' + string('Name') + string('kuayue:company_sliding_door')
        raw += b'\x09' + string('pos') + b'\x03' + struct.pack('>iiii', 3, 1, 3, 1) + b'\x00'
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / 'test.nbt'
            path.write_bytes(gzip.compress(raw))
            self.assertEqual({'Name': 'kuayue:company_sliding_door', 'pos': [1, 3, 1]}, load(path))

    def test_truncated_payload_is_rejected(self):
        with self.assertRaisesRegex(ValueError, 'Truncated'):
            NbtReader(b'\x00').payload(3)

    def test_negative_array_length_is_rejected(self):
        with self.assertRaisesRegex(ValueError, 'array length'):
            NbtReader(struct.pack('>i', -1)).payload(11)


if __name__ == '__main__':
    unittest.main()
