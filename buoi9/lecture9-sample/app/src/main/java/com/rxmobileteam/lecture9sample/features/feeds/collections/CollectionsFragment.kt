package com.rxmobileteam.lecture9sample.features.feeds.collections

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.rxmobileteam.lecture9sample.GlideApp
import com.rxmobileteam.lecture9sample.PrefsManager
import com.rxmobileteam.lecture9sample.SharedBetweenFragmentsInAnActivity
import com.rxmobileteam.lecture9sample.base.BaseFragment
import com.rxmobileteam.lecture9sample.databinding.FragmentCollectionsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

@AndroidEntryPoint
class CollectionsFragment :
  BaseFragment<FragmentCollectionsBinding>(FragmentCollectionsBinding::inflate) {
  @Inject
  lateinit var prefsManager: PrefsManager

  @Inject
  lateinit var sharedBetweenFragmentsInAnActivity: SharedBetweenFragmentsInAnActivity

  private val viewModel by viewModels<CollectionsViewModel>()

  private val collectionUiItemAdapter by lazy(NONE) {
    CollectionUiItemAdapter(
      glide = GlideApp.with(this)
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharedBetweenFragmentsInAnActivity.doSomething(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    Log.d(this::class.java.simpleName, "prefsManager -> $prefsManager")
    Log.d(this::class.java.simpleName, "prefsManager -> ${prefsManager.isDarkMode()}")
    prefsManager.toggle()

    setupViews()
    bindVM()
  }

  private fun bindVM() {
    val layoutManager = binding.recyclerCollections.layoutManager as LinearLayoutManager

    binding.recyclerCollections.addOnScrollListener(object : OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        // Log.d(TAG, "dy=$dy, ${layoutManager.findLastVisibleItemPosition()}, ${layoutManager.itemCount}")

        if (
          dy > 0
          && layoutManager.findLastVisibleItemPosition() + VISIBLE_THRESHOLD >= layoutManager.itemCount
        ) {
          Log.d(TAG, "loadNextPage...")
          viewModel.loadNextPage()
        }
      }
    })

    binding.button.setOnClickListener {
      viewModel.retry()
    }

    viewModel.uiStateLiveData.observe(viewLifecycleOwner) { uiState ->
      when (uiState) {
        is CollectionsUiState.FirstPageFailure -> {
          collectionUiItemAdapter.submitList(emptyList())

          binding.run {
            progressCircular.isVisible = false
            button.isVisible = true
            button.text = "First page failed. Retry"
          }
        }

        CollectionsUiState.FirstPageLoading -> {
          collectionUiItemAdapter.submitList(emptyList())

          binding.run {
            progressCircular.isVisible = true
            button.isVisible = false
          }
        }

        is CollectionsUiState.Page -> {
          collectionUiItemAdapter.submitList(uiState.items)

          binding.run {
            progressCircular.isVisible = uiState.isLoading

            button.isVisible = uiState.error !== null
            button.text = "Next page failed. Retry"
          }
        }
      }
    }
  }

  private fun setupViews() {
    binding.recyclerCollections.run {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(context)
      adapter = collectionUiItemAdapter
    }
  }

  override fun onDestroyView() {
    binding.recyclerCollections.adapter = null
    super.onDestroyView()
  }


  companion object {
    private const val TAG = "CollectionsFragment"

    private const val VISIBLE_THRESHOLD = 1

    fun newInstance() = CollectionsFragment()
  }
}