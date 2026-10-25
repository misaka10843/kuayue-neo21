# Train coupling and uncoupling

This implementation operates on actual Create trains, carriages, spacing, bogey data, cargo storage and entity bindings. The server authorizes interaction and broadcasts topology changes to clients; the same list operation preserves existing client carriage objects. Subsequent logins and saved worlds use Create's normal railway synchronization and persistence.

## Implemented controls

Successful manual coupling, automatic coupling and uncoupling play the original NeoKuayue `kuayue:coupler` sound event (eight random Vorbis clips, shared by coupling and release in the original implementation) and its short CRIT particle effect. Selection, rejection and failed operations stay silent. The real server broadcasts effects once; clients do not replay them from the topology packet. Source: `ConductorHelper.playEffects` and `resources/generated/systems/train_extension`. Source/resource licenses remain in the packaged notices. The importer maintains sound definitions and files; static checks validate Ogg page checksums and referenced sound paths. Actual playback and volume are user game checks.

- Stop and pause the schedule before using a Create wrench. Only the owner or an operator can manually change the train.
- Wrench a connected end/coupler to split that boundary. Both adjacent carriages must have ported coupling parts. The rear portion becomes a separate train. Carriages, cargo and bogey data retain their identity; the original train keeps its schedule and the new portion receives no duplicate schedule or consumed fuel.
- Wrench an external coupler, then the nearby external coupler on another train within 30 seconds to connect. Both trains must be stopped on the same track graph, in one dimension, with opposite ends selected and matching world orientation. Remove any rear-train schedule before merging. The selected couplers must be within two blocks.
- Automatic low-speed coupling checks same-owner trains with matching orientation and adjacent head/tail couplers. Relative closing speed must be positive and no greater than 0.25 blocks/tick. The rear train must not carry a schedule. Merging runs after the normal server tick to avoid mutating the native train iteration. Station claims and signal occupancy are refreshed.
- Non-wrench door interactions remain available. Companion clicks route to their owning end. Locomotive cowcatchers/heads and freight ends use their NeoKuayue forward offsets.

Source references: NeoKuayue `ConductorHelper` list partition/merge operations, `CouplerInteractionBehaviour`, and conductor-provider offsets. Native Create 6 fields and entity setters replace the removed extension/framework registry.

## Explicit behavior gap

Head-to-head and tail-to-tail coupling require reversing assembled carriages, mounted inventories, actors, seats, passengers and third-party electrical state. They are rejected. The old Forge reversal targets different Create storage/rendering APIs and has not been copied as an unsafe partial remap. This is functional same-direction coupling/uncoupling; restoration of every original orientation remains open. Cross-dimensional coupling and high-speed impact coupling are unsupported.

## Validation

Native data-run checks split and rejoin four actual Create carriages, preserving identity, spacing, train references and bogey data, and reject invalid boundaries/self-merges. Compilation and packet registration are checked with and without CEE. These do not prove multi-client rendering, signal/station behavior, mounted cargo during travel, automatic approach coupling or saved-world reload. Those require user game tests.

## 1.3.1 storage binding fix

The supplied client crash reports `MountedStorageManager.tick` dereferencing null `syncedItems`. Rebinding through `CarriageContraptionEntity.setCarriage` calls `swapStorageAfterAssembly`, replacing the client's already initialized contraption storage proxy with the network carriage's uninitialized server cargo manager. A topology change retains the same carriage objects and track graph, so it now updates only entity train ID/index. It leaves the existing carriage pointer, storage proxy, anchors and client interpolation intact; normal native ticks continue synchronization.

A headless server regression constructs real carriage entities with the client-style empty cargo managers and initialized contraption storage. It merges/splits three times, checks IDs/indexes and storage identity, and invokes native `Contraption.tickStorage` after each cycle. This exercises the crash's storage tick path without launching a client; multi-client gameplay remains pending.
