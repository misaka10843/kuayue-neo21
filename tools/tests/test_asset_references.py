import struct
import sys
import unittest
import zlib
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from asset_references import asset_references, asset_path, verify_png


class AssetReferenceTests(unittest.TestCase):
    def test_model_with_same_named_texture_requires_both_files(self):
        model = {'parent': 'kuayue:block/parent', 'textures': {'particle': 'kuayue:block/seat_rz'}}
        paths = {asset_path(kind, ref) for kind, ref in asset_references(model)}
        self.assertIn('assets/kuayue/textures/block/seat_rz.png', paths)
        self.assertNotIn('assets/kuayue/models/block/seat_rz.json', paths)

    def test_obj_geometry_and_texture_are_distinct_dependencies(self):
        refs = dict(asset_references({'model': 'kuayue:models/block/frame.obj', 'textures': {'body': 'kuayue:block/frame', 'alias': '#body'}}))
        self.assertEqual(refs['file'], 'kuayue:models/block/frame.obj')
        self.assertEqual(refs['texture'], 'kuayue:block/frame')
        self.assertEqual(len(refs), 2)

    def test_lfs_pointer_is_not_a_png(self):
        with self.assertRaisesRegex(ValueError, 'signature'):
            verify_png(b'version https://git-lfs.github.com/spec/v1\n')

    def test_png_checks_crc_and_compressed_data(self):
        def chunk(name, payload):
            return struct.pack('>I', len(payload)) + name + payload + struct.pack('>I', zlib.crc32(name + payload))
        image = b'\x89PNG\r\n\x1a\n' + chunk(b'IHDR', struct.pack('>IIBBBBB', 1, 1, 8, 6, 0, 0, 0))
        image += chunk(b'IDAT', zlib.compress(b'\x00\xff\xff\xff\xff')) + chunk(b'IEND', b'')
        self.assertEqual(verify_png(image), (1, 1))
        damaged = bytearray(image)
        damaged[20] ^= 1
        with self.assertRaisesRegex(ValueError, 'checksum'):
            verify_png(damaged)


if __name__ == '__main__':
    unittest.main()
