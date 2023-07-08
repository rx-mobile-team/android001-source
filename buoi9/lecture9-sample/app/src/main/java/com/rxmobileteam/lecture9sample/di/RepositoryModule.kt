package com.rxmobileteam.lecture9sample.di

import com.rxmobileteam.lecture9sample.features.search.repository.SearchRepository
import com.rxmobileteam.lecture9sample.features.search.repository.SearchRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
  single<SearchRepository> { SearchRepositoryImpl(get()) }

//  singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
}