package com.mahyamir.deezaya.list

import androidx.paging.PagingData

data class AlbumListUiState(
    val albums: PagingData<Album>
)