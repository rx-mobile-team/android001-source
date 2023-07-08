package com.rxmobileteam.lecture9sample

import android.app.Activity
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SharedBetweenFragmentsInAnActivity @Inject constructor(
  activity: Activity
) {
  private val appCompatActivity = activity as AppCompatActivity

  init {
    Log.d("XXX", "$this created with $appCompatActivity")

    appCompatActivity.lifecycle.addObserver(object : LifecycleEventObserver {
      override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("XXX", "${this@SharedBetweenFragmentsInAnActivity} -> $event")
      }
    })
  }

  fun doSomething(from: Any?) {
    Log.d("XXX", "$this doSomething $from")
  }
}