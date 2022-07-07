package com.mahyamir.core_data.dao

import com.google.gson.annotations.SerializedName

data class AlbumDetailsApiModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover_medium")
    val coverUrl: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("explicit_lyrics")
    val isExplicit: Boolean,
    @SerializedName("share")
    val share: String,
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("tracks")
    val tracksData: TracksData,
    @SerializedName("error")
    val error: Error?
)

data class TracksData(
    @SerializedName("data")
    val data: List<Track>
)

data class Track(
    @SerializedName("title")
    val title: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("md5_image")
    val imageId: String?
)

data class Error(
    @SerializedName("message")
    val message: String?
)