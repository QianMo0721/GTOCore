package com.gtocore.common.data.translation

import com.gtocore.api.misc.AutoInitialize
import com.gtocore.common.machine.electric.ElectricHeaterMachine
import com.gtocore.common.machine.multiblock.storage.MultiblockCrateMachine
import com.gtocore.common.machine.noenergy.BoilWaterMachine
import com.gtocore.common.machine.noenergy.HeaterMachine
import com.gtocore.utils.ComponentListSupplier
import com.gtocore.utils.toLiteralSupplier
import com.gtocore.utils.translatedTo

object GTOMachineTranslation : AutoInitialize<GTOMachineTranslation>() {
    val BoilWaterMachineTooltips = ComponentListSupplier {
        add("需要外部热源工作" translatedTo "Requires external heat source to operate") { aqua() }
        add(("当蒸汽溢出后继续工作会" translatedTo "When steam overflows, continuing to work will ") + ("爆炸" translatedTo "explode").red().bold()) { aqua() }
        add(("可能发生爆炸的临界温度为" translatedTo "The critical temperature for explosion is ") + (BoilWaterMachine.DrawWaterExplosionLine.toLiteralSupplier()).red().bold()) { aqua() }
    }
    val PerformanceMonitorMachineTooltips = ComponentListSupplier {
        add("能监测全部机器2秒内的平均延迟" translatedTo "Can monitor all machines' average delay within 2 seconds and support highlighting") { aqua() }
        add("右键点击机器以打开性能监测界面" translatedTo "Right click on the machine to open performance monitoring interface") { gray() }
    }

    val HeaterMachineTooltips = ComponentListSupplier {
        add("通过燃烧对四周机器进行加热" translatedTo "Burning to heat up around machines") { aqua() }
        add(ComponentSlang.TemperatureMax(HeaterMachine.MaxTemperature))
        add("前方被阻挡后停止加热" translatedTo "Stop heating after front side is blocked.") { aqua() }
        add("根据温度发出红石信号" translatedTo "Emits redstone signal according to the temperature.") { aqua() }
        add(ComponentSlang.Star(1) + ("机器过热会" translatedTo "When machine is too hot, it will ") + ComponentSlang.Explosion) { aqua() }
        add(ComponentSlang.BewareOfBurns)
    }

    val ElectricHeaterMachineTooltips = ComponentListSupplier {
        add("使用电力对四周机器进行加热" translatedTo "Use electricity to heat up around machines") { aqua() }
        add(ComponentSlang.TemperatureMax(ElectricHeaterMachine.MaxTemperature))
        add(ComponentSlang.Star(1) + ("此机器不会爆炸" translatedTo "This machine will not explode")) { aqua() }
        add(ComponentSlang.BewareOfBurns)
    }

    val MultiblockCrateMachineTooltips = ComponentListSupplier {
        add("多方块箱子" translatedTo "Multiblock Crate") { aqua() }
        add("可以存储大量物品" translatedTo "Can store many many items") { aqua() }
        add("右键点击以打开界面" translatedTo "Right click to open the interface") { gray() }
        add(ComponentSlang.Capacity(MultiblockCrateMachine.Capacity.toString())) { aqua() }
    }

    // 等静压成型机
    val IsostaticPressMachineTooltips = ComponentListSupplier {
        add(("先进的材料学技术一直以来都是格雷科技公司的立身之本" translatedTo "Advanced materials technology has always been the foundation of GregTech Corp")) { aqua() }
        add("被广泛应用的先进工业陶瓷一直是公司的拳头产品" translatedTo "Advanced industrial ceramics widely used in various fields are the company's flagship products") { aqua() }
        add(("型号CML-202等静压成型机外表与百年前无异" translatedTo "Model CML-202 isostatic press looks no different from its predecessors a century ago")) { gray() }
        add("但其先进自动化成型技术和工艺早已不可同日而语" translatedTo "But its advanced automated forming technology and processes are incomparable") { gray() }
        add(
            ("输入" translatedTo "Input ") + ("陶瓷粉原料和粘合剂" translatedTo "ceramic powder and binder").yellow().bold() +
                ("后可完美输出陶瓷粗坯" translatedTo ", and output ceramic blanks with perfect yield"),
        ) { aqua() }
        add(ComponentSlang.Star(1) + ("高效率制作陶瓷粗坯" translatedTo "Efficiently produce ceramic blanks").rainbowSlow().bold().italic()) { white() }
    }

