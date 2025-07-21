package com.gtocore.common.item.misc

import com.gtolib.api.annotation.Scanned
import com.gtolib.api.annotation.language.RegisterLanguage

@Scanned
class OrganTranslation {
    @Scanned
    companion object {
        @RegisterLanguage(cn = "使用量", en = "Using")
        val using: String = "gtocore.organ.using"
    }
}
