package com.gtocore.common.data.translation

import com.gtocore.api.lang.translatedTo
import com.gtocore.api.misc.AutoInitialize

object EnumTranslation : AutoInitialize<EnumTranslation>() {
    val both = ("全部" translatedTo "All").gold()
    val patternHatch = ("样板仓" translatedTo "Pattern Hatch").gold()
    val wirelessConnectionMachine = ("无线连接机" translatedTo "Wireless Connection Machine").gold()
    val other = ("其他" translatedTo "Other").gold()
}
