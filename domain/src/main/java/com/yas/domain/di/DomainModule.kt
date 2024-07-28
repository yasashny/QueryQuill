package com.yas.domain.di

import com.yas.domain.lastRequest.useCases.GetLastRequestIdUseCase
import com.yas.domain.lastRequest.useCases.SaveLastRequestIdUseCase
import com.yas.domain.requestsDb.useCases.AddRequestUseCase
import com.yas.domain.requestsDb.useCases.DeleteRequestUseCase
import com.yas.domain.requestsDb.useCases.GetListOfRequestsUseCase
import com.yas.domain.requestsDb.useCases.GetRequestUseCase
import com.yas.domain.requestsDb.useCases.UpdateRequestUseCase
import com.yas.domain.sendRequest.SendRequestUseCase
import com.yas.domain.settings.GetSettingsUseCase
import com.yas.domain.settings.UpdateSettingsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::AddRequestUseCase)
    factoryOf(::DeleteRequestUseCase)
    factoryOf(::GetLastRequestIdUseCase)
    factoryOf(::GetListOfRequestsUseCase)
    factoryOf(::GetRequestUseCase)
    factoryOf(::SaveLastRequestIdUseCase)
    factoryOf(::UpdateRequestUseCase)
    factoryOf(::SendRequestUseCase)
    factoryOf(::GetSettingsUseCase)
    factoryOf(::UpdateSettingsUseCase)
}