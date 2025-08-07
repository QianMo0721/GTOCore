package com.gtocore.common.data.translation

import com.gtocore.api.misc.AutoInitialize
import com.gtocore.common.data.translation.ComponentSlang.Bar
import com.gtocore.common.data.translation.ComponentSlang.OutTopic
import com.gtocore.common.data.translation.ComponentSlang.Star
import com.gtocore.common.data.translation.ComponentSlang.Tab
import com.gtocore.common.data.translation.ComponentSlang.Warning
import com.gtocore.common.machine.electric.ElectricHeaterMachine
import com.gtocore.common.machine.multiblock.storage.MultiblockCrateMachine
import com.gtocore.common.machine.noenergy.BoilWaterMachine
import com.gtocore.common.machine.noenergy.HeaterMachine
import com.gtocore.utils.ComponentListSupplier
import com.gtocore.utils.initialize
import com.gtocore.utils.toLiteralSupplier
import com.gtocore.utils.translatedTo

object GTOMachineTranslation : AutoInitialize<GTOMachineTranslation>() {
    val pattern = ("样板" translatedTo "Patterns").initialize()
    val BoilWaterMachineTooltips = ComponentListSupplier {
        setTranslationPrefix("boil_water_machine")
        add("需要外部热源工作" translatedTo "Requires external heat source to operate") { aqua() }
        add(("当蒸汽溢出后继续工作会" translatedTo "When steam overflows, continuing to work will ") + ("爆炸" translatedTo "explode").red().bold()) { aqua() }
        add(("可能发生爆炸的临界温度为" translatedTo "The critical temperature for explosion is ") + (BoilWaterMachine.DrawWaterExplosionLine.toLiteralSupplier()).red().bold()) { aqua() }
    }
    val PerformanceMonitorMachineTooltips = ComponentListSupplier {
        setTranslationPrefix("performance_monitor_machine")
        add("能监测全部机器2秒内的平均延迟" translatedTo "Can monitor all machines' average delay within 2 seconds and support highlighting") { aqua() }
        add("右键点击机器以打开性能监测界面" translatedTo "Right click on the machine to open performance monitoring interface") { gray() }
    }

    val HeaterMachineTooltips = ComponentListSupplier {
        setTranslationPrefix("heater_machine")
        add("通过燃烧对四周机器进行加热" translatedTo "Burning to heat up around machines") { aqua() }
        add(ComponentSlang.TemperatureMax(HeaterMachine.MaxTemperature))
        add("前方被阻挡后停止加热" translatedTo "Stop heating after front side is blocked.") { aqua() }
        add("根据温度发出红石信号" translatedTo "Emits redstone signal according to the temperature.") { aqua() }
        add(Star(1) + ("机器过热会" translatedTo "When machine is too hot, it will ") + ComponentSlang.Explosion) { aqua() }
        add(ComponentSlang.BewareOfBurns)
    }

    val ElectricHeaterMachineTooltips = ComponentListSupplier {
        setTranslationPrefix("electric_heater_machine")
        add("使用电力对四周机器进行加热" translatedTo "Use electricity to heat up around machines") { aqua() }
        add(ComponentSlang.TemperatureMax(ElectricHeaterMachine.MaxTemperature))
        add(Star(1) + ("此机器不会爆炸" translatedTo "This machine will not explode")) { aqua() }
        add(ComponentSlang.BewareOfBurns)
    }

    val MultiblockCrateMachineTooltips = ComponentListSupplier {
        setTranslationPrefix("multiblock_crate_machine")
        add("多方块箱子" translatedTo "Multiblock Crate") { aqua() }
        add("可以存储大量物品" translatedTo "Can store many many items") { aqua() }
        add("右键点击以打开界面" translatedTo "Right click to open the interface") { gray() }
        add(ComponentSlang.Capacity(MultiblockCrateMachine.Capacity.toString())) { aqua() }
    }

    // 等静压成型机
    val IsostaticPressMachineTooltips = ComponentListSupplier {
        setTranslationPrefix("isostatic_press_machine")
        add(("先进的材料学技术一直以来都是格雷科技公司的立身之本" translatedTo "Advanced materials technology has always been the foundation of GregTech Corp")) { aqua() }
        add("被广泛应用的先进工业陶瓷一直是公司的拳头产品" translatedTo "Advanced industrial ceramics widely used in various fields are the company's flagship products") { aqua() }
        add(("型号CML-202等静压成型机外表与百年前无异" translatedTo "Model CML-202 isostatic press looks no different from its predecessors a century ago")) { gray() }
        add("但其先进自动化成型技术和工艺早已不可同日而语" translatedTo "But its advanced automated forming technology and processes are incomparable") { gray() }
        add("输入§e§l陶瓷粉原料和粘合剂§r后可完美输出陶瓷粗坯" translatedTo "Input §e§lceramic powder and binder§r to perfectly output ceramic blanks") { aqua() }
        add(Star(1) + ("高效率制作陶瓷粗坯" translatedTo "Efficiently produce ceramic blanks").rainbowSlow().bold().italic()) { white() }
    }