    // 烧结炉
    val SinteringFurnaceTooltips = ComponentListSupplier {
        add(("作为陶瓷生产中的" translatedTo "As the ") + ("核心设备" translatedTo "core equipment").gold().bold()) { aqua() }
        add("格雷科技设计人员为这台烧结炉奋战了无数日夜" translatedTo "GregTech designers fought countless days and nights for this sintering furnace") { aqua() }
        add(
            ("依托" translatedTo "Relying on ") + ("先进的温度管理系统" translatedTo "advanced temperature management system").yellow() +
                ("和加热结构设计" translatedTo " and heating structure design"),
        ) { gray() }
        add(("型号HCS-41烧结炉有着完美的成品率" translatedTo "Model HCS-41 sintering furnace has perfect finished product rate")) { gray() }
        add("生产出的优质陶瓷将成为工业帝国的坚固基石" translatedTo "The high-quality ceramics produced will become the solid foundation of industrial empire") { green() }
        add(ComponentSlang.Star(1) + ("将陶瓷粗坯烧制成成品陶瓷" translatedTo "Sinter ceramic blanks into finished ceramics").rainbowSlow().bold().italic()) { white() }
    }

    // 大型热解炉
    val LargePyrolysisOvenTooltips = ComponentListSupplier {
        add(
            ("进入" translatedTo "Entering the ") + ("大机器时代" translatedTo "large machine era").gold() +
                ("，小型热解炉产能已无法满足需要" translatedTo ", small pyrolysis ovens can no longer meet the demand"),
        ) { aqua() }
        add(("针对不断提高的" translatedTo "Addressing the increasing ") + ("木材处理需求" translatedTo "wood processing demand").yellow().bold()) { aqua() }
        add(("HCL-104型大型连续式热解炉应运而生" translatedTo "Model HCL-104 large continuous pyrolysis oven was developed")) { gray() }
        add(
            ("以" translatedTo "Famous for its ") + ("低廉的造价" translatedTo "low cost").green() +
                ("和" translatedTo " and ") + ("极高的可靠性" translatedTo "extremely high reliability").green() + ("而闻名" translatedTo ""),
        ) { gray() }
        add("在先进温控系统加持下为产业链输送海量基础材料" translatedTo "Under advanced temperature control system, it delivers massive basic materials for the industrial chain") { green() }
        add(
            ("使用" translatedTo "Using ") + ("更高级的线圈" translatedTo "higher-grade coils").yellow().bold() +
                ("可以提高处理速度" translatedTo " can increase processing speed"),
        ) { aqua() }
        add(ComponentSlang.Star(1) + ("大批量木材加工的最佳选择" translatedTo "Best choice for large-scale wood processing").gold().bold().italic()) { white() }
    }

