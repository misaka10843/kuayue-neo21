import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from slab_models import add_boundary_cullfaces, remove_identity_rotations


class SlabModelTests(unittest.TestCase):
    def test_identity_rotation_removal_preserves_geometry_and_uv(self):
        element = {'from': [0, 8, 0], 'to': [16, 16, 16],
                   'rotation': {'angle': 0, 'axis': 'y', 'origin': [100, 8, -200]},
                   'faces': {'up': {'texture': '#floor', 'uv': [0, 0, 8, 8], 'rotation': 90}}}
        model = {'elements': [element]}
        expected = {key: value for key, value in element.items() if key != 'rotation'}
        self.assertEqual(remove_identity_rotations(model), 1)
        self.assertEqual(element, expected)
        self.assertEqual(remove_identity_rotations(model), 0)

    def test_real_rotations_and_rescaling_are_retained(self):
        rotations = [{'angle': 22.5, 'axis': 'x'}, {'angle': 0, 'axis': 'y', 'rescale': True}]
        model = {'elements': [{'rotation': rotation.copy()} for rotation in rotations]}
        self.assertEqual(remove_identity_rotations(model), 0)
        self.assertEqual([element['rotation'] for element in model['elements']], rotations)

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
