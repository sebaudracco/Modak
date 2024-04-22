package com.sebadracco.modak.detail.viewModel

import BasePresenterDelegate
import DetailEntityResponse
import com.sebadracco.modak.detail.datasource.entity.DetailModel

class DetailPresenterDelegate (private val bindingDelegate: DetailBindingDelegate) :
    BasePresenterDelegate(bindingDelegate) {

    fun showDetailArtWork(value: DetailModel?) {
        bindingDelegate.hideProgressPostValue()
        bindingDelegate.showDetailArtWork((value))
    }

    fun showUnknownErrorPostValue(){
        bindingDelegate.hideProgressPostValue()
        bindingDelegate.showUnknownErrorPostValue()
    }

}