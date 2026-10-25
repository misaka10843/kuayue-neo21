import sys
import tempfile
import unittest
import zipfile
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from verify_library_independence import verify_jar


class LibraryIndependenceTests(unittest.TestCase):
    def make_jar(self, directory, files):
        path = Path(directory) / 'port.jar'
        with zipfile.ZipFile(path, 'w') as jar:
            jar.writestr('META-INF/neoforge.mods.toml', '[[mods]]\nmodId="kuayue"\n')
            for name in ('KuaYue-LICENSE.txt', 'KuaYue-LICENSE-RESOURCES.txt'):
                jar.writestr('META-INF/licenses/' + name, 'test license')
            for name, data in files.items():
                jar.writestr(name, data)
        return path

    def test_clean_jar(self):
        with tempfile.TemporaryDirectory() as directory:
            path = self.make_jar(directory, {'port/Block.class': b'net/minecraft/world/level/block/Block'})
            self.assertEqual([], verify_jar(path))

    def test_relocated_class_cannot_hide_library_references(self):
        with tempfile.TemporaryDirectory() as directory:
            path = self.make_jar(directory, {'port/Block.class': b'lib/kasuga/registration/RegistryGroup'})
            self.assertTrue(any('Forbidden class reference' in error for error in verify_jar(path)))

    def test_nested_jar_is_rejected(self):
        with tempfile.TemporaryDirectory() as directory:
            path = self.make_jar(directory, {'META-INF/jarjar/inject.jar': b'embedded library'})
            self.assertTrue(any('Unexpected bundled JAR' in error for error in verify_jar(path)))


if __name__ == '__main__':
    unittest.main()
