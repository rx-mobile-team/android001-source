package com.rxmobileteam.lecture9sample.features.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.rxmobileteam.lecture9sample.GlideApp
import com.rxmobileteam.lecture9sample.SharedBetweenFragmentsInAnActivity
import com.rxmobileteam.lecture9sample.base.BaseFragment
import com.rxmobileteam.lecture9sample.databinding.FragmentSearchPhotosBinding
import com.rxmobileteam.lecture9sample.extensions.setupVertically
import com.rxmobileteam.lecture9sample.features.feeds.collections.CollectionUiItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchPhotoFragment :
  BaseFragment<FragmentSearchPhotosBinding>(FragmentSearchPhotosBinding::inflate) {

  private val collectionUiItemAdapter by lazy(LazyThreadSafetyMode.NONE) {
    CollectionUiItemAdapter(
      glide = GlideApp.with(this)
    )
  }

  @Inject
  lateinit var sharedBetweenFragmentsInAnActivity: SharedBetweenFragmentsInAnActivity

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharedBetweenFragmentsInAnActivity.doSomething(this)
  }

  private val viewModel by activityViewModels<SearchViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.recyclerViewSearchPhoto.setupVertically(
      collectionUiItemAdapter
    )
    viewModel.resultSearchPhotos.observe(viewLifecycleOwner) {
      collectionUiItemAdapter.submitList(it)
    }
  }


  companion object {
    fun newInstance() = SearchPhotoFragment()
  }
}
