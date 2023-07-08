package com.rxmobileteam.lecture9sample.di

import com.rxmobileteam.lecture9sample.features.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel {
    SearchViewModel(get())
  }
}