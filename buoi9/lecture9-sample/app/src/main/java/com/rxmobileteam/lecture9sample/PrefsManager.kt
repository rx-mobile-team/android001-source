package com.rxmobileteam.lecture9sample

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PrefsModule {
  companion object {
    @Provides
    @Singleton
    fun providePrefs(
      @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences("app_prefs", MODE_PRIVATE)
  }
}

@Singleton
class PrefsManager @Inject constructor(
  private val prefs: SharedPreferences,
) {
  private val scope = CoroutineScope(Dispatchers.IO)

  fun isDarkMode(): Boolean = prefs.getBoolean(IS_DARK_MODE_KEY, false)

  fun toggle() {
    scope.launch {
      prefs.edit(commit = true) {
        putBoolean(IS_DARK_MODE_KEY, !isDarkMode())
      }
    }
  }

  companion object {
    const val IS_DARK_MODE_KEY = "is_dark_mode"
  }
}