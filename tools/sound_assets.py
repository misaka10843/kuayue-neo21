import struct


def crc_table():
    table = []
    for value in range(256):
        crc = value << 24
        for _ in range(8):
            crc = ((crc << 1) ^ (0x04c11db7 if crc & 0x80000000 else 0)) & 0xffffffff
        table.append(crc)
    return table


CRC_TABLE = crc_table()


def verify_ogg(data):
    offset = 0
    pages = 0
    ended = False
    while offset < len(data):
        if len(data) - offset < 27 or data[offset:offset + 5] != b'OggS\0':
            raise ValueError('Invalid Ogg page header')
        segments = data[offset + 26]
        body = offset + 27 + segments
        if body > len(data):
            raise ValueError('Truncated Ogg segment table')
        end = body + sum(data[offset + 27:body])
        if end > len(data):
            raise ValueError('Truncated Ogg page')
        if pages == 0 and not data[body:end].startswith(b'\x01vorbis'):
            raise ValueError('Missing Vorbis identification packet')
        page = bytearray(data[offset:end])
        expected = struct.unpack_from('<I', page, 22)[0]
        page[22:26] = b'\0' * 4
        crc = 0
        for value in page:
            crc = ((crc << 8) & 0xffffffff) ^ CRC_TABLE[((crc >> 24) ^ value) & 0xff]
        if crc != expected:
            raise ValueError('Invalid Ogg page checksum')
        ended = bool(data[offset + 5] & 4)
        pages += 1
        offset = end
    if not pages or not ended:
        raise ValueError('Incomplete Ogg stream')
    return pages
