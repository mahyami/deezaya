package com.mahyamir.deezaya.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.cachedIn
import com.mahyamir.core_data.AlbumDomainModel
import com.mahyamir.core_data.AlbumsRepository
import com.mahyamir.deezaya.scheduler.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AlbumListViewModel @Inject constructor(
    albumsRepository: AlbumsRepository,
    schedulerProvider: SchedulerProvider
) : ViewModel() {

    private var _albumsUiState = MutableLiveData<AlbumListUiState>()
    val albumsUiState: LiveData<AlbumListUiState> = _albumsUiState
    private var albumsDisposable: Disposable? = null

    init {
        _albumsUiState.postValue(AlbumListUiState.Loading)
        albumsDisposable = albumsRepository.getAlbums()
            // important for config change
            .cachedIn(viewModelScope)
            .subscribeOn(schedulerProvider.ioScheduler)
            .observeOn(schedulerProvider.mainScheduler)
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
        _albumsUiState.postValue(AlbumListUiState.Loaded(albums))
    }

    private fun onError(throwable: Throwable) {
        Log.e("Error getting album details! ", "${throwable.message}")
        _albumsUiState.postValue(AlbumListUiState.Error)
    }

    fun onDestroy() = albumsDisposable?.dispose()
}