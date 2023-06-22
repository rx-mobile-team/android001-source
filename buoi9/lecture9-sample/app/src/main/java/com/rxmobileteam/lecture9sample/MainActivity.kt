package com.rxmobileteam.lecture9sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.rxmobileteam.lecture9sample.features.feeds.FeedsFragment

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState === null) {
      supportFragmentManager.commit {
        setReorderingAllowed(true)
        add<FeedsFragment>(
          containerViewId = R.id.container,
          tag = "FeedsFragment",
        )
      }
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
  }
}