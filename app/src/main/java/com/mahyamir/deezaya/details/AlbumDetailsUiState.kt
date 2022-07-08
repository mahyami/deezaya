package com.mahyamir.deezaya.details

import com.mahyamir.core_data.model.AlbumDetailsDomainModel

sealed class AlbumDetailsUiState {
    data class Loaded(val details: AlbumDetailsDomainModel) : AlbumDetailsUiState()
    object Error : AlbumDetailsUiState()
    object Loading : AlbumDetailsUiState()
}