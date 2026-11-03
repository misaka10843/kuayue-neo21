# Steam 'n' Rails bogey menu

Supports Steam 'n' Rails Neoforge `0.3.0-beta.2+neoforge-mc1.21.1`, matching the supplied Railway 1.21 development source. Railways is optional and is a compile-only dependency; its implementation is not bundled. The tested runtime uses Create 6.0.10 and NeoForge 21.1.264.

Hold a railway casing and open Railways' bogey selection menu with its configured key. Eight KuaYue categories contain 53 individually named models, including directional, powered, andesite and meter variants. Each entry has a single-size menu style pointing to the existing block and renderer, so choosing one model cannot cycle through other models during placement. Existing block IDs, default styles and native wrench cycle groups remain unchanged.

The menu uses Railways' public `BogeyMenuManager` API. Styles are registered during queued common setup after Railways loads; only this mod's own categories are used. Placement on KuaYue tracks reads the same server-side menu selection as Railways' normal track placement. Saved bogey and carriage data use Create's existing style serialization.

Meter gauge remains KuaYue's independent track type, not Railways narrow gauge. A standard-gauge menu model selected on meter track falls back to MKZ; a meter model selected on standard track falls back to Create's standard style, preserving KuaYue's normal default on its own standard track. These explicit mappings prevent fitting a meter bogey to standard track. Other Railways gauge conversions retain Railways' existing behavior and do not promise a KuaYue model for unsupported gauges.

Without Railways, menu aliases are not added to the style registry and the existing placement and wrench behavior remain available.

## Sources and automatic checks

Reference: supplied Railway `0.3.0-beta.2` source, particularly `api/bogeymenu/v0/BogeyMenuManager`, `entry/CategoryEntry`, `registry/CRBogeyStyles`, `content/bogey_menu/handler/BogeyMenuHandlerServer`, and `mixin/MixinTrackBlock` / `MixinStationBlockEntity`. The integration calls these APIs; it does not copy their framework or menu implementation.

The runtime regression uses the released `0.3.0-beta.2` JAR. It checks each menu entry once, all 53 variants on native standard / KuaYue standard / KuaYue meter track with two orientations (318 checks), gauge validity, 53 saved-style round trips and 53 native carriage-style checks. A separate run without Railways checks optional loading and absence of alias registrations. Client menu layout, previews, mouse selection, station placement, wrench cycling and assembled animation remain user game checks.
