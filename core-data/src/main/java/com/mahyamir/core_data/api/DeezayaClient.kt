package com.mahyamir.core_data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeezayaClient @Inject constructor() {

    private var deezayaService: DeezayaService

    companion object {
        private const val BASE_URL = "https://api.deezer.com/"
    }

    init {
        val logger =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        val gson = GsonBuilder().create()

        deezayaService = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(DeezayaService::class.java)
    }

    fun getAlbums(index: Int) = deezayaService.getAlbums(index)

    fun getAlbum(id: String) = deezayaService.getAlbum(id)
}

