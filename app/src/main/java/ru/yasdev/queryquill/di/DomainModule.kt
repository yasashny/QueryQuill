package ru.yasdev.queryquill.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.yasdev.domain.lastRequest.useCases.GetLastRequestIdUseCase
import ru.yasdev.domain.lastRequest.useCases.SaveLastRequestIdUseCase
import ru.yasdev.domain.requestsDb.useCases.AddRequestUseCase
import ru.yasdev.domain.requestsDb.useCases.DeleteRequestUseCase
import ru.yasdev.domain.requestsDb.useCases.GetListOfRequestsUseCase
import ru.yasdev.domain.requestsDb.useCases.GetRequestUseCase
import ru.yasdev.domain.requestsDb.useCases.UpdateRequestUseCase

val domainModule = module {
    factoryOf(::AddRequestUseCase) { bind<AddRequestUseCase>() }
    factoryOf(::DeleteRequestUseCase) { bind<DeleteRequestUseCase>() }
    factoryOf(::GetLastRequestIdUseCase) { bind<GetLastRequestIdUseCase>() }
    factoryOf(::GetListOfRequestsUseCase) { bind<GetListOfRequestsUseCase>() }
    factoryOf(::GetRequestUseCase) { bind<GetRequestUseCase>() }
    factoryOf(::SaveLastRequestIdUseCase) { bind<SaveLastRequestIdUseCase>() }
    factoryOf(::UpdateRequestUseCase) { bind<UpdateRequestUseCase>() }
}