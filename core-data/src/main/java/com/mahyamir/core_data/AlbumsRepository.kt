package com.mahyamir.core_data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.mahyamir.core_data.api.DeezayaClient
import com.mahyamir.core_data.converter.AlbumDetailsConverter
import com.mahyamir.core_data.model.AlbumDetailsDomainModel
import com.mahyamir.core_data.model.AlbumDomainModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepository @Inject constructor(
    private val albumsPagingSource: AlbumsPagingSource,
    private val deezayaClient: DeezayaClient,
    private val albumDetailsConverter: AlbumDetailsConverter
) {

    fun getAlbums(): Observable<PagingData<AlbumDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = AlbumsPagingSource.PAGE_SIZE),
            pagingSourceFactory = { albumsPagingSource }
        ).observable
    }

    fun getAlbum(id: String): Single<AlbumDetailsDomainModel> = deezayaClient.getAlbum(id)
        .map {
            albumDetailsConverter.convert(it)
        }
}