    // 烧结炉
    val SinteringFurnaceTooltips = ComponentListSupplier {
        setTranslationPrefix("sintering_furnace")
        add(("作为陶瓷生产中的" translatedTo "As the ") + ("核心设备" translatedTo "core equipment").gold().bold()) { aqua() }
        add("格雷科技设计人员为这台烧结炉奋战了无数日夜" translatedTo "GregTech designers fought countless days and nights for this sintering furnace") { aqua() }
        add("依托先进的§e温度管理系统和加热结构§r设计" translatedTo "§eadvanced temperature management system§r Relying on and heating structure design") { gray() }
        add(("型号HCS-41烧结炉有着完美的成品率" translatedTo "Model HCS-41 sintering furnace has perfect finished product rate")) { gray() }
        add("生产出的优质陶瓷将成为工业帝国的坚固基石" translatedTo "The high-quality ceramics produced will become the solid foundation of industrial empire") { green() }
        add(Star(1) + ("将陶瓷粗坯烧制成成品陶瓷" translatedTo "Sinter ceramic blanks into finished ceramics").rainbowSlow().bold().italic()) { white() }
    }

    // 大型热解炉
    val LargePyrolysisOvenTooltips = ComponentListSupplier {
        setTranslationPrefix("large_pyrolysis_oven")
        add("进入§6大型化时代§r后小型热解炉产能已无法满足需要" translatedTo "After entering the era of §6large-scale§r machines, small pyrolysis ovens can no longer meet the demand") { aqua() }
        add("针对不断提高的§e§l木材处理需求" translatedTo "Addressing the increasing §e§lwood processing demand") { aqua() }
        add("HCL-104型大型连续式热解炉应运而生" translatedTo "Model HCL-104 large continuous pyrolysis oven was developed") { gray() }
        add("以§a低廉的造价§r§7和§a极高的可靠性§r§7而闻名" translatedTo "Famous for its §alow cost§r and §aextremely high reliability§r") { gray() }
        add("在先进温控系统加持下为产业链输送海量基础材料" translatedTo "Under advanced temperature control system, it delivers massive basic materials for the industrial chain") { green() }
        add("使用§e§l更高级的线圈§r§b可以提高处理速度" translatedTo "Using §e§lhigher-grade coils§r§b can increase processing speed") { aqua() }
        add(Star(1) + ("§6§l§o大批量木材加工的最佳选择" translatedTo "Best choice for large-scale wood processing")) { white() }
    }

    // 催化剂仓
    val CatalystHatchTooltips = ComponentListSupplier {
        setTranslationPrefix("catalyst_hatch")
        add(Star(1) + ("使用催化剂仓输入催化剂" translatedTo "Use catalyst hatch to input catalysts")) { aqua() }
        add(Tab(1) + ("可以不消耗催化剂，让催化剂重复使用" translatedTo "Can be used without consuming catalysts, allowing catalysts to be reused")) { gray() }
    }

    // ME存储访问仓
    val MEStorageAccessHatchTooltips = ComponentListSupplier {
        setTranslationPrefix("me_storage_access_hatch")
        add(Star(1) + ("访问ME存储器内的存储" translatedTo "Access storage in ME storage")) { aqua() }
        add(Tab(1) + ("直接让ME线缆连上就好，不推荐无线连接" translatedTo "Directly let ME cable connect, not recommended to use wireless connection")) { gray() }
    }

    // ME存储访问仓代理
    val MEStorageAccessHatchProxyTooltips = ComponentListSupplier {
        setTranslationPrefix("me_storage_access_hatch_proxy")
        add(Star(1) + ("是ME存储访问仓的克隆体" translatedTo "Is a clone of ME storage access hatch")) { aqua() }
        add(Tab(1) + ("需要绑定在ME存储访问仓" translatedTo "Need to be bound to ME storage access hatch")) { gray() }
        add(Tab(1) + ("甚至可以跨纬度绑定" translatedTo "Even can be bound across dimensions")) { gray() }
        add(Tab(1) + ("这意味着你可以不使用量子环，来跨纬度访问ME存储器了" translatedTo "That means you can no longer use a quantum ring to cross dimensions to access ME storage")) { gray() }
    }

    // 合成样板仓
    val MeCraftPatternHatchTooltips = ComponentListSupplier {
        setTranslationPrefix("me_craft_pattern_part_machine")
        add(Star(1) + ("合成样板仓用于存储合成样板" translatedTo "Synthesis template hatch is used to store synthesis templates")) { aqua() }
        add(Star(1) + ("配合超级分子装配室使用" translatedTo "use it Combined with Super Molecular Assembly")) { aqua() }
        add(ComponentSlang.Capacity(72.toString())) { aqua() }
    }

    // ME可编程样板总成
    val MeProgrammablePatternAssemblyTooltips = ComponentListSupplier {
        setTranslationPrefix("me_programmable_pattern_assembly")
        add(Star(1) + ("可以使用虚拟物品提供器进行编程" translatedTo "Can be programmed using virtual item provider")) { aqua() }
        add(Tab(1) + ("一台机器，一个总成搞定所有样板" translatedTo "One machine, one pattern hatch ,finished.")) { gray() }
    }

    // ME样板总成
    val MePatternHatchTooltips = {capacity: Int-> ComponentListSupplier {
            setTranslationPrefix("me_pattern_assembly")
            add(Star(1) + ("可以放入样板，并进行一键发配" translatedTo "Can put patterns and distribute them one click")) { aqua() }
            add(Tab(1) + ("样板间分别隔离，互不干扰" translatedTo "Patterns are isolated from each other, do not interfere with each other")) { gray() }
            add(Tab(1) + ("对着样板按鼠标中键可单独设置电路或者提供特别输入" translatedTo "Press the middle mouse button on the pattern to set the circuit or provide special input")) { gray() }
            add(OutTopic(1) + ("集成式的ME样板供应器" translatedTo "Integrated ME pattern provider")) { gold() }
            add(ComponentSlang.Capacity(capacity.toString())) { aqua() }
        }
    }.initialize()

