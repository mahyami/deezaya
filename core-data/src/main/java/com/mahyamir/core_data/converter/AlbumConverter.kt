package com.mahyamir.core_data.converter

import com.mahyamir.core_data.dao.AlbumApiModel
import com.mahyamir.core_data.model.AlbumDomainModel
import javax.inject.Inject

class AlbumConverter @Inject constructor() {
    fun convert(apiModel: AlbumApiModel): AlbumDomainModel = AlbumDomainModel(
        id = apiModel.id,
        title = apiModel.title,
        coverUrl = apiModel.coverUrl,
        artistName = apiModel.artist.name
    )
}