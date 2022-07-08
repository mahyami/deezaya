package com.mahyamir.core_data.model

data class AlbumDetailsDomainModel(
    val id: String,
    val title: String,
    val coverUrl: String,
    val releaseDate: String,
    val isExplicit: Boolean,
    val share: String,
    val artistName: String,
    val tracks: List<Track>
)

data class Track(
    val name: String,
    val link: String,
    val coverUrl: String?
)