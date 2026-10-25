import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from asset_references import asset_references, asset_path
from sound_assets import verify_ogg


class SoundAssetTests(unittest.TestCase):
    def test_sound_event_requires_ogg_file(self):
        refs = list(asset_references({'coupler': {'sounds': ['kuayue:coupler/coupler_1']}}))
        self.assertEqual([asset_path(kind, ref) for kind, ref in refs], ['assets/kuayue/sounds/coupler/coupler_1.ogg'])

    def test_imported_vorbis_and_corrupt_page(self):
        source = Path(__file__).resolve().parents[2] / 'src/main/resources/assets/kuayue/sounds/coupler/coupler_1.ogg'
        data = source.read_bytes()
        self.assertGreater(verify_ogg(data), 1)
        damaged = bytearray(data)
        damaged[-1] ^= 1
        with self.assertRaisesRegex(ValueError, 'checksum'):
            verify_ogg(damaged)
        with self.assertRaisesRegex(ValueError, 'header'):
            verify_ogg(b'version https://git-lfs.github.com/spec/v1\n')
