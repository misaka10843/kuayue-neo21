# 来源与资源维护

车辆逻辑、模型、贴图与配方来源为相邻 NeoKuayue 项目。原项目代码采用 MIT，资源采用 CC-BY-SA-4.0；完整原始许可证随 JAR 打包在 `META-INF/licenses/`。本次修改包含 Minecraft/NeoForge/Create 接口适配、命名空间迁移、资源引用修复、去蓝图制作链和补充加工配方。

Java 源码已经完成适配，作为普通源码维护。`legacy` 包保留原有车型与形状结构，公共注册、制作材料、转向架适配、客户端可视化和验证代码放在对应职责目录。

资源中的原始作者与 Blockbench credit 保留。DF11G 驾驶室门原资源引用了不存在的门框模型，本次以原有设备门框为模板并使用驾驶室门贴图补齐；此处外观需要游戏内重点核对。集便装置粒子贴图路径、SS3 重复路径分隔符和旧 OBJ 加载器名称也已修复。

## 可重复生成

- `src/porting/legacy-recipes.json` 是旧配方源快照，供生成器移除蓝图并转换格式。
- `src/porting/languages/` 保存筛选后的原始语言内容，生成时合并当前物品栏和补充物品名称。
- `src/porting/block-tags.json` 保存旧版功能标签源，生成时保留已移植方块及内部延伸方块的有效引用。
- `tools/generate_data.py` 是当前配方、掉落、工具标签、补充物品模型和语言资源的生成入口。
- `tools/verify_port.py` 检查资源引用、物品模型、内部原料与制作可达性。
- `tools/asset_references.py` 按 JSON 字段区分模型、PNG 和 OBJ 文件依赖，并检查 PNG 内容。旧版导入器混淆同名模型与贴图的问题已修复；仅检查路径存在不足以证明资源有效。
- `PortAuditProvider` 在 `runData` 中使用实际注册表和配方 Codec 检查配方解码、方块实体类型与所有方块状态组合。
- `docs/legacy-blocks.json`、`docs/legacy-bogeys.json` 是移植基准清单；`docs/recipe-generation.json` 记录补充制作路径。

`tools/extract_legacy.py`、`tools/import_resources.py` 和 `tools/import_bogeys.py` 用于显式刷新上游快照，正常构建不需要相邻旧项目。资源导入器的 `--prune` 会删除导入输出目录中不在本次清单内的文件，手工资源修订应同步维护导入规则后再使用。

Create 依赖配置依据：[官方 1.21.1 开发文档](https://wiki.createmod.net/developers/depend-on-create/neoforge-1.21.1)。锁定 Create 6.0.10-280、Ponder 1.0.82、Flywheel 1.0.6 和 Registrate MC1.21-1.3.0+67。
# Official 1.21.1 source update

The primary source is now the sibling `KuaYue` checkout. See `docs/official-port.md` and `src/porting/official-source.json` for revision, file fingerprints, adaptations, library replacements and the official/fallback resource split. Both its MIT code license and CC BY-SA resource license are packaged under `META-INF/licenses/KuaYue-*`; original NeoKuayue notices remain for fallback content.

