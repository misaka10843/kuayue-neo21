# Create: Electro-Energetics compatibility

Validated API target: user's `electroenergetics-1.21.1-1.1.4.jar`, with source inspected read-only in `D:/misaka10843/Downloads/Create-Electro-Energetics-1.21.1`. No CEE classes or assets are bundled, and CEE is not a required runtime dependency. String-targeted optional Mixins apply only when its classes exist. Kuayue's own wires, electrical attachment system and pantographs remain excluded.

## Catenary mounting

CEE's `CatenaryHolderBlockEntity.getPoleWidth` recognizes Kuayue pillars, trusses and their owned structural companions. `PoleMountBlock.canSurvive` also accepts those structures. CEE retains ownership of its holder/bracket models, connections and electrical behavior. Replacing or removing the structural support still invokes CEE's ordinary neighbor validation.

## Pantograph base on decorative roofs

The requested adjustment concerns the mounted base, not the raised arm height. CEE 1.1.4's base geometry starts 5/16 block above its origin. When the support immediately below is a Kuayue block with a top surface within its own cell, the adapter translates the complete pantograph by `supportTop - 1 - 5/16`. A half-height roof therefore uses -13/16. Ordinary supports from other namespaces retain CEE's default behavior.

The adapter shifts stationary rendering, selection/collision shape, electrical terminal positions and contact search/connector positions together. The offset is written to block-entity NBT for Create assembly. Contraption rendering and connector/search positions use that saved value; the server's train catenary circuit applies it before pitch/yaw transforms. Existing extension, double-arm pairing, color, current simulation and controls remain CEE's implementations. This does not add a new pantograph block or alter maximum extension.

Seven CEE target classes successfully transform in an isolated data run with the supplied JAR. A real server GameTest verifies the half-height roof offset and its saved NBT, pole-mount survival and catenary holder recognition. Native curve, registry and recipe checks also pass with CEE loaded. Those checks do not render the model or prove moving-train electricity, Sable/sublevel behavior or another CEE version; those remain explicit game checks.

```powershell
./gradlew.bat runData '-PcompatMod=run/mods/electroenergetics-1.21.1-1.1.4.jar' --no-configuration-cache
./gradlew.bat runGameTestServer '-PcompatMod=run/mods/electroenergetics-1.21.1-1.1.4.jar' '-PschematicFixture=build/schematic-inspection.json' --no-configuration-cache
```

The compatibility JAR is loaded only into that requested development runtime. It is not copied into user saves or the final artifact.