    // 监控器系列
    val monitor = { init:ComponentListSupplier.()->Unit -> ComponentListSupplier {
        setTranslationPrefix("monitor")
        init()
        add(Star(1) + ("监控器系列" translatedTo "Monitor series")) { rainbowSlow() }
    } }

    // 基础监控器
    val BasicMonitorTooltips = monitor {
        add("是监控器的基础组成部分" translatedTo "Is the basic component of the monitor") { aqua()}
        add("可以与其他§6多个监控器§r系列相连" translatedTo "Can be connected to other §6multiple monitors§r series") { aqua() }
        add("多个监控器可以组成一个大屏" translatedTo "Multiple monitors can form a large screen") { aqua() }
        add("可以使用§d喷漆§r对监控器分组" translatedTo "Can use§d paint spray§r to group monitors") { aqua() }
        add(Star(1) + ("与§6监控器组件§r相连来显示不同信息" translatedTo "Use §6monitor components§r to display different information")) { aqua() }
    }
    // 监控器电网组件
    val MonitorPowerComponentTooltips = monitor {
        add("用于显示§6无线电网§r的数据在监控器上" translatedTo "Used to display§6 wireless energy grid data§r on the monitor") { aqua() }
    }
    // 监控器魔力组件
    val MonitorManaComponentTooltips = monitor {
        add("用于显示§6无线魔力网§r的数据在监控器上" translatedTo "Used to display§6 wireless mana grid data§r on the monitor") { aqua() }
    }
    // 监控器算力组件
    val MonitorComputingComponentTooltips = monitor {
        add("用于显示§6无线算力网§r的数据在监控器上" translatedTo "Used to display§6 wireless computing grid data§r on the monitor") { aqua() }
    }
    // 监控器ME吞吐量监控组件
    val MonitorMEThroughputComponentTooltips = monitor {
        add("用于显示ME中§6指定物品§r的吞吐量数据在监控器上" translatedTo "Used to display the throughput data of §6specified items §rin ME in the monitor") { aqua() }
    }
    // 监控器机器通用组件
    val MonitorMachineComponentTooltips = monitor {
        add("用于显示§6机器§r的通用数据在监控器上" translatedTo "Used to display the general data of §6machines§r on the monitor") { aqua() }
        add("例如耗电，产电，输入，输出等" translatedTo "For example, power consumption, power production, input, output, etc.") { aqua() }
        add("需要放入坐标信息卡" translatedTo "Need to put coordinate card") { aqua() }
    }
    // 监控器合成处理单元组件
    val MonitorCraftingComponentTooltips = monitor {
        add("用于显示§6ME合成处理单元§r的合成数据在监控器上" translatedTo "Used to display the synthesis data of §6crafting unit§r on the monitor") { aqua() }
    }
    // 监控器自定义文本组件
    val MonitorCustomTextComponentTooltips = monitor {
        add("用于显示§6自定义文本§r在监控器上" translatedTo "Used to display§6 custom text§r on the monitor") { aqua() }
        add("同一块监控器大屏的多个文本组件将被顺序显示" translatedTo "Multiple text components of the same block monitor screen will be displayed in sequence") { aqua() }
    }

    // 光伏电站 (PG-11)
    val PhotovoltaicPlant11Tooltips = ComponentListSupplier {
        setTranslationPrefix("photovoltaic_plant_11")
        add("格雷科技致力于§e能源获取形式的多样化" translatedTo "GregTech is committed to §ediversifying energy acquisition methods") { aqua() }
        add("§6太阳能光伏发电§r§b是公司的研究方向之一" translatedTo "§6Solar photovoltaic power generation§r is one of the company's research directions") { aqua() }
        add("§6型号PG-11光伏电站技术§r§7起初被员工试用" translatedTo "§6Model PG-11 photovoltaic plant technology§r was initially tested by employees") { gray() }
        add("由于复杂的生产材料要求和较差发电能力常被冷落" translatedTo "Often neglected due to complex material requirements and poor power generation") { gray() }
        add("技术人员偶然发现它能§a§l高效采集魔力" translatedTo "Technicians accidentally discovered it can §a§lefficiently collect mana") { green() }
        add("§6改进后的PMG-11§r§a以另一种身份被广泛使用" translatedTo "The §6improved PMG-11§r is widely used in another capacity") { green() }
        add(ComponentSlang.RecommendedUseAs(("生产魔力" translatedTo "mana production"))) { aqua() }
        add(Star(1) + ("§6§l§o魔力采集的高效设备" translatedTo "Efficient equipment for mana collection")) { aqua() }
    }

