package com.mahyamir.core_data

data class AlbumDomainModel(
    val id: String,
    val title: String,
    val coverUrl: String?,
    val artistName: String
)