package com.mahyamir.core_data

import com.mahyamir.core_data.dao.AlbumDetailsApiModel
import javax.inject.Inject

class AlbumDetailsConverter @Inject constructor() {

    fun convert(apiModel: AlbumDetailsApiModel): AlbumDetailsDomainModel {
        return AlbumDetailsDomainModel(
            id = apiModel.id,
            title = apiModel.title,
            coverUrl = apiModel.coverUrl,
            releaseDate = apiModel.releaseDate,
            isExplicit = apiModel.isExplicit,
            share = apiModel.share,
            artistName = apiModel.artist.name,
            genres = apiModel.genreData.data.map { it.name },
            tracks = apiModel.tracksData.data.map { Track(name = it.title, link = it.link) },
        )
    }
}