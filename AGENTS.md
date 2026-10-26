# Repository constraints

## Local file inspection

Prefer FastCtx inspect_local_file, grep and glob for local reads and searches. Pass absolute filesystem paths. Batch related reads. Follow Partial continuation parameters exactly. Use FastCtx replace for mechanical replacements and apply_patch for semantic edits.

## Porting scope

Implement docs/porting-plan.md. The KuaYue sibling directory is the primary read-only 1.21.1 source reference; NeoKuayue supplements functionality absent from that checkout. Preserve upstream licenses and record source mappings. Include all ordinary blocks, tracks, station/ground facilities, decoration and material blocks, plus catenary pillars and structural supports compatible with Create: Electro-Energetics brackets. Include coupling and uncoupling behavior. Do not implement Kuayue's own overhead electrical/wire system or pantographs; driving devices, editable signs, company customization, blueprint workbench and technology gates remain excluded or deferred. Every obtainable ported block needs a reachable survival production path.

Do not depend on, shade, relocate or imitate the KasugaLib framework, its injection library, Micronaut or its Gradle registration/resource plugins. Use NeoForge deferred registers, explicit initialization and the supported Create APIs. Necessary Create dependencies remain allowed. Check resolved dependencies and final class files for forbidden library references; this does not prove compatibility with every other mod.

Preserve the included blocks' behavior and Create integration, including track-aligned bogey placement, native style cycling, assembly data, doors, seats and assisted vehicle-part placement. Registration and models alone do not constitute a functional port. Keep any unresolved behavior gaps explicit.

Topology and velocity synchronization must retain existing client carriage/storage objects. Collision response is server-authoritative: normalize opposing train axes, conserve momentum without adding kinetic energy, and deduplicate contact impulses. Keep native handling for unrelated trains, side crossings and portal-spanning consists. Use the imported original coupler sounds on successful server-side coupling/release, not client packet replay.

Schematic loading must not mutate neighboring blocks from onPlace in virtual levels. Repair companions on the real server after placement, claim only air or companions owned by the same root, and never destroy a root merely because a companion was replaced. Direct player removal and wrench removal must still remove the owned part correctly. Keep regression coverage for legacy companion IDs and overlapping schematic footprints.

## Code

CEE pantograph compatibility concerns mounting the base flush against decorative roof blocks, not changing arm extension height. Keep rendering, hit/collision geometry, saved assembly offsets and electrical contact coordinates consistent. CEE remains optional; validate its actual supplied JAR without bundling its implementation.

Use the `kuayue` mod and resource namespace so included original block IDs can resolve in Create schematics. Keep the project standalone from the official mod and its removed library framework.

Use Java 21 and NeoForge 1.21.1 with Create. Organize code by responsibility. Prefer descriptive English names and direct implementations. Evaluate splitting at roughly 300 lines and prioritize splitting around 500 lines. Avoid unnecessary abstractions, commented-out implementations and unrelated formatting. Comments are exceptional, short English explanations of constraints or reasons. Preserve upstream license notices. Generated files must be maintained through their source generator.

## Validation

The implementer runs compilation, packaging, generation and static resource/recipe checks. The user performs client startup and all in-game verification. Never label compilation as gameplay acceptance. Record actual results and outstanding checks in docs/porting-status.md.

Validate texture references against actual PNG files, model references against model definitions, and OBJ references against geometry/material files. A same-named model must never satisfy a texture dependency. Validate image contents and exercise Create integration APIs separately from recipe/registry checks.

Floor lighting validation must include baked model AO flags and rendered vertex light continuity across adjacent faces in all blockstate rotations. Compare shared world-space vertices, not vertex array indices. Zero-angle element rotations still suppress native FaceBakery winding repair; remove identity rotations through the model importer while preserving real rotations and rescaling. Server light propagation alone does not validate smooth surface lighting. Keep visual reports unresolved until the reported client path is explained or retested; diagnostic builds must be labeled explicitly.

Door pairing must match Create's exact neighbor, block identity, facing, opposite hinge and pre-click open-state rules. Validate companion geometry and orientation before forwarding interactions. Preserve each door's animation progress by position and block identity across ClientContraption render-level rebuilds; never suppress native rebuilds or replace cargo objects. Sliding-door selection retains its frame while collision follows the rendered leaf. Contraption picking must query shapes at real local positions, resolve companion parents from contraption NBT and choose the closest hit.

## Work and Git

Work continuously in complete implementation and verification phases; do not request approval for each block. Preserve unrelated work. The user authorizes a Git commit after each complete feature and its appropriate automatic checks. Use English `type (scope): description`, stage only task paths and group complete features. Do not push, rewrite history or change Git configuration. Authorization from another repository does not transfer here.