    // 光伏电站 (PG-12)
    val PhotovoltaicPlant12Tooltips = ComponentListSupplier {
        setTranslationPrefix("photovoltaic_plant_12")
        add("格雷科技致力于§e能源获取形式的多样化" translatedTo "GregTech is committed to §ediversifying energy acquisition methods") { aqua() }
        add("§6太阳能光伏发电§r§b是公司的研究方向之一" translatedTo "§6Solar photovoltaic power generation§r is one of the company's research directions") { aqua() }
        add("§6型号PG-12光伏电站技术§r§7起初被员工试用" translatedTo "§6Model PG-12 photovoltaic plant technology§r was initially tested by employees") { gray() }
        add("由于复杂的生产材料要求和较差发电能力常被冷落" translatedTo "Often neglected due to complex material requirements and poor power generation") { gray() }
        add("技术人员偶然发现它能§a§l高效采集魔力" translatedTo "Technicians accidentally discovered it can §a§lefficiently collect mana") { green() }
        add("§6改进后的PMG-12§r§a以另一种身份被广泛使用" translatedTo "The §6improved PMG-12§r is widely used in another capacity") { green() }
        add(ComponentSlang.RecommendedUseAs(("生产魔力" translatedTo "mana production"))) { aqua() }
        add(Star(1) + ("§6§l§o中级魔力采集设备" translatedTo "Intermediate mana collection equipment")) { aqua() }
    }

    // 光伏电站 (PG-13)
    val PhotovoltaicPlant13Tooltips = ComponentListSupplier {
        setTranslationPrefix("photovoltaic_plant_13")
        add("格雷科技致力于§e能源获取形式的多样化" translatedTo "GregTech is committed to §ediversifying energy acquisition methods") { aqua() }
        add("§6太阳能光伏发电§r§b是公司的研究方向之一" translatedTo "§6Solar photovoltaic power generation§r is one of the company's research directions") { aqua() }
        add("§6型号PG-13光伏电站技术§r§7起初被员工试用" translatedTo "§6Model PG-13 photovoltaic plant technology§r was initially tested by employees") { gray() }
        add("由于复杂的生产材料要求和较差发电能力常被冷落" translatedTo "Often neglected due to complex material requirements and poor power generation") { gray() }
        add("技术人员偶然发现它能§a§l高效采集魔力" translatedTo "Technicians accidentally discovered it can §a§lefficiently collect mana") { green() }
        add("§6改进后的PMG-13§r§a以另一种身份被广泛使用" translatedTo "The §6improved PMG-13§r is widely used in another capacity") { green() }
        add(ComponentSlang.RecommendedUseAs(("生产魔力" translatedTo "mana production"))) { aqua() }
        add(Star(1) + ("§6§l§o高级魔力采集设备" translatedTo "Advanced mana collection equipment")) { aqua() }
    }

    // 能量注入仪
    val EnergyInjectorTooltips = ComponentListSupplier {
        setTranslationPrefix("energy_injector")
        add("§e§l电池箱充电太慢？" translatedTo "§e§lBattery box charging too slow?") { yellow() }
        add("针对中后期越来越大的§e§l充放电需求" translatedTo "Addressing the increasing §e§lcharging and discharging demands") { aqua() }
        add("格雷科技隆重推出§6§lSCL-1000大型能量注入仪" translatedTo "GregTech proudly introduces the §6§lSCL-1000 large energy injector") { aqua() }
        add("§a§l全新设计的超级快充系统" translatedTo "§a§lBrand new super fast charging system") { gray() }
        add("使用§e高能外壳§r§7和§e超导材料" translatedTo "Using §ehigh-energy shell§r and §esuperconducting materials") { gray() }
        add("足以使用高压能源仓为各种设备§a§l快速充能" translatedTo "Sufficient to use high-voltage energy storage for §a§lrapid charging") { green() }
        add("§6§o\"充电1秒钟，工作一整年\"" translatedTo "§6§o\"Charge for 1 second, work for a whole year\"") { white() }
        add(Star(1) + ("快速充电的最佳选择" translatedTo "Best choice for fast charging").rainbowSlow().bold().italic()) { aqua() }
        add(Star(1) + ("§6§l§o可为物品充电，还可消耗电力修复物品耐久" translatedTo "Can to charge items, Can consume electricity to repair item durability")) { aqua() }
    }

    // 渔场
    val FishingFarmTooltips = ComponentListSupplier {
        setTranslationPrefix("fishing_farm")
        add("§e§l喜欢吃鱼？" translatedTo "§e§lLike eating fish?") { yellow() }
        add("§6AFFL-200智能大型渔场§r§b是舌尖上的格雷系列常客" translatedTo "§6AFFL-200 intelligent large fishing farm§r is a regular on GregTech cuisine series") { aqua() }
        add("强大的§e智能养殖系统§r§b带来强大产能" translatedTo "Powerful §eintelligent breeding system§r brings powerful productivity") { aqua() }
        add("能够满足整个分公司员工的§a水产食用需求" translatedTo "Can meet the entire branch office employees' §aaquatic food consumption needs") { gray() }
        add("产出的各种水产品在处理后" translatedTo "Various aquatic products produced can become") { gray() }
        add("同样可以成为工业产线上的§e§l关键原料" translatedTo "key §e§lraw materials on industrial production lines") { green() }
        add("§6§o\"纯工业，零天然\"" translatedTo "§6§o\"Pure industrial, zero natural\"") { white() }
        add(Star(1) + ("§6§l§o水产品和工业原料的双重来源" translatedTo "Dual source of aquatic products and industrial materials")) { aqua() }
    }

