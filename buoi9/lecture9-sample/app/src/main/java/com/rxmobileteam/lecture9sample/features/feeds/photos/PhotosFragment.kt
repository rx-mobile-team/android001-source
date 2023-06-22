package com.rxmobileteam.lecture9sample.features.feeds.photos

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rxmobileteam.lecture9sample.GlideApp
import com.rxmobileteam.lecture9sample.base.BaseFragment
import com.rxmobileteam.lecture9sample.databinding.FragmentPhotosBinding

class PhotosFragment : BaseFragment<FragmentPhotosBinding>(FragmentPhotosBinding::inflate) {

  private val viewModel by viewModels<PhotosViewModel>(
    factoryProducer = PhotosViewModel::factory
  )

  private val photosUiItemAdapter by lazy(LazyThreadSafetyMode.NONE) {
    PhotoUiItemAdapter(
      glide = GlideApp.with(this)
    )
  }

  private fun bindVM() {
    val layoutManager = binding.recyclerPhotoss.layoutManager as LinearLayoutManager

    binding.recyclerPhotoss.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (
          dy > 0
          && layoutManager.findLastVisibleItemPosition() + PhotosFragment.VISIBLE_THRESHOLD >= layoutManager.itemCount
        ) {
          Log.d(PhotosFragment.TAG, "loadNextPage...")
          viewModel.loadNextPage()
        }
      }
    })

    binding.button.setOnClickListener {
      viewModel.retry()
    }

    viewModel.uiStateLiveData.observe(viewLifecycleOwner) { uiState ->
      when (uiState) {
        is PhotosUiState.FirstPageFailure -> {
          photosUiItemAdapter.submitList(emptyList())

          binding.run {
            progressCircular.isVisible = false
            button.isVisible = true
            button.text = "First page failed. Retry"
          }
        }

        PhotosUiState.FirstPageLoading -> {
          photosUiItemAdapter.submitList(emptyList())

          binding.run {
            progressCircular.isVisible = true
            button.isVisible = false
          }
        }
        is PhotosUiState.Page -> {
          photosUiItemAdapter.submitList(uiState.items)

          binding.run {
            progressCircular.isVisible = uiState.isLoading

            button.isVisible = uiState.error !== null
            button.text = "Next page failed. Retry"
          }
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupViews()
    bindVM()
  }

  private fun setupViews() {
    binding.recyclerPhotoss.run {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(context)
      adapter = photosUiItemAdapter
    }
  }

  override fun onDestroyView() {
    binding.recyclerPhotoss.adapter = null
    super.onDestroyView()
  }

  companion object {
    private const val TAG = "PhotosFragment"

    private const val VISIBLE_THRESHOLD = 1

    fun newInstance() = PhotosFragment()
  }
}