package com.mahyamir.deezaya.list

import androidx.paging.PagingData

sealed class AlbumListUiState {
    data class Loaded(val albums: PagingData<Album>) : AlbumListUiState()
    object Loading : AlbumListUiState()
    object Error : AlbumListUiState()
}