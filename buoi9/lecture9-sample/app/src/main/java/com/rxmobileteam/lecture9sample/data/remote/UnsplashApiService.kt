package com.rxmobileteam.lecture9sample.data.remote

import com.rxmobileteam.lecture9sample.data.remote.response.CollectionListResponseItem
import com.rxmobileteam.lecture9sample.data.remote.response.PhotoListResponseItem
import com.rxmobileteam.lecture9sample.data.remote.response.SearchPhotosResult
import com.rxmobileteam.lecture9sample.data.remote.response.SearchUserResult
import com.rxmobileteam.lecture9sample.features.feeds.photos.PhotosUiItem
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {
    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<CollectionListResponseItem>

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<PhotoListResponseItem>


    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): SearchPhotosResult

    @GET("search/photos")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): SearchUserResult

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<UnsplashApiService>()
    }
}