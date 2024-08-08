package com.yas.common

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class QQDispatchers {
    Main, IO, Default
}

val commonModule = module {
    factory(named(QQDispatchers.Main)) {
        Dispatchers.Main
    }
    factory(named(QQDispatchers.IO)) {
        Dispatchers.IO
    }
    factory(named(QQDispatchers.Default)) {
        Dispatchers.Default
    }
}