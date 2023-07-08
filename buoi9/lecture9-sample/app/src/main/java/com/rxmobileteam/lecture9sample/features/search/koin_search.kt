package com.rxmobileteam.lecture9sample.features.search

import com.rxmobileteam.lecture9sample.utils.MySharePref
import org.koin.dsl.module

val featureSearch = module {
    scope<SearchActivity> {
      scoped {
        MySharePref(get())
      }
    }
}