    // 光伏电站 (PG-11)
    val PhotovoltaicPlant11Tooltips = ComponentListSupplier {
        add(("格雷科技致力于" translatedTo "GregTech is committed to ") + ("能源获取形式的多样化" translatedTo "diversifying energy acquisition methods").yellow()) { aqua() }
        add(
            ("太阳能光伏发电" translatedTo "Solar photovoltaic power generation").gold() +
                ("是公司的研究方向之一" translatedTo " is one of the company's research directions"),
        ) { aqua() }
        add(
            ("型号PG-11光伏电站技术" translatedTo "Model PG-11 photovoltaic plant technology").gold() +
                ("起初被员工试用" translatedTo " was initially tested by employees"),
        ) { gray() }
        add("由于复杂的生产材料要求和较差发电能力常被冷落" translatedTo "Often neglected due to complex material requirements and poor power generation") { gray() }
        add(
            ("技术人员偶然发现它能" translatedTo "Technicians accidentally discovered it can ") +
                ("高效采集魔力" translatedTo "efficiently collect mana").green().bold(),
        ) { green() }
        add(
            ("改进后的PMG-11" translatedTo "The improved PMG-11").gold() +
                ("以另一种身份被广泛使用" translatedTo " is widely used in another capacity"),
        ) { green() }
        add(ComponentSlang.RecommendedToUse(("生产魔力" translatedTo "mana production").toLiteralSupplier())) { aqua() }
        add(ComponentSlang.Star(1) + ("魔力采集的高效设备" translatedTo "Efficient equipment for mana collection").gold().bold().italic()) { white() }
    }

    // 光伏电站 (PG-12)
    val PhotovoltaicPlant12Tooltips = ComponentListSupplier {
        add(("格雷科技致力于" translatedTo "GregTech is committed to ") + ("能源获取形式的多样化" translatedTo "diversifying energy acquisition methods").yellow()) { aqua() }
        add(
            ("太阳能光伏发电" translatedTo "Solar photovoltaic power generation").gold() +
                ("是公司的研究方向之一" translatedTo " is one of the company's research directions"),
        ) { aqua() }
        add(
            ("型号PG-12光伏电站技术" translatedTo "Model PG-12 photovoltaic plant technology").gold() +
                ("起初被员工试用" translatedTo " was initially tested by employees"),
        ) { gray() }
        add("由于复杂的生产材料要求和较差发电能力常被冷落" translatedTo "Often neglected due to complex material requirements and poor power generation") { gray() }
        add(
            ("技术人员偶然发现它能" translatedTo "Technicians accidentally discovered it can ") +
                ("高效采集魔力" translatedTo "efficiently collect mana").green().bold(),
        ) { green() }
        add(
            ("改进后的PMG-12" translatedTo "The improved PMG-12").gold() +
                ("以另一种身份被广泛使用" translatedTo " is widely used in another capacity"),
        ) { green() }
        add(ComponentSlang.RecommendedToUse(("生产魔力" translatedTo "mana production").toLiteralSupplier())) { aqua() }
        add(ComponentSlang.Star(1) + ("中级魔力采集设备" translatedTo "Intermediate mana collection equipment").gold().bold().italic()) { white() }
    }

    // 光伏电站 (PG-13)
    val PhotovoltaicPlant13Tooltips = ComponentListSupplier {
        add(("格雷科技致力于" translatedTo "GregTech is committed to ") + ("能源获取形式的多样化" translatedTo "diversifying energy acquisition methods").yellow()) { aqua() }
        add(
            ("太阳能光伏发电" translatedTo "Solar photovoltaic power generation").gold() +
                ("是公司的研究方向之一" translatedTo " is one of the company's research directions"),
        ) { aqua() }
        add(
            ("型号PG-13光伏电站技术" translatedTo "Model PG-13 photovoltaic plant technology").gold() +
                ("起初被员工试用" translatedTo " was initially tested by employees"),
        ) { gray() }
        add("由于复杂的生产材料要求和较差发电能力常被冷落" translatedTo "Often neglected due to complex material requirements and poor power generation") { gray() }
        add(
            ("技术人员偶然发现它能" translatedTo "Technicians accidentally discovered it can ") +
                ("高效采集魔力" translatedTo "efficiently collect mana").green().bold(),
        ) { green() }
        add(
            ("改进后的PMG-13" translatedTo "The improved PMG-13").gold() +
                ("以另一种身份被广泛使用" translatedTo " is widely used in another capacity"),
        ) { green() }
        add(ComponentSlang.RecommendedToUse(("生产魔力" translatedTo "mana production").toLiteralSupplier())) { aqua() }
        add(ComponentSlang.Star(1) + ("高级魔力采集设备" translatedTo "Advanced mana collection equipment").gold().bold().italic()) { white() }
    }