    // 培养缸
    val CulturingTankTooltips = ComponentListSupplier {
        setTranslationPrefix("culturing_tank")
        add("科技发展的脚步滚滚向前" translatedTo "The pace of technological development rolls forward") { aqua() }
        add("在传统电子技术登峰造极之后" translatedTo "After traditional electronic technology reached its peak") { aqua() }
        add("格雷科技将目光放到§d§l鲸鱼座T星的异星藻类§r§7上" translatedTo "GregTech set its sights on §d§lalien algae from Cetus T star§r") { gray() }
        add("这种藻类有着§a§l奇特的群体意识" translatedTo "This algae has §a§lpeculiar collective consciousness") { gray() }
        add("技术人员创造性地使用它们开发出§6§l全新体制芯片" translatedTo "Technicians creatively used them to develop §6§lchips of entirely new architecture") { green() }
        add("§6AFMS-05培养缸§r§a为培养生物细胞材料量身打造" translatedTo "§6AFMS-05 culturing tank§r is tailor-made for cultivating biological cell materials") { green() }
        add("§e§l过滤器等级§r§b决定配方等级" translatedTo "§e§lFilter tier§r determines recipe tier") { aqua() }
        add("§e§l玻璃等级§r§b决定可用电压上限" translatedTo "§e§lGlass tier§r determines  upper limit of voltage usable") { aqua() }
        add(Star(1) + ("生物材料培养的基础设施" translatedTo "Basic infrastructure for biological material cultivation").gold().bold().italic()) { aqua() }
    }

    // 大型培养缸
    val LargeCulturingTankTooltips = ComponentListSupplier {
        setTranslationPrefix("large_culturing_tank")
        add("随着§e生物材料§r被广泛用于各种机器中" translatedTo "As §ebiological materials§r are widely used in various machines") { aqua() }
        add("老旧的§6ABMS-05培养缸§r产能早已不能满足需求" translatedTo "The old §6ABMS-05 culturing tank§r's capacity can no longer meet demand") { aqua() }
        add("针对越发庞大的§e§l生物材料产能需求§r" translatedTo "Addressing the increasingly massive §e§lbiological material production demand§r") { gray() }
        add("§6§lABFL-411大型培养缸§r被开发出来" translatedTo "§6§lABFL-411 large culturing tank§r was developed") { gray() }
        add("拥有§a更大的培养罐§r，§a更先进的培养管理系统§r" translatedTo "Features §alarger culture tanks§r and §amore advanced cultivation management system§r") { green() }
        add("在体积没有显著提升的情况下§a§l极大提高生产效率§r" translatedTo "Without significant volume increase, §a§lgreatly improves production efficiency§r") { green() }
        add("§e§l过滤器等级§r决定配方等级" translatedTo "§e§lFilter tier§r determines recipe tier") { aqua() }
        add("§e§l玻璃等级§r决定可用电压上限" translatedTo "§e§lGlass tier§r determines maximum usable voltage") { aqua() }
        add(Star(1) + ("§6§l§o大规模生物材料生产设施§r" translatedTo "§6§l§oLarge-scale biological material production facility§r")) { aqua() }
    }

    // 复合式蒸馏分馏塔
    val CompoundDistillationTowerTooltips = ComponentListSupplier {
        setTranslationPrefix("compound_distillation_tower")
        add("§d§l这是现代炼金术的圣殿§r" translatedTo "§d§lThis is the temple of modern alchemy§r") { lightPurple() }
        add("§6§o原油的暗夜在此裂解出光的碎片§r" translatedTo "§6§oThe crude oil's darkness cracks into fragments of light here§r") { gold() }
        add("§d§o用几何的刚毅线条书写分子世界的缱绻情书§r" translatedTo "§d§oWriting tender love letters of the molecular world with geometric rigid lines§r") { lightPurple() }
        add("§l最炽热的交融终将成就最极致的纯粹§r" translatedTo "§lThe most intense fusion will ultimately achieve the most extreme purity§r") { aqua() }
        add("格雷科技§e化工设计部门§r的最新力作" translatedTo "GregTech's §echemical design department§r latest masterpiece") { aqua() }
        add("§6§lDHG-1020复合式蒸馏分馏塔§r" translatedTo "§6§lDHG-1020 compound distillation fractionation tower§r") { aqua() }
        add("设计部门为其设计了§a§l极为精巧的结构§r" translatedTo "The design department created an §a§lextremely sophisticated structure§r") { gray() }
        add("§a§l几乎完美的运行程序§r" translatedTo "§a§lNear-perfect operating procedures§r") { gray() }
        add("能以§a§l极高效率§r同时承担蒸发和蒸馏处理工作" translatedTo "Can§a§l extremely high efficiency§r simultaneously handle evaporation and distillation") { green() }
        add("是§6§l最强大的蒸馏蒸发设施§r" translatedTo "It is §6§lthe most powerful distillation and evaporation facility§r") { gold() }
        add(Star(1) + ("§6§l§o极高效率的多功能化工处理设施§r" translatedTo "§6§l§oHighly efficient multi-functional chemical processing facility§r")) { aqua() }
    }

