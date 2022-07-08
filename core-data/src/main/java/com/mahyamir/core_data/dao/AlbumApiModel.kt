package com.mahyamir.core_data.dao

import com.google.gson.annotations.SerializedName

data class AlbumApiModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover_medium")
    val coverUrl: String,
    @SerializedName("artist")
    val artist: Artist
)

data class AlbumsData(
    @SerializedName("data")
    val albums: List<AlbumApiModel>,
    @SerializedName("total")
    val totalCount: Int,
    @SerializedName("next")
    val nextPage: String?,
    @SerializedName("prev")
    val prevPage: String?
)