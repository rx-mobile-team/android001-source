package com.rxmobileteam.lecture9sample.features.feeds.photos

sealed interface PhotosUiState {
    object FirstPageLoading : PhotosUiState

    data class FirstPageFailure(val error: Throwable) : PhotosUiState

    data class Page(
        val pageNumber: Int,
        val items: List<PhotosUiItem>,
        val isLoading: Boolean,
        val error: Throwable?
    ) : PhotosUiState
}

data class PhotosUiItem(
    val id: String,
    val title: String,
    val description: String,
    val coverUrl: String,
    val authorUrl: String,
    val nameAuthor: String,
    val like: Int
)