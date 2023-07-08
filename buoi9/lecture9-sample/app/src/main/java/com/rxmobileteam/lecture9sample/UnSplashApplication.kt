package com.rxmobileteam.lecture9sample

import android.app.Application
import com.rxmobileteam.lecture9sample.di.networkModule
import com.rxmobileteam.lecture9sample.di.repositoryModule
import com.rxmobileteam.lecture9sample.di.sharePrefModule
import com.rxmobileteam.lecture9sample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class UnSplashApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    initKoin()
  }

  private fun initKoin() {
    startKoin {
      androidLogger(Level.ERROR)
      androidContext(this@UnSplashApplication)
      modules(
        listOf(
          networkModule,
          repositoryModule,
          viewModelModule,
          sharePrefModule
        )
      )
    }
  }
}