    // 能量注入仪
    val EnergyInjectorTooltips = ComponentListSupplier {
        add(("电池箱充电太慢？" translatedTo "Battery box charging too slow?").yellow().bold()) { yellow() }
        add(
            ("针对中后期越来越大的" translatedTo "Addressing the increasing ") +
                ("充放电需求" translatedTo "charging and discharging demands").yellow().bold(),
        ) { aqua() }
        add(
            ("格雷科技隆重推出" translatedTo "GregTech proudly introduces the ") +
                ("SCL-1000大型能量注入仪" translatedTo "SCL-1000 large energy injector").gold().bold(),
        ) { aqua() }
        add(("全新设计的超级快充系统" translatedTo "Brand new super fast charging system").green().bold()) { gray() }
        add(
            ("使用" translatedTo "Using ") + ("高能外壳" translatedTo "high-energy shell").yellow() +
                ("和" translatedTo " and ") + ("超导材料" translatedTo "superconducting materials").yellow(),
        ) { gray() }
        add(
            ("足以使用高压能源仓为各种设备" translatedTo "Sufficient to use high-voltage energy storage for ") +
                ("快速充能" translatedTo "rapid charging").green().bold(),
        ) { green() }
        add(("\"充电1秒钟，工作一整年\"" translatedTo "\"Charge for 1 second, work for a whole year\"").gold().italic()) { white() }
        add(ComponentSlang.Star(1) + ("快速充电的最佳选择" translatedTo "Best choice for fast charging").rainbowSlow().bold().italic()) { white() }
        add(ComponentSlang.Star(1) + ("可为物品充电，还可消耗电力修复物品耐久" translatedTo "Can to charge items, Can consume electricity to repair item durability.").gold().bold().italic()) { white() }
    }

    // 渔场
    val FishingFarmTooltips = ComponentListSupplier {
        add(("喜欢吃鱼？" translatedTo "Like eating fish?").yellow().bold()) { yellow() }
        add(
            ("AFFL-200智能大型渔场" translatedTo "AFFL-200 intelligent large fishing farm").gold() +
                ("是舌尖上的格雷系列常客" translatedTo " is a regular on GregTech cuisine series"),
        ) { aqua() }
        add(
            ("强大的" translatedTo "Powerful ") + ("智能养殖系统" translatedTo "intelligent breeding system").yellow() +
                ("带来强大产能" translatedTo " brings powerful productivity"),
        ) { aqua() }
        add(
            ("能够满足整个分公司员工的" translatedTo "Can meet the entire branch office employees' ") +
                ("水产食用需求" translatedTo "aquatic food consumption needs").green(),
        ) { gray() }
        add("产出的各种水产品在处理后" translatedTo "Various aquatic products produced can become") { gray() }
        add(
            ("同样可以成为工业产线上的" translatedTo "key ") +
                ("关键原料" translatedTo "raw materials on industrial production lines").yellow().bold(),
        ) { green() }
        add(("\"纯工业，零天然\"" translatedTo "\"Pure industrial, zero natural\"").gold().italic()) { white() }
        add(ComponentSlang.Star(1) + ("水产品和工业原料的双重来源" translatedTo "Dual source of aquatic products and industrial materials").gold().bold().italic()) { white() }
    }

