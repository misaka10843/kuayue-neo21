# NeoKuayue 1.21.1 port

## Included

Registered vehicle parts for 22, 25B/G/K/T/Z, shared 25, M1/JY30, CR200J, DF11, DF11G, DF21, DF5, HXD3D, SS3, SS8, JY290, C70 and NX70. Include interiors, seats, lights, underbody equipment, CR logo and 240km decoration. Include registered passenger, freight, locomotive and meter-gauge bogeys and their directional, powered and andesite variants, including QJ and DFH21.

## Excluded

Overhead lines, ground facilities, platform walls/doors, new tracks, pantographs, driving controllers/seats, editable signs and company customization. No blueprint workstation, blueprint items, technology gates or unrelated food, ore generation and fluid systems. No old-world upgrade compatibility.

## Implementation

Keep kuayueneo21, Java 21 and NeoForge 1.21.1. Adapt to Create without importing the old registration library. Separate common vehicle behavior, families, interiors, bogeys, rendering and generation. Preserve door/window interaction, animation, seating, lights and necessary contraption behavior. Preserve meter-gauge installation semantics and document dependencies on excluded track systems.

All obtainable blocks require reachable survival recipes. Preserve suitable vanilla/Create processing, remove blueprint operations and retain only the minimal material closure. Replace otherwise unavailable ingredients consistently. Generate recipes, loot and tags and validate references and reachability.

## Functional clarification

The port includes behavior, not only registration and visual assets. Bogeys must participate in Create's native track placement, wrench style selection, station assembly, train data and rendering. Preserve assisted placement of panels, floors and skirts, and interaction/collision of multi-block doors on moving contraptions. Validate PNG and model dependencies as different resource types.

The user confirmed that the separate legacy custom coupling/uncoupling system is deferred. The current scope restores vehicle blocks and native Create integration; it does not add that custom train-management subsystem.

## Delivery

Complete implementation as one continuous phase, then run consolidated automated verification and fixes. Deliver the JAR, actual status and a gameplay checklist. Do not stop for per-block approval.

The implementer handles build, generation, resource/recipe validation and static client/server boundary checks. The user handles client startup, visuals, placement, interaction, save/reload, Create assembly/driving/disassembly and actual production. Record untested gameplay explicitly.
# 2026-10-25 implementation source update

Use the official sibling `KuaYue` 1.21.1 code and assets as the primary reference. Replace KasugaLib, its injection/registration/resource plugins and Micronaut with native NeoForge initialization and Create integration. Do not shade or relocate the removed frameworks. Preserve the previously confirmed scope, survival production and verification division. Where the official checkout lacks selected behavior, retain the functional implementation and document the difference in `docs/official-port.md` and `src/porting/official-source.json`.

