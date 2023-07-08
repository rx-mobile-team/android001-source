package com.rxmobileteam.lecture9sample.utils

import android.util.Log

interface Logger {
  fun logMessage(message: String)
}

class ConsoleLoggerImpl : Logger {
  override fun logMessage(message: String) {
    Log.d("Logger", "ConsoleLoggerImpl + $message")
  }
}
class FileSystemLoggerImpl : Logger {
  override fun logMessage(message: String) {
    Log.d("Logger", "FileSystemLoggerImpl + $message")
  }
}
