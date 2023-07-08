package com.rxmobileteam.lecture9sample.features.search

import android.os.Bundle
import android.view.View
import com.rxmobileteam.lecture9sample.SharedBetweenFragmentsInAnActivity
import com.rxmobileteam.lecture9sample.base.BaseFragment
import com.rxmobileteam.lecture9sample.databinding.FragmentSearchUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchUserFragment :
  BaseFragment<FragmentSearchUsersBinding>(FragmentSearchUsersBinding::inflate) {
  @Inject
  lateinit var sharedBetweenFragmentsInAnActivity: SharedBetweenFragmentsInAnActivity

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharedBetweenFragmentsInAnActivity.doSomething(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  companion object {
    fun newInstance() = SearchUserFragment()
  }
}