package org.queryquill.app.feature.cookie.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.queryquill.app.feature.cookie.CookieViewModel

val cookieModule = module {
    viewModelOf(::CookieViewModel)
}