    // 培养缸
    val CulturingTankTooltips = ComponentListSupplier {
        add(("科技发展的脚步滚滚向前" translatedTo "The pace of technological development rolls forward")) { aqua() }
        add(("在传统电子技术登峰造极之后" translatedTo "After traditional electronic technology reached its peak")) { aqua() }
        add(
            ("格雷科技将目光放到" translatedTo "GregTech set its sights on ") +
                ("鲸鱼座T星的异星藻类" translatedTo "alien algae from Cetus T star").lightPurple().bold() + ("上" translatedTo ""),
        ) { gray() }
        add(
            ("这种藻类有着" translatedTo "This algae has ") +
                ("奇特的群体意识" translatedTo "peculiar collective consciousness").green().bold(),
        ) { gray() }
        add(
            ("技术人员创造性地使用它们开发出" translatedTo "Technicians creatively used them to develop ") +
                ("全新体制芯片" translatedTo "chips of entirely new architecture").gold().bold(),
        ) { green() }
        add(
            ("AFMS-05培养缸" translatedTo "AFMS-05 culturing tank").gold() +
                ("为培养生物细胞材料量身打造" translatedTo " is tailor-made for cultivating biological cell materials"),
        ) { green() }
        add(
            ("过滤器等级" translatedTo "Filter tier").yellow().bold() +
                ("决定配方等级" translatedTo " determines recipe tier"),
        ) { aqua() }
        add(
            ("玻璃等级" translatedTo "Glass tier").yellow().bold() +
                ("决定可用电压上限" translatedTo " determines maximum usable voltage"),
        ) { aqua() }
        add(ComponentSlang.Star(1) + ("生物材料培养的基础设施" translatedTo "Basic infrastructure for biological material cultivation").gold().bold().italic()) { white() }
    }

    // 大型培养缸
    val LargeCulturingTankTooltips = ComponentListSupplier {
        add(
            ("随着" translatedTo "As ") + ("生物材料" translatedTo "biological materials").yellow() +
                ("被广泛用于各种机器中" translatedTo " are widely used in various machines"),
        ) { aqua() }
        add(
            ("老旧的" translatedTo "The old ") + ("ABMS-05培养缸" translatedTo "ABMS-05 culturing tank").gold() +
                ("产能早已不能满足需求" translatedTo "'s capacity can no longer meet demand"),
        ) { aqua() }
        add(
            ("针对越发庞大的" translatedTo "Addressing the increasingly massive ") +
                ("生物材料产能需求" translatedTo "biological material production demand").yellow().bold(),
        ) { gray() }
        add(
            ("ABFL-411大型培养缸" translatedTo "ABFL-411 large culturing tank").gold().bold() +
                ("被开发出来" translatedTo " was developed"),
        ) { gray() }
        add(
            ("拥有" translatedTo "Features ") + ("更大的培养罐" translatedTo "larger culture tanks").green() +
                ("，" translatedTo " and ") + ("更先进的培养管理系统" translatedTo "more advanced cultivation management system").green(),
        ) { green() }
        add(
            ("在体积没有显著提升的情况下" translatedTo "Without significant volume increase, ") +
                ("极大提高生产效率" translatedTo "greatly improves production efficiency").green().bold(),
        ) { green() }
        add(
            ("过滤器等级" translatedTo "Filter tier").yellow().bold() +
                ("决定配方等级" translatedTo " determines recipe tier"),
        ) { aqua() }
        add(
            ("玻璃等级" translatedTo "Glass tier").yellow().bold() +
                ("决定可用电压上限" translatedTo " determines maximum usable voltage"),
        ) { aqua() }
        add(ComponentSlang.Star(1) + ("大规模生物材料生产设施" translatedTo "Large-scale biological material production facility").gold().bold().italic()) { white() }
    }

