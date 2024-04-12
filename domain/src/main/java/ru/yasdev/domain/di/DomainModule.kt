package ru.yasdev.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.yasdev.domain.lastRequest.useCases.GetLastRequestIdUseCase
import ru.yasdev.domain.lastRequest.useCases.SaveLastRequestIdUseCase
import ru.yasdev.domain.requestsDb.useCases.AddRequestUseCase
import ru.yasdev.domain.requestsDb.useCases.DeleteRequestUseCase
import ru.yasdev.domain.requestsDb.useCases.GetListOfRequestsUseCase
import ru.yasdev.domain.requestsDb.useCases.GetRequestUseCase
import ru.yasdev.domain.requestsDb.useCases.UpdateRequestUseCase
import ru.yasdev.domain.sendRequest.SendRequestUseCase

val domainModule = module {
    factoryOf(::AddRequestUseCase)
    factoryOf(::DeleteRequestUseCase)
    factoryOf(::GetLastRequestIdUseCase)
    factoryOf(::GetListOfRequestsUseCase)
    factoryOf(::GetRequestUseCase)
    factoryOf(::SaveLastRequestIdUseCase)
    factoryOf(::UpdateRequestUseCase)
    factoryOf(::SendRequestUseCase)
}