package com.mahyamir.core_data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezayaService {

    @GET("/2.0/user/2529/albums")
    fun getAlbums(@Query("index") index: Int): Single<AlbumsData>
}