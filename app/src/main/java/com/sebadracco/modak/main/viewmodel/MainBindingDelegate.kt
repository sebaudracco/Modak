package com.sebadracco.modak.main.viewmodel

import BaseBindingDelegate
import DetailEntityResponse
import Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainBindingDelegate : BaseBindingDelegate() {

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


    private val _showlistOfArtWork = MutableLiveData<Event<List<DetailEntityResponse>?>>()
    val showlistOfArtWork : LiveData<Event<List<DetailEntityResponse>?>> get() = _showlistOfArtWork
    fun showlistOfArtWork(articles: List<DetailEntityResponse>? ) {
        _showlistOfArtWork.value = Event(articles)
    }


    //endregion

}