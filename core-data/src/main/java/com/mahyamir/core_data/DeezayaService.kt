package com.mahyamir.core_data

import com.mahyamir.core_data.dao.AlbumDetailsApiModel
import com.mahyamir.core_data.dao.AlbumsData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezayaService {

    @GET("/2.0/user/2529/albums")
    fun getAlbums(@Query("index") index: Int): Single<AlbumsData>

    @GET("/2.0/album/{id}")
    fun getAlbum(@Path("id") id: String): Single<AlbumDetailsApiModel>
}