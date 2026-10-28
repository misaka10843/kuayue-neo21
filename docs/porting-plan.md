# 最新范围修正：普通方块、轨道、挂摘钩及接触网支撑

2026-10-28 补充：删除盐、盐块、盐矿及深层盐矿和对应生成、加工、掉落与资源，不再包含无车辆／建筑制作用途的盐链。保留实际生产链依赖的材料和普通建筑方块。本模组不额外生成矿石；本条替代此前盐矿纳入范围的要求。

以用户最新说明为准：轨道、站台／地面设施、装饰板、普通材料等方块全部纳入，不再沿用旧的轨道和地面设施排除条款。逐个对照原注册清单，不只修补示例蓝图缺失的 ID。包括标准、无枕、无砟、米轨及护轨，保留完整 Create 放置、弯轨、车站与转向架联动。

挂钩／摘钩本轮加入。接触网柱、桁架等支撑构件纳入，并与机械动力：电力学（Create: Electro-Energetics）的接触网架兼容；跨越自有接触网电气／连线系统及受电弓不移植。驾驶设备、GUI 编辑及公司定制、蓝图工作台和科技解锁仍按既有要求排除或暂缓。旧版本章节中的范围说明仅作历史记录。

电力学受电弓适配的是底座贴合车顶装饰块，不能误解为修改升弓高度。安装偏移应同时覆盖静态和组装后渲染、碰撞、保存数据与电气接触坐标。

蓝图掉落在用户复测中仍存在，必须继续追踪实际 Create 放置路径，不得以策略回放通过宣称游戏修复完成。客户端与游戏测试仍由用户执行；每个完整功能经对应自动检查后提交 Git commit。

2026-10-25 补充：恢复原版挂钩／摘钩共用的车钩音效及粒子；恢复含跨越部件列车的同轨纵向碰撞速度交换／回弹，保留侧撞和无关列车的原生处理。对向车体速度必须换算到同一世界方向，碰撞同步不得替换客户端库存。范围与验证边界见 `docs/train-collisions.md`。

# 1.2.0 用户确认的命名空间与提交规则

从本版起统一使用官方 `kuayue` mod ID／资源命名空间，支持已包含原方块 ID 的 Create 结构蓝图。先前计划中的 `kuayueneo21` 命名空间要求由本条替代。每完成一个完整功能并通过对应自动检查后提交 Git commit；不推送、不改写历史。原有移植范围、去库化要求和客户端测试分工保持有效。

# NeoKuayue 1.21.1 port

## Included

Registered vehicle parts for 22, 25B/G/K/T/Z, shared 25, M1/JY30, CR200J, DF11, DF11G, DF21, DF5, HXD3D, SS3, SS8, JY290, C70 and NX70. Include interiors, seats, lights, underbody equipment, CR logo and 240km decoration. Include registered passenger, freight, locomotive and meter-gauge bogeys and their directional, powered and andesite variants, including QJ and DFH21.

## Excluded

Kuayue's overhead electrical/wire system and pantographs, driving controllers/seats, editable signs and company customization. No blueprint workstation, blueprint items, technology gates, salt chain or unrelated food/fluid systems. Ordinary infrastructure and tracks remain included. No old-world upgrade compatibility.

## Implementation

Keep kuayue, Java 21 and NeoForge 1.21.1. Adapt to Create without importing the old registration library. Separate vehicle behavior, families, interiors, bogeys, infrastructure, rendering and generation. Preserve door/window interaction, animation, seating, lights and contraption behavior. Include meter track and preserve its gauge-specific bogey placement.

All obtainable blocks require reachable survival recipes. Preserve suitable vanilla/Create processing, remove blueprint operations and retain only the minimal material closure. Replace otherwise unavailable ingredients consistently. Generate recipes, loot and tags and validate references and reachability.

## Functional clarification

The port includes behavior, not only registration and visual assets. Bogeys must participate in Create's native track placement, wrench style selection, station assembly, train data and rendering. Preserve assisted placement of panels, floors and skirts, and interaction/collision of multi-block doors on moving contraptions. Validate PNG and model dependencies as different resource types.

The latest user instruction includes train coupling/uncoupling. Implement actual Create topology and synchronization. Keep any remaining orientation or third-party-state gaps explicit in docs/train-coupling.md and the current status.

## Delivery

Complete implementation as one continuous phase, then run consolidated automated verification and fixes. Deliver the JAR, actual status and a gameplay checklist. Do not stop for per-block approval.

The implementer handles build, generation, resource/recipe validation and static client/server boundary checks. The user handles client startup, visuals, placement, interaction, save/reload, Create assembly/driving/disassembly and actual production. Record untested gameplay explicitly.
# 2026-10-25 implementation source update

Use the official sibling `KuaYue` 1.21.1 code and assets as the primary reference. Replace KasugaLib, its injection/registration/resource plugins and Micronaut with native NeoForge initialization and Create integration. Do not shade or relocate the removed frameworks. Preserve the previously confirmed scope, survival production and verification division. Where the official checkout lacks selected behavior, retain the functional implementation and document the difference in `docs/official-port.md` and `src/porting/official-source.json`.
