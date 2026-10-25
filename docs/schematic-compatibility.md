# Schematic compatibility

The supplied `软卧红色涂装25G(汽鸣铁道詹式车钩）.nbt` is a gzip Minecraft structure with size 27 × 6 × 5 and 468 saved blocks. Its file is read-only. An eight-block entrance excerpt is kept in `src/porting/schematic-regression.json` for automated regression checks.

## Fixed placement behavior

- `onPlace` previously generated companions immediately, including inside Create's virtual schematic world. It could replace doors, shelves, concrete and other parts occupying the same footprint.
- Replacing a companion called its parent's destructive removal path. Loading another saved block or a legacy companion over the generated block could therefore drop the parent item.
- Companion generation now runs immediately only for an actual entity placement, or in a deferred real-server block tick. Both paths preserve every occupied block except a companion owned by the same parent. No neighboring blocks are generated inside the virtual schematic world.
- Generic companion replacement no longer destroys its parent. Direct player breaking and delegated Create wrench removal still remove the parent part. Parent destruction still cleans up its owned companions.
- Legacy `company_door_block`, `company_sliding_door`, `company_floor` and `company_carport` are registered alongside the official companion IDs. The registry now has 500 blocks: 491 obtainable and nine hidden companions.
- Create's instant printer calls `BedBlock.setPlacedBy` for both saved halves. Calling it on a saved head creates another head, which can break beds. The Create-only callback redirect skips this extra head callback while preserving the saved head, normal foot callback and hand placement.

## Validation and limits

The full supplied blueprint passes both the world-free ownership replay and two headless server GameTests. The latter call Create's actual `BlockHelper.placeSchematicBlock` for all 468 saved blocks, in forward and reverse order, then wait eight server ticks. All expected non-companion blocks remain and no item entities appear. These tests execute placement callbacks and deferred companion repair in a real `ServerLevel` in `build/gametest-run`, separate from user saves. Client printing, schematicannon inventory use, rotations, direct breaking, wrench removal and reload in the user's modpack remain user game checks.

Unregistered IDs are represented as occupied cells in that replay and reported separately. This does not register or simulate the missing content.

In the isolated audit environment these IDs are unavailable:

| ID | Saved blocks | Scope |
| --- | ---: | --- |
| `railways:link_and_pin` | 2 | External mod content; needs its own compatible mod |

Tracks and quartz decorations now resolve to actual registered blocks. The two external couplers are occupied barrier placeholders in isolated tests; their behavior is not tested or implemented by this port. No original schematic file is rewritten.

## Reproduce the supplied-file audit

```powershell
uv run --no-project python tools/inspect_schematic.py 'run/schematics/软卧红色涂装25G(汽鸣铁道詹式车钩）.nbt' --output build/schematic-inspection.json
./gradlew.bat runData '-PschematicFixture=build/schematic-inspection.json' --no-configuration-cache
./gradlew.bat runGameTestServer '-PschematicFixture=build/schematic-inspection.json' --no-configuration-cache
```

Normal `runData` uses the small repository fixture and `build/data-run` as its game directory. It does not load the user's client mods from `run/mods`. The resulting report is `build/reports/port-audit.json`.

`tools/generate_test_structure.py` maintains the empty GameTest structure. Tests only register when the `kuayue` GameTest namespace is enabled. A passing isolated test is not acceptance of an existing damaged save or compatibility with every installed mod.
