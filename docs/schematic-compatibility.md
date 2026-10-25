# Schematic compatibility in 1.2.1

The supplied `软卧红色涂装25G(汽鸣铁道詹式车钩）.nbt` is a gzip Minecraft structure with size 27 × 6 × 5 and 468 saved blocks. Its file is read-only. An eight-block entrance excerpt is kept in `src/porting/schematic-regression.json` for automated regression checks.

## Fixed placement behavior

- `onPlace` previously generated companions immediately, including inside Create's virtual schematic world. It could replace doors, shelves, concrete and other parts occupying the same footprint.
- Replacing a companion called its parent's destructive removal path. Loading another saved block or a legacy companion over the generated block could therefore drop the parent item.
- Companion generation now runs immediately only for an actual entity placement, or in a deferred real-server block tick. Both paths preserve every occupied block except a companion owned by the same parent. No neighboring blocks are generated inside the virtual schematic world.
- Generic companion replacement no longer destroys its parent. Direct player breaking and delegated Create wrench removal still remove the parent part. Parent destruction still cleans up its owned companions.
- Legacy `kuayue:company_door_block` and `kuayue:company_sliding_door` are registered as hidden compatibility blocks alongside the newer official IDs. They use the same companion block entity and have no obtainable item or survival recipe. The registry now has 458 blocks, including 451 obtainable blocks and seven hidden companions.

## Validation and limits

The full supplied blueprint passes a world-free forward/reverse placement-policy replay using actual registered block states, footprints, ownership tags and extension factories: 468 saved blocks, 668 replayed root placements and 46 occupied-cell protection decisions. This tests the placement policy; it does not execute Minecraft world callbacks, physics, schematicannon inventory use or actual item drops. Client printing, direct breaking, wrench removal, rotations and saved-world reload remain user game checks.

Unregistered IDs are represented as occupied cells in that replay and reported separately. This does not register or simulate the missing content.

In the isolated audit environment these IDs are unavailable:

| ID | Saved blocks | Scope |
| --- | ---: | --- |
| `kuayue:standard_track` | 27 | Tracks were explicitly excluded from this port |
| `kuayue:smooth_quartz_panel_4` | 7 | Ground/decorative facility content outside the selected vehicle scope |
| `railways:link_and_pin` | 2 | External mod content; needs its own compatible mod |

Consequently the entire blueprint is not claimed to print unchanged. Substitute Create standard tracks and an available decorative block as appropriate; the external coupler depends on its owning mod. Meter bogeys still have no included meter track. No original schematic file is rewritten by these tools.

## Reproduce the supplied-file audit

```powershell
uv run --no-project python tools/inspect_schematic.py 'run/schematics/软卧红色涂装25G(汽鸣铁道詹式车钩）.nbt' --output build/schematic-inspection.json
./gradlew.bat runData '-PschematicFixture=build/schematic-inspection.json' --no-configuration-cache
```

Normal `runData` uses the small repository fixture and `build/data-run` as its game directory. It does not load the user's client mods from `run/mods`. The resulting report is `build/reports/port-audit.json`.
