package com.sebadracco.modak.main.viewmodel

import BaseViewModel
import DetailEntityResponse
import MainUseCase
import androidx.lifecycle.viewModelScope
import com.sebadracco.modak.core.base.util.SharePreferencesManager
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainViewUseCase: MainUseCase,
    private val sharedPreferences: SharePreferencesManager,
    override val bindingDelegate: MainBindingDelegate,
    private val presenterDelegate: MainPresenterDelegate = MainPresenterDelegate(
        bindingDelegate
    )
) : BaseViewModel(bindingDelegate, presenterDelegate) {

    fun getArticles() {
        viewModelScope.launch {
            bindingDelegate.showProgressPostValue()
            when (val response = mainViewUseCase.invoke(Unit)) {
                is BaseResultWrapper.ApiError -> presenterDelegate.showUnknownErrorPostValue()

                is BaseResultWrapper.ApiSuccess -> presenterDelegate.showlistOfArticles(response.value)
            }
        }
    }


    fun showProgressPostValue() {
        bindingDelegate.showProgressPostValue()
    }

    fun hideProgressPostValue() {
        bindingDelegate.hideProgressPostValue()
    }


    fun getEmptyList(): List<DetailEntityResponse> {
        return emptyList()
    }




}