    // 纳米蜂群电路组装工厂
    val NanoswarmCircuitAssemblyFactoryTooltips = ComponentListSupplier {
        setTranslationPrefix("nanoswarm_circuit_assembly_factory")
        add("§d§l在绝对寂静的空间里§r" translatedTo "§d§lIn absolutely silent space§r") { lightPurple() }
        add("§o一场微观宇宙的创世仪式正在上演§r" translatedTo "§oA creation ceremony of the microscopic universe is being performed§r") { return@add rainbow() }
        add("§6§l纳米蜂群工厂§r——这里没有熔炉的咆哮" translatedTo "§6§lNanoswarm factory§r - there is no roar of furnaces here") { aqua() }
        add("没有机械臂的挥舞" translatedTo "No waving of mechanical arms") { aqua() }
        add("只有§e§l亿万纳米机器人§r§6§o以光的语言低语§r" translatedTo "Only §e§lbillions of nanobots§r §6§owhispering in the language of light§r") { gold() }
        add("在§a§l原子尺度§r编织着电子文明的§l神经网络§r" translatedTo "At §a§latomic scale§r weaving the §lneural networks of electronic civilization§r") { rainbow() }
        add("§6§lASMEG-5000电路组装工厂§r" translatedTo "§6§lASMEG-5000 circuit assembly factory§r") { aqua() }
        add("§6§l电路组装设施的巅峰之作§r" translatedTo "§6§lThe pinnacle of circuit assembly facilities§r") { gold() }
        add("采用大量的§e§l纳米机器人§r完成§a§l纳米级别组装加工§r" translatedTo "Uses massive §e§lnanobots§r to complete §a§lnanoscale assembly and processing§r") { green() }
        add("它们如同§a辛勤的工人§r，在硅片中建造§a§o微观城市§r" translatedTo "They are like §adiligent workers§r, building §a§omicroscopic cities§r") { green() }
        add("为工业帝国不断输送§6§l神经§r" translatedTo "Continuously supplying §6§lnerves§r") { gold() }
        add(Star(1) + ("§l§o最高级电路组装的终极设备§r" translatedTo "§l§oUltimate equipment for highest-grade circuit assembly§r")) { aqua() }
    }

    // 化工厂
    val ChemicalFactoryTooltips = ComponentListSupplier {
        setTranslationPrefix("chemical_factory")
        add("§d§l在化学的世界里§r" translatedTo "§d§lIn the world of chemistry§r") { lightPurple() }
        add("§o每一个分子都在诉说着自己的故事§r" translatedTo "§oEvery molecule tells its own story§r") { aqua() }
        add("§6§l化工厂§r——这里没有轰鸣的机器" translatedTo "§6§lChemical factory§r - there are no roaring machines here") { aqua() }
        add(ComponentSlang.CoilEfficiencyBonus("§6§l§线圈等级每高出白铜一级能耗与时间减少5%§r" translatedTo "§6§l§oEach coil tier above Cupronickel, Reduces energy consumption and duration by 5%§r"))
    }

    // 衰变加速器
    val DecayAcceleratorTooltips = ComponentListSupplier {
        setTranslationPrefix("decay_accelerator")
        add("§d§l在原子世界的边缘§r" translatedTo "§d§lOn the edge of the atomic world§r") { lightPurple() }
        add("§o时间的流逝被重新定义§r" translatedTo "§oThe passage of time is redefined§r") { aqua() }
        add("§6§l衰变加速器§r——这里没有缓慢的衰变" translatedTo "§6§lDecay Accelerator§r - there is no slow decay here") { aqua() }
        add("没有静止的原子" translatedTo "No stationary atoms") { aqua() }
        add("只有§e§l高速运动的粒子§r在时间的洪流中穿梭" translatedTo "Only §e§lhigh-speed particles§r traversing through the torrent of time") { gold() }
        add("它们如同§a§l时间的使者§r，传递着未来的讯息" translatedTo "They are like §a§ltime's messengers§r, delivering messages from the future") { green() }
        add("为工业帝国不断输送着新的可能性" translatedTo "Continuously supplying new possibilities for the industrial empire") { gold() }
    }

    // 回收机
    val RecyclingMachineTooltips = ComponentListSupplier {
        setTranslationPrefix("recycling_machine")
        add("§d§l在废弃物的世界里§r" translatedTo "§d§lIn the world of waste§r") { lightPurple() }
        add("§o每一件被遗忘的物品都有它的价值§r" translatedTo "§oEvery forgotten item has its value§r") { aqua() }
        add("§6§l回收机§r——这里没有无用的垃圾" translatedTo "§6§lRecycling Machine§r - there is no useless trash here") { aqua() }
        add("没有被丢弃的物品" translatedTo "No discarded items") { aqua() }
        add("只有§e§l被重新赋予生命的材料§r在循环中重生" translatedTo "Only §e§lmaterials reborn with new life§r in the cycle") { gold() }
        add("它们如同§a§l工业的再生者§r，赋予旧物新的使命" translatedTo "They are like §a§lregenerators of industry§r, giving old items new missions") { green() }
        add("为工业帝国不断输送着可持续发展的动力" translatedTo "Continuously supplying sustainable power for the industrial empire") { gold() }
    }

    // 质量发生器
    val MassGeneratorTooltips = ComponentListSupplier {
        setTranslationPrefix("mass_generator")
        add("§d§l在质量的深渊中§r" translatedTo "§d§lIn the abyss of mass§r") { lightPurple() }
        add("§o每一克物质都蕴含着无限的可能性§r" translatedTo "§oEvery gram of matter contains infinite possibilities§r") { aqua() }
        add("§6§l质量发生器§r——这里没有轻视的物质" translatedTo "§6§lMass Generator§r - there is no trivial matter here") { aqua() }
        add("没有被忽视的质量" translatedTo "No ignored mass") { aqua() }
        add("只有§e§l被重新定义的能量§r在宇宙的法则中舞动" translatedTo "Only §e§lenergy redefined by the laws of the universe§r dancing in the cosmic laws") { gold() }
        add("它们如同§a§l宇宙的编织者§r，编织着物质与能量的交响曲" translatedTo "They are like §a§lweavers of the universe§r, weaving the symphony of matter and energy") { green() }
        add("为工业帝国不断输送着新的力量" translatedTo "Continuously supplying new power for the industrial empire") { gold() }
    }

