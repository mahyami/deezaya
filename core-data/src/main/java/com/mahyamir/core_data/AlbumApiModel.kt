package com.mahyamir.core_data

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
    val totalCount: Int
    //TODO next prev
)

data class Artist(
    @SerializedName("name")
    val name: String
)