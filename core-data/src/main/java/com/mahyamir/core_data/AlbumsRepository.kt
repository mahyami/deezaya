package com.mahyamir.core_data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import androidx.paging.rxjava3.observable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AlbumsRepository @Inject constructor(
    private val albumsPagingSource: AlbumsPagingSource
) {

    fun getAlbums(): Observable<PagingData<AlbumDomainModel>> {
        return Pager(
            config = PagingConfig(pageSize = AlbumsPagingSource.PAGE_SIZE),
            pagingSourceFactory = { albumsPagingSource }
        ).observable
    }
}
