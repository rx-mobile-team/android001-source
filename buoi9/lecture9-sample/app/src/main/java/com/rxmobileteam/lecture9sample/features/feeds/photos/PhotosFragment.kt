package com.rxmobileteam.lecture9sample.features.feeds.photos

import android.os.Bundle
import android.util.Log
import com.rxmobileteam.lecture9sample.PrefsManager
import com.rxmobileteam.lecture9sample.SharedBetweenFragmentsInAnActivity
import com.rxmobileteam.lecture9sample.base.BaseFragment
import com.rxmobileteam.lecture9sample.databinding.FragmentPhotosBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {
  @Inject
  lateinit var sharedBetweenFragmentsInAnActivity: SharedBetweenFragmentsInAnActivity

  @Inject
  lateinit var prefsManager: PrefsManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(this::class.java.simpleName, "prefsManager -> $prefsManager")
    Log.d(this::class.java.simpleName, "prefsManager -> ${prefsManager.isDarkMode()}")
    prefsManager.toggle()

    sharedBetweenFragmentsInAnActivity.doSomething(this)
  }

  companion object {
    fun newInstance() = PhotosFragment()
  }
}