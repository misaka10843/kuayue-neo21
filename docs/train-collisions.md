# Train impact response

The NeoKuayue reference implements an on-rail speed exchange in `MixinTrain.onCrash`, `ConductorHelper.momentumExchange`, and `ExtensionHelper.gentlyCrash`. This port restores that behavior using Create 6's collision result, native train/storage APIs and NeoForge networking. It does not copy the extension registry or removed library types.

## Behavior

When Create detects a collision involving at least one train containing Kuayue parts, both trains share a graph, and their carriage axes are nearly parallel, closing trains exchange momentum. At least one ported part must be present in loaded contraption blocks or saved block palettes. Head-on trains with opposite facing axes are converted to a shared world direction for the calculation, then converted back to their own signed speeds. Receding contact does not receive another impulse. A brief per-pair cooldown prevents both trains from resolving the same impact twice.

The relative restitution follows the original curve: `min(1, 0.4 / relativeSpeed)` for nonzero relative speed, with velocities measured in blocks/tick. The result conserves momentum and does not create kinetic energy. Speeds are assigned to the corresponding train, including unequal masses and reverse-facing trains.

Mass uses the original dimensionless approximation: one per structural block, 0.6 per item, and 0.4 per mB of fluid. Unloaded carriages use the native saved block list. Mounted item and fluid handlers are read without changing cargo; an uninitialized manager contributes no cargo and is never initialized or swapped by this code. Each carriage has a minimum structural weight of one.

Handled impacts retain graph membership and stay on rails. Navigation is cancelled, schedules pause, stations release the train and target speed resets; the exchanged velocity then moves through Create's normal train tick. Collision speeds and paused state are sent to clients without replacing carriage/storage objects. Drivers may still apply manual controls afterward. Coupler clanks/particles accompany impact; stronger relative impacts retain Create's non-block-breaking explosion effect. Pure Create trains, side crossings, invalid/derailed trains and portal-spanning consists keep native Create handling. This is an on-track longitudinal response, not a separate off-track rigid-body simulation.

Automatic coupling remains limited to its existing same-direction, low-speed, owner/schedule checks. Higher-speed impact response does not imply high-speed coupling. Head-to-head impact rebound is implemented; head-to-head/tail-to-tail topology reversal for coupling remains unfinished.

## Automatic evidence and game checks

- 135 numeric cases check momentum, energy and restitution; known equal/unequal-mass cases check train assignment. A native buffer round trip checks IDs and signed speeds in the velocity packet.
- Headless server tests enter Create's actual private collision method with a controlled collision-detection result, real track nodes/edges and native bogey travelling points. They test unequal mass, opposite axes, contact cooldown expiry, preserved graph/schedule state and native fallback for unrelated trains. This tests response integration; it does not simulate trains approaching on a built railway.
- A native mounted chest with ten iron ingots and a fluid tank with 1,000 mB water verify cargo contribution and unchanged cargo contents.
- Actual approach detection during driving, visible push/rebound, slope/curve behavior, passenger effects, audio, multiplayer and save/reload remain user game checks. The eight original sound clips pass file/reference and Ogg checksum checks; automated checks do not listen to playback.
