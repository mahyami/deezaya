package com.mahyamir.core_data

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.mahyamir.core_data.dao.AlbumsData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AlbumsPagingSource @Inject constructor(
    private val client: DeezayaClient
) : RxPagingSource<Int, AlbumDomainModel>() {

    override fun getRefreshKey(state: PagingState<Int, AlbumDomainModel>): Int? {
        // TODO
        return null
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, AlbumDomainModel>> {
        val position = params.key ?: 0

        return client.getAlbums(position)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: AlbumsData, position: Int): LoadResult<Int, AlbumDomainModel> {
        return LoadResult.Page(
            data = data.albums.map {
                AlbumDomainModel(
                    id = it.id,
                    title = it.title,
                    coverUrl = it.coverUrl,
                    artistName = it.artist.name

                )
            },
            // TODO improve last page is buggy
            prevKey = if (position <= PAGE_SIZE) null else position - PAGE_SIZE,
            nextKey = if (position + PAGE_SIZE >= data.totalCount) null else position + PAGE_SIZE
        )
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}