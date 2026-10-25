# Repository constraints

## Local file inspection

Prefer FastCtx inspect_local_file, grep and glob for local reads and searches. Pass absolute filesystem paths. Batch related reads. Follow Partial continuation parameters exactly. Use FastCtx replace for mechanical replacements and apply_patch for semantic edits.

## Porting scope

Implement docs/porting-plan.md. The KuaYue sibling directory is the primary read-only 1.21.1 source reference; NeoKuayue supplements functionality absent from that checkout. Preserve upstream licenses and record source mappings. Exclude overhead lines, ground facilities, tracks, pantographs, driving devices, editable signs, company customization, blueprints and technology gates. Every obtainable ported block needs a reachable survival production path.

Do not depend on, shade, relocate or imitate the KasugaLib framework, its injection library, Micronaut or its Gradle registration/resource plugins. Use NeoForge deferred registers, explicit initialization and the supported Create APIs. Necessary Create dependencies remain allowed. Check resolved dependencies and final class files for forbidden library references; this does not prove compatibility with every other mod.

Preserve the included blocks' behavior and Create integration, including track-aligned bogey placement, native style cycling, assembly data, doors, seats and assisted vehicle-part placement. Registration and models alone do not constitute a functional port. Keep any unresolved behavior gaps explicit.

## Code

Use Java 21 and NeoForge 1.21.1 with Create. Organize code by responsibility. Prefer descriptive English names and direct implementations. Evaluate splitting at roughly 300 lines and prioritize splitting around 500 lines. Avoid unnecessary abstractions, commented-out implementations and unrelated formatting. Comments are exceptional, short English explanations of constraints or reasons. Preserve upstream license notices. Generated files must be maintained through their source generator.

## Validation

The implementer runs compilation, packaging, generation and static resource/recipe checks. The user performs client startup and all in-game verification. Never label compilation as gameplay acceptance. Record actual results and outstanding checks in docs/porting-status.md.

Validate texture references against actual PNG files, model references against model definitions, and OBJ references against geometry/material files. A same-named model must never satisfy a texture dependency. Validate image contents and exercise Create integration APIs separately from recipe/registry checks.

## Work and Git

Work continuously in complete implementation and verification phases; do not request approval for each block. Preserve unrelated work. The user authorizes a Git commit after each complete feature and its appropriate automatic checks. Use English `type (scope): description`, stage only task paths and group complete features. Do not push, rewrite history or change Git configuration. Authorization from another repository does not transfer here.
