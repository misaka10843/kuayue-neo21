# Create: Electro-Energetics compatibility

Validated API target: user's `electroenergetics-1.21.1-1.1.4.jar`, with source inspected read-only in `D:/misaka10843/Downloads/Create-Electro-Energetics-1.21.1`. No CEE classes or assets are bundled, and CEE is not a required runtime dependency. String-targeted optional Mixins apply only when its classes exist. Kuayue's own wires, electrical attachment system and pantographs remain excluded.

## Catenary mounting

CEE's `CatenaryHolderBlockEntity.getPoleWidth` recognizes Kuayue pillars, trusses and their owned structural companions. `PoleMountBlock.canSurvive` also accepts those structures. CEE retains ownership of its holder/bracket models, connections and electrical behavior. Replacing or removing the structural support still invokes CEE's ordinary neighbor validation.

## Pantograph base on decorative roofs

The requested adjustment concerns the mounted base, not the raised arm height. CEE 1.1.4 uses separate static block geometry for its feet, insulators and fixed parts, plus dynamic renderer geometry for the bow. The feet start at block Y=0; the dynamic mounting plate starts at Y=5/16. Version 1.3.0 incorrectly moved only the dynamic renderer and subtracted that extra 5/16. Version 1.3.1 translates the complete assembly by `supportTop - 1`, so a half-height roof uses -1/2. Ordinary supports from other namespaces retain CEE's default placement.

The baked-model handler retains all eight original facing/single/double base models, suppresses their original chunk/contraption quads, and draws that geometry with the same translated entity/actor pose as the bow. Inventory models stay unchanged. New assembly NBT includes mount version 2; old nonzero mount offsets are converted by adding back the erroneous 5/16, including already assembled trains.

The adapter shifts stationary rendering, selection/collision shape, electrical terminal positions and contact search/connector positions together. The offset is written to block-entity NBT for Create assembly. Contraption rendering and connector/search positions use that saved value; the server's train catenary circuit applies it before pitch/yaw transforms. Existing extension, double-arm pairing, color, current simulation and controls remain CEE's implementations. This does not add a new pantograph block or alter maximum extension.

Seven CEE target classes successfully transform in an isolated data run with the supplied JAR. A real server GameTest verifies the half-height roof offset, saved NBT and legacy offset conversion, pole-mount survival and catenary holder recognition. A headless baked-model routing audit checks all eight base states and both quad APIs, preserving the original geometry and inventory model. Native curve, registry and recipe checks also pass with CEE loaded. These checks do not render a frame or prove moving-train electricity, Sable/sublevel behavior or another CEE version; those remain explicit game checks.

```powershell
./gradlew.bat runData '-PcompatMod=run/mods/electroenergetics-1.21.1-1.1.4.jar' --no-configuration-cache
./gradlew.bat runGameTestServer '-PcompatMod=run/mods/electroenergetics-1.21.1-1.1.4.jar' '-PschematicFixture=build/schematic-inspection.json' --no-configuration-cache
```

The compatibility JAR is loaded only into that requested development runtime. It is not copied into user saves or the final artifact.
