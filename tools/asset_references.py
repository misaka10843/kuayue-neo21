import struct
import zlib


def asset_references(value):
    if isinstance(value, list):
        for item in value:
            yield from asset_references(item)
    elif isinstance(value, dict):
        for key, item in value.items():
            if key == 'sounds' and isinstance(item, list):
                for sound in item:
                    if isinstance(sound, str):
                        yield 'sound', sound
                    elif isinstance(sound, dict) and sound.get('type', 'file') == 'file':
                        yield 'sound', sound['name']
            elif key == 'textures' and isinstance(item, dict):
                for texture in item.values():
                    if isinstance(texture, str) and not texture.startswith('#'):
                        yield 'texture', texture
            elif key in ('model', 'parent') and isinstance(item, str):
                yield 'file' if item.endswith(('.obj', '.mtl')) else 'model', item
            elif key == 'texture' and isinstance(item, str) and not item.startswith('#'):
                yield 'texture', item
            else:
                yield from asset_references(item)


def asset_path(kind, reference):
    namespace, path = reference.split(':', 1) if ':' in reference else ('minecraft', reference)
    path = path.replace('//', '/')
    suffix = {'texture': 'textures/' + path + '.png', 'model': 'models/' + path + '.json', 'sound': 'sounds/' + path + '.ogg', 'file': path}[kind]
    return 'assets/' + namespace + '/' + suffix


def verify_png(data):
    if data[:8] != b'\x89PNG\r\n\x1a\n':
        raise ValueError('Invalid PNG signature')
    offset = 8
    compressed = bytearray()
    dimensions = None
    ended = False
    while offset < len(data):
        if offset + 12 > len(data):
            raise ValueError('Truncated PNG chunk')
        length = struct.unpack_from('>I', data, offset)[0]
        name = data[offset + 4:offset + 8]
        payload = data[offset + 8:offset + 8 + length]
        if offset + 12 + length > len(data):
            raise ValueError('Truncated PNG payload')
        crc = struct.unpack_from('>I', data, offset + 8 + length)[0]
        if zlib.crc32(name + payload) != crc:
            raise ValueError('Invalid PNG chunk checksum')
        if name == b'IHDR':
            dimensions = struct.unpack_from('>II', payload)
            if not all(dimensions):
                raise ValueError('Empty PNG dimensions')
        elif name == b'IDAT':
            compressed.extend(payload)
        elif name == b'IEND':
            ended = True
            break
        offset += length + 12
    if not ended or dimensions is None or not compressed:
        raise ValueError('Incomplete PNG image')
    zlib.decompress(compressed)
    return dimensions
