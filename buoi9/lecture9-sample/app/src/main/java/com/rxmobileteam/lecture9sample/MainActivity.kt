package com.rxmobileteam.lecture9sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.rxmobileteam.lecture9sample.features.feeds.FeedsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  @Inject
  lateinit var sharedBetweenFragmentsInAnActivity: SharedBetweenFragmentsInAnActivity

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    sharedBetweenFragmentsInAnActivity.doSomething(this)

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
}