    // 复合式蒸馏分馏塔
    val CompoundDistillationTowerTooltips = ComponentListSupplier {
        add(("这是现代炼金术的圣殿" translatedTo "This is the temple of modern alchemy").lightPurple().bold()) { lightPurple() }
        add(("原油的暗夜在此裂解出光的碎片" translatedTo "The crude oil's darkness cracks into fragments of light here").gold().italic()) { gold() }
        add(("用几何的刚毅线条书写分子世界的缱绻情书" translatedTo "Writing tender love letters of the molecular world with geometric rigid lines").lightPurple().italic()) { lightPurple() }
        add(("最炽热的交融终将成就最极致的纯粹" translatedTo "The most intense fusion will ultimately achieve the most extreme purity").rainbow().bold()) { rainbow() }
        add(
            ("格雷科技" translatedTo "GregTech's ") + ("化工设计部门" translatedTo "chemical design department").yellow() +
                ("的最新力作" translatedTo " latest masterpiece"),
        ) { aqua() }
        add(("DHG-1020复合式蒸馏分馏塔" translatedTo "DHG-1020 compound distillation fractionation tower").gold().bold()) { aqua() }
        add(
            ("设计部门为其设计了" translatedTo "The design department created an ") +
                ("极为精巧的结构" translatedTo "extremely sophisticated structure").green().bold(),
        ) { gray() }
        add(("几乎完美的运行程序" translatedTo "Near-perfect operating procedures").green().bold()) { gray() }
        add(
            ("能以" translatedTo "Can ") + ("极高效率" translatedTo "extremely high efficiency").green().bold() +
                ("同时承担蒸发和蒸馏处理工作" translatedTo " simultaneously handle evaporation and distillation"),
        ) { green() }
        add(
            ("是" translatedTo "It is ") +
                ("最强大的蒸馏蒸发设施" translatedTo "the most powerful distillation and evaporation facility").gold().bold(),
        ) { gold() }
        add(ComponentSlang.Star(1) + ("极高效率的多功能化工处理设施" translatedTo "Highly efficient multi-functional chemical processing facility").gold().bold().italic()) { white() }
    }

    // 纳米蜂群电路组装工厂
    val NanoswarmCircuitAssemblyFactoryTooltips = ComponentListSupplier {
        add(("在绝对寂静的空间里" translatedTo "In absolutely silent space").lightPurple().bold()) { lightPurple() }
        add(("一场微观宇宙的创世仪式正在上演" translatedTo "A creation ceremony of the microscopic universe is being performed").rainbow().italic()) { rainbow() }
        add(
            ("纳米蜂群工厂" translatedTo "Nanoswarm factory").gold().bold() +
                ("——这里没有熔炉的咆哮" translatedTo " - there is no roar of furnaces here"),
        ) { aqua() }
        add(("没有机械臂的挥舞" translatedTo "No waving of mechanical arms")) { aqua() }
        add(
            ("只有" translatedTo "Only ") + ("亿万纳米机器人" translatedTo "billions of nanobots").yellow().bold() +
                ("以光的语言低语" translatedTo " whispering in the language of light").gold().italic(),
        ) { gold() }
        add(
            ("在" translatedTo "At ") + ("原子尺度" translatedTo "atomic scale").green().bold() +
                ("编织着电子文明的神经网络" translatedTo " weaving the neural networks of electronic civilization").rainbow().bold(),
        ) { rainbow() }
        add(("ASMEG-5000电路组装工厂" translatedTo "ASMEG-5000 circuit assembly factory").gold().bold()) { aqua() }
        add(("电路组装设施的巅峰之作" translatedTo "The pinnacle of circuit assembly facilities").gold().bold()) { gold() }
        add(
            ("采用大量的" translatedTo "Uses massive ") + ("纳米机器人" translatedTo "nanobots").yellow().bold() +
                ("完成" translatedTo " to complete ") + ("纳米级别组装加工" translatedTo "nanoscale assembly and processing").green().bold(),
        ) { green() }
        add(
            ("它们如同" translatedTo "They are like ") + ("辛勤的工人" translatedTo "diligent workers").green() +
                ("，在硅片中建造" translatedTo ", building ") + ("微观城市" translatedTo "microscopic cities").green().italic(),
        ) { green() }
        add(("为工业帝国不断输送" translatedTo "Continuously supplying ") + ("神经" translatedTo "nerves").gold().bold()) { gold() }
        add(ComponentSlang.Star(1) + ("最高级电路组装的终极设备" translatedTo "Ultimate equipment for highest-grade circuit assembly").rainbowSlow().bold().italic()) { white() }
    }
}
