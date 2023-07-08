package com.rxmobileteam.lecture9sample.features.feeds.collections

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxmobileteam.lecture9sample.data.remote.UnsplashApiService
import com.rxmobileteam.lecture9sample.data.remote.response.CollectionListResponseItem
import com.rxmobileteam.lecture9sample.extensions.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
  private val unsplashApiService: UnsplashApiService
) : ViewModel() {
  private val _uiStateLiveData =
    MutableLiveData<CollectionsUiState>(CollectionsUiState.FirstPageLoading)

  val uiStateLiveData: LiveData<CollectionsUiState> get() = _uiStateLiveData

  init {
    loadFirstPage()
  }

  private fun loadFirstPage() {
    viewModelScope.launch {
      _uiStateLiveData.update { CollectionsUiState.FirstPageLoading }

      try {
        val responses = unsplashApiService.getCollections(
          page = 1,
          perPage = PER_PAGE,
        )
        Log.d(TAG, "loadFirstPage: success")

        _uiStateLiveData.update {
          CollectionsUiState.Page(
            pageNumber = 1,
            items = responses.map { it.toCollectionUiItem() },
            isLoading = false,
            error = null
          )
        }

      } catch (e: CancellationException) {
        throw e
      } catch (e: Throwable) {
        Log.e(TAG, "loadFirstPage: failed", e)

        _uiStateLiveData.update {
          CollectionsUiState.FirstPageFailure(error = e)
        }
      }
    }
  }

  fun loadNextPage() {
    val currentState = _uiStateLiveData.value
    if (currentState !is CollectionsUiState.Page) {
      // ignore
      return
    }

    if (!currentState.isLoading
      && currentState.error == null
      && currentState.pageNumber >= 1
      && currentState.items.isNotEmpty()
    ) {

      _uiStateLiveData.update {
        currentState.copy(
          isLoading = true
        )
      }
      val newPageNumber = currentState.pageNumber + 1

      viewModelScope.launch {
        try {
          val newPageItems = unsplashApiService.getCollections(
            page = newPageNumber,
            perPage = PER_PAGE
          )
          Log.d(TAG, "loadNextPage: success")

          _uiStateLiveData.update {
            currentState.copy(
              pageNumber = newPageNumber,
              items = (currentState.items + newPageItems.map { it.toCollectionUiItem() })
                .distinctBy { it.id },
              isLoading = false,
              error = null,
            )
          }
        } catch (e: CancellationException) {
          throw e
        } catch (e: Throwable) {
          Log.e(TAG, "loadNextPage: failed", e)

          _uiStateLiveData.update {
            currentState.copy(
              isLoading = false,
              error = e,
            )
          }
        }
      }
    }
  }

  fun retry() {
    TODO("Not yet implemented")
  }

  companion object {
    private const val TAG = "CollectionsViewModel"
    private const val PER_PAGE = 30
  }
}

private fun CollectionListResponseItem.toCollectionUiItem(): CollectionUiItem = CollectionUiItem(
  id = id,
  title = title,
  description = description.orEmpty(),
  coverUrl = coverPhoto.urls.full,
)
