package com.sebadracco.modak.main.viewmodel

import BasePresenterDelegate
import DetailEntityResponse

class MainPresenterDelegate (private val bindingDelegate: MainBindingDelegate) :
    BasePresenterDelegate(bindingDelegate) {

    fun showlistOfArticles(value:List<DetailEntityResponse>?) {
        bindingDelegate.hideProgressPostValue()
        bindingDelegate.showlistOfArtWork((value))
    }

    fun showUnknownErrorPostValue(){
        bindingDelegate.hideProgressPostValue()
        bindingDelegate.showUnknownErrorPostValue()
    }

}