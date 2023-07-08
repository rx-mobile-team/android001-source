package com.rxmobileteam.lecture9sample.features.feeds

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.rxmobileteam.lecture9sample.SharedBetweenFragmentsInAnActivity
import com.rxmobileteam.lecture9sample.base.BaseFragment
import com.rxmobileteam.lecture9sample.databinding.FragmentFeedsBinding
import com.rxmobileteam.lecture9sample.features.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedsFragment : BaseFragment<FragmentFeedsBinding>(FragmentFeedsBinding::inflate) {
  @Inject
  lateinit var sharedBetweenFragmentsInAnActivity: SharedBetweenFragmentsInAnActivity

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharedBetweenFragmentsInAnActivity.doSomething(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViewPagerAndTabs()
    binding.btnSearch.setOnClickListener {
      Intent(requireContext(), SearchActivity::class.java).apply {
        startActivity(this)
      }
    }
  }

  private fun setupViewPagerAndTabs() {
    binding.run {
      viewPager.adapter = FeedsViewPagerAdapter(this@FeedsFragment)

      TabLayoutMediator(
        tabsLayout,
        viewPager
      ) { tab, position ->
        tab.text = when (position) {
          0 -> "Collections"
          1 -> "Photos"
          else -> error("Unknown position: $position")
        }
      }.attach()
    }
  }
}