package com.mahyamir.deezaya.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.cachedIn
import com.mahyamir.core_data.AlbumDomainModel
import com.mahyamir.core_data.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AlbumListViewModel @Inject constructor(
    albumsRepository: AlbumsRepository
) : ViewModel() {

    private var _albumUiState = MutableLiveData<AlbumListUiState>()
    val albumUiState: LiveData<AlbumListUiState> = _albumUiState
    private var albumsDisposable: Disposable? = null

    init {
        albumsDisposable = albumsRepository.getAlbums()
                // important for rotations
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = ::onNextPageAlbums,
                onError = ::onError
            )
    }

    private fun onNextPageAlbums(data: PagingData<AlbumDomainModel>) {
        val albums = data.map { model ->
            Album(
                id = model.id,
                name = model.title,
                artistName = model.artistName,
                coverUrl = model.coverUrl
            )
        }
        _albumUiState.postValue(AlbumListUiState(albums))
    }

    private fun onError(throwable: Throwable) {
        // TODO
    }

    fun onDestroy() = albumsDisposable?.dispose()
}