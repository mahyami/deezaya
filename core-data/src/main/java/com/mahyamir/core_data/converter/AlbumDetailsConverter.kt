package com.mahyamir.core_data.converter

import com.mahyamir.core_data.dao.AlbumDetailsApiModel
import com.mahyamir.core_data.model.AlbumDetailsDomainModel
import com.mahyamir.core_data.model.Track
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
            tracks = apiModel.tracksData.data.map {
                Track(
                    name = it.title,
                    link = it.link,
                    coverUrl = createGenerateCoverUrl(it.imageId)
                )
            },
        )
    }

    private fun createGenerateCoverUrl(id: String?): String? {
        return id?.let {
            "https://e-cdn-images.dzcdn.net/images/cover/$it/120x120-000000-80-0-0.jpg"
        }
    }
}