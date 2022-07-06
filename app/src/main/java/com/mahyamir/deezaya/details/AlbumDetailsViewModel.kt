package com.mahyamir.deezaya.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahyamir.core_data.AlbumDetailsDomainModel
import com.mahyamir.core_data.AlbumsRepository
import com.mahyamir.deezaya.scheduler.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private var _albumUiState = MutableLiveData<AlbumDetailsUiState>()
    val albumUiState: LiveData<AlbumDetailsUiState> = _albumUiState
    private var disposable: Disposable? = null

    fun init(albumId: String) {
        disposable = albumsRepository.getAlbum(albumId)
            .subscribeOn(schedulerProvider.ioScheduler)
            .observeOn(schedulerProvider.mainScheduler)
            .subscribeBy(
                onSuccess = ::onSuccess,
                onError = ::onError
            )
    }

    private fun onSuccess(details: AlbumDetailsDomainModel) {
        _albumUiState.postValue(AlbumDetailsUiState(details))
    }

    private fun onError(throwable: Throwable) {

    }

    fun onDestroy() {
        disposable?.dispose()
    }

}