    // 精密组装机
    val PrecisionAssemblerTooltips = ComponentListSupplier {
        setTranslationPrefix("precision_assembler")
        add("§9§lDSW-17型精密组装机§r的运作原理是什么？" translatedTo "What is the operating principle of the §9§lDSW-17 Precision Assembler§r?") { blue() }
        add("§7据说它的§e§l多轴同步控制系统§r源自§d§l星际传输技术§r" translatedTo "§7Its §e§lmulti-axis synchronous control system§r is said to originate from §d§linterstellar transfer technology§r") { gray() }
        add("§o每一块§6§l超导组装平台§r都由专属工程师维护" translatedTo "§oEach §6§lsuperconducting assembly platform§r is maintained by dedicated engineers") { gold() }
        add("§a你是否曾想过，§b每一颗微型芯片§r的诞生，背后有多少§7§lDSW-17型§r的机械臂在默默运作？" translatedTo "§aHave you ever wondered how many §7§lDSW-17§r robotic arms work silently behind the birth of every microchip?") { green() }
        add("§e§l源代码存储于§c§l隔离仓§r，只有持有§d§l工程师徽章§r的人员可访问" translatedTo "§e§lSource code is stored in §c§lisolation vaults§r, accessible only to those with a §d§lengineer badge§r") { yellow() }
    }

    // 熔岩炉
    val LavaFurnaceTooltips = ComponentListSupplier {
        setTranslationPrefix("lava_furnace")
        add(("§b§oSCP-114514——熔岩炉的传说§r" translatedTo "§b§oSCP-114514 — The Legend of the Lava Furnace§r")) { aqua() }
        add(("据说它曾在§7§o格雷科技食堂§r里被用来煮方便面，结果一锅下去，面都变成了岩浆。" translatedTo "Rumor has it, it was once used in the GregTech cafeteria to cook instant noodles, but after one pot, all the noodles turned into lava.")) { gray() }
        add(("§d§o工程师们纷纷表示：‘这锅面有点烫嘴。’§r" translatedTo "§d§oEngineers commented: 'This pot of noodles is a bit too hot.'§r")) { lightPurple() }
        add(("§a§o后来大家发现，只要往里面丢点圆石或石头，就能收获一桶岩浆，简直是工业界的‘岩浆自助餐’！§r" translatedTo "§a§oLater, everyone found that tossing in some cobblestone or stone yields a bucket of lava—truly an 'all-you-can-eat lava buffet' for industry!§r")) { green() }
        add(Warning(1) + (("§7每提供一个任意类型的圆石或石头可输出§c1B§7熔岩" translatedTo "§7Each cobblestone or stone provided outputs §c1B§7 lava").green())) { gray() }
        add(Star(1) + ("用来生产岩浆很方便，但请勿尝试用它来煮面！" translatedTo "Convenient for producing lava, but please do not attempt to cook noodles with it!").bold().italic())
    }

    // 大气收集室
    val AtmosphereCollectorRoomTooltips = ComponentListSupplier {
        setTranslationPrefix("atmosphere_collector_room")
        add(("§b§o传说中的大气收集室，曾被工程师们昵称为‘空气罐头工厂’§r" translatedTo "§b§oThe legendary Atmosphere Collector Room, affectionately called the 'Canned Air Factory' by engineers§r")) { aqua() }
        add(("§7据说有一次，某位新手把午餐忘在了收集室里，结果第二天打开，发现三明治变成了‘高纯度氮气’。" translatedTo "§7Rumor has it, a rookie once left his lunch in the collector room, and the next day, his sandwich had turned into 'high-purity nitrogen'.")) { gray() }
        add(("§a§o工程师们纷纷表示：‘这空气，吸一口能飘起来。’§r" translatedTo "§a§oEngineers commented: 'One breath of this air and you might just float away.'§r")) { green() }
        add(("§e§o后来大家发现，只要把收集室门关紧，连隔壁的猫都能变成‘稀有气体’供应商。" translatedTo "§e§oLater, everyone found that with the door tightly shut, even the neighbor's cat could become a 'rare gas supplier'.")) { yellow() }
        add(Star(1) + ("§7相比小型收集器，收集室能收获更多气体。" translatedTo "§7Compared to small collectors, the room gathers more gases.").italic()) { gray() }
    }

