package com.example.readbooks.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode

class GlobalTestConfig: AbstractProjectConfig() {
	override fun extensions() = listOf(SpringExtension)
}