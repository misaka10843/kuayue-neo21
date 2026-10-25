# Official 1.21.1 source and library removal

## 1.2.0 namespace update

The active namespace is now `kuayue`, as explicitly requested for original Create schematics. Use `build/libs/kuayue-1.2.0.jar` in the verification commands below. The original companion block IDs and three shared block-entity IDs have also been aligned with the official 1.21.1 source. Earlier version names below describe the 1.1.0 implementation. See `docs/porting-status.md` for current interaction, rendering, seating and placement repairs, and `docs/gameplay-checklist.md` for pending game checks.

Primary source: the read-only sibling `KuaYue` checkout, revision `c8f11d2df50c04d5dc3a32a0f17dad8693a920f5`. Source fingerprints and the 60 class mappings are recorded in `src/porting/official-source.json`. This is a source adaptation, with the `kuayue` namespace and the previously confirmed content scope.

The checkout contains Java implementations for common panels and eight locomotive registration families. It also contains carriage, seat and bogey resources, but no corresponding Java seat or vehicle-bogey implementation was found in its main source sets. Those functions continue using the adapted implementations already in this project. Interlocking, dispatching, driving equipment and tracks are outside the confirmed scope.

## Replaced framework services

| Official service | Standalone implementation |
| --- | --- |
| Kasuga `RegistryGroup`, `BlockReg`, `PanelReg`, `SlabReg`, `SkirtReg` | NeoForge deferred registers through the small project-owned `PortRegistration`; no framework classes copied |
| Micronaut `@Context`, injection entry points | Explicit family registration and lifecycle callbacks in the mod entry point |
| `UnModeledBlockProperty` | Removed together with the deferred panel editor state |
| Kasuga resource compiler and Gradle plugins | Imported ordinary Minecraft assets and checked-in data produced by `tools/generate_data.py` |
| Kasuga client registration | NeoForge client events and direct renderer registration |
| Compatibility wrench wrapper | Direct Create `IWrenchable` and its native rotation sound |

No KasugaLib, injection library or Micronaut JAR is required, shaded or relocated. Create, Ponder, Flywheel and Registrate remain necessary dependencies. `verifyNoLibraryDependencies` checks resolved compile/runtime/processor artifacts as part of Gradle `check`. `tools/verify_library_independence.py --jar ...` checks source references, packaged class references, nested JARs, metadata and licenses. These checks establish library independence; compatibility with a particular modpack still needs game testing.

## Functional adaptations

45 mapped classes were adapted from the official source, including the eight locomotive registration families, panel/slab/window implementations, model-backed door entities and renderers. The other 15 mappings retain or merge existing behavior for these concrete reasons:

- Companion extensions keep root ownership checks and assembly/removal guards. The official `BaseEntityBlock` implementation returns a null codec; this port keeps the existing `Block`/`EntityBlock` implementation.
- Door/window actions remain server-authoritative, update only owned extensions and synchronize open state. The official held-item interaction entry points and companion forwarding were merged.
- `TrainPanelBlock` omits the editor property and its library wrapper, consistent with the deferred GUI scope.
- The current checkout simplifies smoke blocks, JY290 geometry and some special door renderers. Existing smoke, hinge, collision, head-height and dedicated sliding/double-rotation behavior remains.
- DF5's four official door registrations use hinge panels without `OPEN`, while the official blockstates require `OPEN`. The state coverage audit detected this mismatch. Functional door factories and their collision shapes were retained for those four entries.
- The official door copy constructor copies the closed offset into the open offset; this port preserves `modelFrom.openoffset`.
- The official nested carport slab alias is represented by the existing separate class.

The independent custom coupling/uncoupling subsystem remains deferred, as confirmed by the user. Native Create train assembly and bogey behavior remain included.

## Resources and regeneration

The import selects 2818 source assets: 2805 from official 1.21.1 and 13 fallback assets. Existing supplemental resources are preserved. The final asset audit checks 585 PNGs, model dependencies and OBJ/MTL dependencies separately.

```powershell
uv run --no-project python tools/import_resources.py --source ../NeoKuayue --official-source ../KuaYue
uv run --no-project python tools/generate_data.py
./gradlew.bat verifyPortResources runData build --no-configuration-cache
uv run --no-project python -m unittest discover -s tools/tests -v
uv run --no-project python tools/verify_port.py --jar build/libs/kuayue-1.2.0.jar
uv run --no-project python tools/verify_library_independence.py --jar build/libs/kuayue-1.2.0.jar
```

The data generator removes identical duplicate recipes introduced by overlapping source layouts and removes stale JSON only inside its own generated recipe directory. The result has 484 distinct recipes. Java source is maintained normally; `audit_official_source.py` produces review candidates under `build/official-review`, never overwrites maintained Java, and optionally refreshes the provenance snapshot with `--record`.

Upstream MIT code and CC BY-SA 4.0 resource licenses are included under `META-INF/licenses/KuaYue-*`. Namespace, registration, lifecycle, resource loader and functional fixes are adaptations of the upstream content.

## User game checks

Replace the previously installed port JAR with `1.2.0`; keep Create and its required components. The port itself does not require KasugaLib. Check the previous problem examples (SS8 windows, auxiliary reservoir, soft seats and 22-series parts), held-item/empty-hand window interaction, companion clicks, DF5 door collision changes and native bogey style/assembly behavior. Use `docs/gameplay-checklist.md` for the complete behavior checklist. No client or game-world acceptance is claimed by the automated checks.
