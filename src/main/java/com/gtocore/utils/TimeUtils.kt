package com.gtocore.utils

import kotlin.time.Duration

fun Duration.toTicks(): Int = (this.inWholeSeconds * 20).toInt()
fun Duration.toSeconds(): Int = (this.inWholeSeconds).toInt()
