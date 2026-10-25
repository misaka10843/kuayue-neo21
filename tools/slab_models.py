def remove_identity_rotations(model):
    changed = 0
    for element in model.get('elements', []):
        rotation = element.get('rotation')
        if rotation and rotation.get('angle') == 0 and not rotation.get('rescale', False):
            # A zero-angle rotation still suppresses FaceBakery's vertex winding repair.
            del element['rotation']
            changed += 1
    return changed


def add_boundary_cullfaces(model):
    changed = 0
    boundaries = {'west': (0, 0, 'from'), 'east': (0, 16, 'to'),
                  'down': (1, 0, 'from'), 'up': (1, 16, 'to'),
                  'north': (2, 0, 'from'), 'south': (2, 16, 'to')}
    for element in model.get('elements', []):
        lower, upper = element.get('from', []), element.get('to', [])
        if len(lower) != 3 or len(upper) != 3 or any(a < 0 or b > 16 for a, b in zip(lower, upper)):
            continue
        if element.get('rotation', {}).get('angle', 0) != 0:
            continue
        for direction, face in element.get('faces', {}).items():
            axis, coordinate, endpoint = boundaries[direction]
            if abs(element[endpoint][axis] - coordinate) < 1e-6 and 'cullface' not in face:
                face['cullface'] = direction
                changed += 1
    return changed
