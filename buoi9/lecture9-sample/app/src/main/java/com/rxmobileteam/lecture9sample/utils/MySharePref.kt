package com.rxmobileteam.lecture9sample.utils

import android.content.SharedPreferences

class MySharePref(private val sharedPreferences: SharedPreferences) {

  fun saveString(key: String, value: String) {
    sharedPreferences.edit().putString(key, value).apply()
  }

  fun getString(key: String): String? {
    return sharedPreferences.getString(key, null)
  }

}