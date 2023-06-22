package com.rxmobileteam.lecture9sample.features.feeds.photos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rxmobileteam.lecture9sample.ServiceLocator
import com.rxmobileteam.lecture9sample.data.remote.UnsplashApiService
import com.rxmobileteam.lecture9sample.data.remote.response.PhotoListResponseItem
import com.rxmobileteam.lecture9sample.features.feeds.collections.update
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

class PhotosViewModel (
    private val unsplashApiService: UnsplashApiService
) : ViewModel() {

    private val _uiStateLiveData = MutableLiveData<PhotosUiState>(PhotosUiState.FirstPageLoading)

    val uiStateLiveData: LiveData<PhotosUiState> get() = _uiStateLiveData

    init {
        loadFirstPage()
    }

    companion object {
        private const val TAG = "PhotosViewModel"
        private const val PER_PAGE = 30

        fun factory(): ViewModelProvider.Factory = viewModelFactory {
            addInitializer(PhotosViewModel::class) {
                PhotosViewModel(
                    unsplashApiService = ServiceLocator.unsplashApiService
                )
            }
        }
    }

    private fun loadFirstPage() {
        viewModelScope.launch {
            _uiStateLiveData.update { PhotosUiState.FirstPageLoading }

            try {
                val responses = unsplashApiService.getPhotos(
                    page = 1,
                    perPage = PhotosViewModel.PER_PAGE,
                )
                Log.d(PhotosViewModel.TAG, "loadFirstPage: success")

                _uiStateLiveData.update {
                    PhotosUiState.Page(
                        pageNumber = 1,
                        items = responses.map { it.toPhotoUiItem() },
                        isLoading = false,
                        error = null
                    )
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                Log.e(PhotosViewModel.TAG, "loadFirstPage: failed", e)

                _uiStateLiveData.update {
                    PhotosUiState.FirstPageFailure(error = e)
                }
            }
        }
    }

    fun loadNextPage() {
        val currentState = _uiStateLiveData.value
        if (currentState !is PhotosUiState.Page) {
            // ignore
            return
        }

        if (!currentState.isLoading
            && currentState.error == null
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
                    val newPageItems = unsplashApiService.getPhotos(
                        page = newPageNumber,
                        perPage = PhotosViewModel.PER_PAGE
                    )
                    Log.d(PhotosViewModel.TAG, "loadNextPage: success")

                    _uiStateLiveData.update {
                        currentState.copy(
                            pageNumber = newPageNumber,
                            items = (currentState.items + newPageItems.map { it.toPhotoUiItem() })
                                .distinctBy { it.id },
                            isLoading = false,
                            error = null,
                        )
                    }
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Throwable) {
                    Log.e(PhotosViewModel.TAG, "loadNextPage: failed", e)

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
        val currentState = _uiStateLiveData.value
        if (currentState !is PhotosUiState.Page) {
            loadFirstPage()
            return
        }
        _uiStateLiveData.update {
            currentState.copy(
                isLoading = false,
                error = null,
            )
        }
        loadNextPage()
    }
}

private fun PhotoListResponseItem.toPhotoUiItem(): PhotosUiItem = PhotosUiItem(
    id = id,
    title = altDescription,
    description = description.orEmpty(),
    coverUrl = urls.full,
    authorUrl = user.profileImage.medium,
    nameAuthor = user.name,
    like = likes
)