    // 激光蚀刻工厂 星星提示：运行激光焊接配方时速度x5  星星提示：精密激光模式不支持并行处理
    val LaserEtchingFactoryTooltips = ComponentListSupplier {
        setTranslationPrefix("laser_etching_factory")
        add(("§b§o在格雷科技总部，有一台传说中的激光蚀刻工厂§r" translatedTo "§b§oAt GregTech HQ, there is a legendary Laser Etching Factory§r")) { aqua() }
        add(("§7据说有位员工曾用它雕刻午餐盒，结果盒子直接变成了量子芯片。" translatedTo "§7Rumor has it, an employee once tried to engrave his lunchbox, but it turned into a quantum chip.")) { gray() }
        add(("§a§o大家纷纷表示：‘这盒饭，吃一口能通电。’§r" translatedTo "§a§oEveryone commented: 'One bite and you might get a charge.'§r")) { green() }
        add(("§7员工们建议：使用前请先确认午餐盒不是高科技材料。" translatedTo "§7Employees suggest: Before use, make sure your lunchbox isn't made of advanced materials.")) { aqua() }
        add(Star(1) + ("运行激光焊接配方时速度x5" translatedTo "Running laser welding recipes speeds up x5").italic()) { gold() }
        add(Warning(1) + ("精密激光模式不支持并行处理" translatedTo "Precise laser mode does not support parallel processing").italic()) { yellow() }
    }

    // 裂变反应堆
    val FissionReactorTooltips = ComponentListSupplier {
        setTranslationPrefix("fission_reactor")
        add(Star(1) + ("反应堆结构" translatedTo "Reactor structure")) { lightPurple() }
        add(Bar(2) + ("§b燃料组件: §r提供最大并行数量" translatedTo "§bFuel component: §rprovides maximum parallel number")) { gold() }
        add(Bar(2) + ("升温系数为燃料组件相邻数之和加一" translatedTo "Heating coefficient is the sum of adjacent numbers plus one")) { gold() }
        add(Bar(2) + ("§9冷却组件: §r提供最大冷却能力" translatedTo "§9Cooling component: §rprovides maximum cooling capability")) { aqua() }
        add(Bar(2) + ("有效冷却组件必须与燃料组件相邻" translatedTo "Effective cooling component must be adjacent to the fuel component")) { aqua() }
        add(Star(1) + ("温度变化" translatedTo "Temperature change")) { lightPurple() }
        add(Bar(2) + ("§e初始温度为: §c298K§r，每秒增加配方产热 × 升温系数" translatedTo "§eInitial temperature is: §c298K§r, increase the heat produced by the recipe × heating coefficient per second")) { white() }
        add(Bar(2) + ("§e温度上限为: §c1500K§r，抵达后机器开始损坏" translatedTo "§eTemperature limit is: §c1500K§r, after reaching, the machine starts to damage")) { aqua() }
        add(Bar(2) + ("停止工作每秒降低1K" translatedTo "Stops working every second decreases 1K")) { aqua() }
        add(Bar(2) + ("完全损坏时" translatedTo "When completely damaged,the machine will") + ComponentSlang.Explosion) { aqua() }
        add(Star(1) + ("冷却你的反应堆，供给量 ≥ 需求量反应堆温度下降" translatedTo "Cool your reactor, supply ≥ demand, the temperature drops")) { lightPurple() }
        add(Bar(2) + ("§9冷却液类型: §r蒸馏水或钠钾合金" translatedTo "§9Cooling liquid type: §rDistilled water or sodium-potassium alloy")) { aqua() }
        add(Bar(2) + ("§9冷却参数: §r需求量 × 冷却液系数" translatedTo "§9Cooling parameters: §rConsumption × Cooling liquid coefficient per second")) { aqua() }
        add(Bar(2) + ("§9消耗量: §r需求量 × 冷却液系数" translatedTo "§9Consumption: §rDemand × Cooling liquid coefficient per second")) { aqua() }
        add(Bar(2) + ("§9最低需求量: §r配方产热 × 冷却参数 × 实际并行 × 当前温度 / 1500" translatedTo "§9Minimum demand: §rHeat produced by the recipe × Cooling parameters × Actual parallel × Current temperature / 1500")) { aqua() }
        add(Bar(2) + ("§9最高供给量: §r(冷却组件 - 相邻数/3) × 8" translatedTo "§9Maximum supply: §r(Cooling component - adjacent numbers / 3) × 8")) { aqua() }
        add(Star(1) + ("超频反应堆~！" translatedTo "Overclocking your Fission Reactor~!")) { lightPurple() }
        add(Bar(2) + ("§e超频机制: §r触发条件：供给量 >= n × 需求量 (n>1)" translatedTo "§9Overclocking mechanism: §rTrigger condition: Supply >= n × Demand (n>1)")) { white() }
        add(Bar(2) + ("§e超频效果: §r减少n秒配方时间" translatedTo "§9Overclocking effect: §rReduce n seconds of recipe time")) { white() }
        add(Star(1) + ("产出物" translatedTo "Output")) { lightPurple() }
        add(Bar(2) + ("§6蒸馏水冷却: §r产出蒸汽，产量：消耗量 × min(160, 160/(1.4^(373-温度)))" translatedTo "§9Distilled water cooling: §rProduces steam, output: Consumption × min(160, 160/(1.4^(373-Temperature)))")) { green() }
        add(Bar(2) + ("§6钠钾合金冷却: §r温度≤825K：热钠钾合金，温度>825K：超临界钠钾合金" translatedTo "§9Sodium-potassium alloy cooling: §rTemperature≤825K：Hot sodium-potassium alloy, Temperature>825K：Supercritical sodium-potassium alloy")) { green() }
    }

    // （请AI帮我攥写好笑幽默的小故事，诙谐或者幽默的故事，类似SCP风格，但不要出现SCP元素，用语亲切，关键词汇使用较淡的颜色代码）长度：中 科技程度：高
    // 游戏：我的世界 MOD：应用能源，格雷科技 整合包：GTOdyssey 可以称呼玩家为员工之类的
}
