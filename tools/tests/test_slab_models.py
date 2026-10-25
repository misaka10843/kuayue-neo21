import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from slab_models import add_boundary_cullfaces


class SlabModelTests(unittest.TestCase):
    def test_top_slab_culls_only_boundary_faces(self):
        faces = {side: {'texture': '#floor'} for side in ['up', 'down', 'east', 'west', 'north', 'south']}
        model = {'elements': [{'from': [0, 8, 0], 'to': [16, 16, 16], 'faces': faces}]}
        self.assertEqual(add_boundary_cullfaces(model), 5)
        self.assertNotIn('cullface', faces['down'])
        self.assertEqual(faces['up']['cullface'], 'up')
        self.assertEqual(add_boundary_cullfaces(model), 0)

    def test_wide_or_rotated_geometry_keeps_unculled_faces(self):
        for lower, upper, rotation in [([-16, 8, 0], [16, 16, 16], 0), ([0, 8, 0], [16, 16, 16], 22.5)]:
            model = {'elements': [{'from': lower, 'to': upper, 'rotation': {'angle': rotation}, 'faces': {'up': {'texture': '#floor'}}}]}
            self.assertEqual(add_boundary_cullfaces(model), 0)
            self.assertNotIn('cullface', model['elements'][0]['faces']['up'])
