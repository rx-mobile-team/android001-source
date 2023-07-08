package com.rxmobileteam.lecture9sample.di

import android.content.Context
import com.rxmobileteam.lecture9sample.utils.ConsoleLoggerImpl
import com.rxmobileteam.lecture9sample.utils.FileSystemLoggerImpl
import com.rxmobileteam.lecture9sample.utils.Logger
import com.rxmobileteam.lecture9sample.utils.MySharePref
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharePrefModule = module {
  single { androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }

//  factory { MySharePref(get()) }


  single<Logger>(named("LoggerConsole")) { ConsoleLoggerImpl() }
  single<Logger>(named("LoggerFileSystem")) { FileSystemLoggerImpl() }


}