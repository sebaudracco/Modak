package com.sebadracco.modak.detail.viewModel

import BaseBindingDelegate
import DetailEntityResponse
import Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sebadracco.modak.detail.datasource.entity.DetailModel

class DetailBindingDelegate : BaseBindingDelegate() {

    //region Show progress view
    private val _showProgress = MutableLiveData<Event<Unit>>()
    val showProgress: LiveData<Event<Unit>> get() = _showProgress
    fun showProgressPostValue() {
        _showProgress.value = Event(Unit)
    }
    //endregion
    //region Hide progress view
    private val _hideProgress = MutableLiveData<Event<Unit>>()
    val hideProgress: LiveData<Event<Unit>> get() = _hideProgress
    fun hideProgressPostValue() {
        _hideProgress.value = Event(Unit)
    }
    //endregion

    //region Show Unknown error
    private val _showUnknownError = MutableLiveData<Event<Unit>>()
    val showUnknownError: LiveData<Event<Unit>> get() = _showUnknownError
    fun showUnknownErrorPostValue() {
        _showUnknownError.value = Event(Unit)
    }
    //endregion


    private val _showDetailArtWork = MutableLiveData<Event<DetailModel?>>()
    val showDetailArtWork : LiveData<Event<DetailModel?>> get() = _showDetailArtWork
    fun showDetailArtWork(articles: DetailModel? ) {
        _showDetailArtWork.value = Event(articles)
    }


    //endregion

}