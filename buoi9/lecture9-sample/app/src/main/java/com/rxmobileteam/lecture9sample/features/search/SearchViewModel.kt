package com.rxmobileteam.lecture9sample.features.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rxmobileteam.lecture9sample.AppModule
import com.rxmobileteam.lecture9sample.data.remote.UnsplashApiService
import com.rxmobileteam.lecture9sample.features.feeds.collections.CollectionUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val unsplashApiService: UnsplashApiService
) : ViewModel() {
  private val _querySubject = BehaviorSubject.createDefault("")
  private val _resultSearchPhotos = MutableLiveData<List<CollectionUiItem>>(emptyList())

  val resultSearchPhotos get() = _resultSearchPhotos

  private val disposable = _querySubject
    .debounce(600, TimeUnit.MILLISECONDS)
    .filter { it.isNotBlank() }
    .distinctUntilChanged()
    .switchMap { query ->
      unsplashApiService
        .searchPhotosRx(
          query = query,
          page = 1,
          perPage = 10
        )
        .toObservable()
        .map { Result.success(it) }
        .onErrorReturn { Result.failure(it) }
    }
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeBy(
      onNext = { result ->
        result.fold(
          onSuccess = { photosResult ->
            _resultSearchPhotos.value = photosResult.results.map {
              CollectionUiItem(
                id = it.id,
                title = it.description ?: "",
                description = it.description ?: "",
                coverUrl = it.urls.full
              )
            }
          },
          onFailure = { error ->
            Log.e("SearchViewModel", "Error", error)
          }
        )
      },
      onError = { error ->
        Log.e("SearchViewModel", "Error", error)
      }
    )

  override fun onCleared() {
    disposable.dispose()
    super.onCleared()
  }

//  private val _queryLiveData = MutableLiveData<String>("")
//
//  val resultSearchPhotos = _queryLiveData
//    .debounce(
//      duration = 500,
//      coroutineScope = viewModelScope,
//    ).switchMap { query ->
//      liveData {
//        try {
//          val resultSearch = unsplashApiService.searchPhotos(
//            query = query,
//            page = 1,
//            perPage = 10
//          ).results
//            .map {
//              CollectionUiItem(
//                id = it.id,
//                title = it.description ?: "",
//                description = it.description ?: "",
//                coverUrl = it.urls.full
//              )
//            }
//          emit(resultSearch)
//        } catch (e: CancellationException) {
//          throw e
//        } catch (e: Exception) {
//          e.printStackTrace()
//        }
//      }
//    }

  fun queryTextChange(query: String) {
//    _queryLiveData.value = query
    _querySubject.onNext(query)
  }
}
