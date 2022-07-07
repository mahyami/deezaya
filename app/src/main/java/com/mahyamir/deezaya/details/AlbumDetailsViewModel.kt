package com.mahyamir.deezaya.details

import android.util.Log
import androidx.lifecycle.*
import com.mahyamir.core_data.AlbumDetailsDomainModel
import com.mahyamir.core_data.AlbumsRepository
import com.mahyamir.deezaya.scheduler.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    albumsRepository: AlbumsRepository,
    schedulerProvider: SchedulerProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _albumUiState = MutableLiveData<AlbumDetailsUiState>()
    val albumUiState: LiveData<AlbumDetailsUiState> = _albumUiState
    private var disposable: Disposable? = null

    init {
        val albumId = requireNotNull(savedStateHandle.get<String>("id"))
        _albumUiState.postValue(AlbumDetailsUiState.Loading)
        albumsRepository.getAlbum(albumId)
            .subscribeOn(schedulerProvider.ioScheduler)
            .observeOn(schedulerProvider.mainScheduler)
            .subscribeBy(
                onSuccess = ::onSuccess,
                onError = ::onError
            )
    }

    private fun onSuccess(details: AlbumDetailsDomainModel) {
        _albumUiState.postValue(AlbumDetailsUiState.Loaded(details))
    }

    private fun onError(throwable: Throwable) {
        Log.e("Error getting album details! ", "${throwable.message}")
        _albumUiState.postValue(AlbumDetailsUiState.Error)
    }

    fun onDestroy() {
        disposable?.dispose()
    }
}