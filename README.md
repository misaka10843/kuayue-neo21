## info (WIP)

一个将跨越模组的大部分方块先移植到1.21.1 neoforge的mod

为了不让自己等的太久所搓出来的mod，可能与跨越的小部分功能不同

此mod仅仅打算移植对应的方块而不移植其控制/受电弓(请使用机械动力：电力学)等功能

## 当前版本

1.2.0 使用官方 `kuayue` mod ID 与资源命名空间，支持范围内原方块 ID 的 Create 结构蓝图。保留去库化及原生列车联动，具体适配差异见 [官方源码适配与去库化](docs/official-port.md)，反馈修复和验证见 [当前状态](docs/porting-status.md)。旧版自定义车钩联挂／解编按确认暂缓。

已移入 398 个车辆与内饰方块、53 个转向架变体和 484 条制作配方。蓝图和科技解锁已移除，客户端与游戏内效果等待用户验收。米轨转向架保留独立轨道类型，本轮没有移植米轨轨道。

运行环境：Minecraft 1.21.1、NeoForge 21.1.264、Java 21、Create 6.0.10 及其匹配依赖。完整依赖见 [资源来源与维护](docs/resource-provenance.md)。

## 构建与检查

普通构建使用仓库内的生成资源，不需要旧项目或官方源码目录：

```powershell
./gradlew.bat build
```

修改配方源、语言源或生成规则后，使用已安装的 uv 执行：

```powershell
./gradlew.bat generatePortData
./gradlew.bat verifyPortResources runData build
```

`runData` 只执行注册表和数据检查，输出到 `build/datagen`，不会清理 `src/generated/resources`。JAR 位于 `build/libs/`。

若当前 Windows 桌面环境出现 `Unable to establish loopback connection`，可仅为构建进程指定已存在的短临时路径，例如 `$env:JAVA_TOOL_OPTIONS='-Djdk.net.unixdomain.tmpdir=D:/Temp'`。不要为此修改系统 Java 或全局设置。

## 项目文档

- [仓库约束](AGENTS.md)
- [移植范围](docs/porting-plan.md)
- [完成情况与自动验证](docs/porting-status.md)
- [游戏内验收清单](docs/gameplay-checklist.md)
- [资源来源、许可证与生成入口](docs/resource-provenance.md)
- [官方 1.21.1 源码适配与去库化](docs/official-port.md)

原 NeoKuayue 代码的 MIT 许可证与资源的 CC-BY-SA-4.0 许可证保存在 `src/main/resources/META-INF/licenses/` 并随 JAR 分发。
