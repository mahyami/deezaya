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
    @SerializedName("genres")
    val genreData: GenreData,
    @SerializedName("tracks")
    val tracksData: TracksData
)

data class GenreData(
    @SerializedName("data")
    val data: List<Genre>
)

data class Genre(
    @SerializedName("name")
    val name: String
)

data class TracksData(
    @SerializedName("data")
    val data: List<Track>
)

data class Track(
    @SerializedName("title")
    val title: String,
    @SerializedName("link")
    val link: String
)