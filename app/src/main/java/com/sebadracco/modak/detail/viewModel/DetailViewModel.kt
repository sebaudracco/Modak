package com.sebadracco.modak.detail.viewModel

import BaseViewModel
import androidx.lifecycle.viewModelScope
import com.sebadracco.modak.core.base.util.SharePreferencesManager
import com.sebadracco.modak.detail.usecase.DetailUseCase
import com.sebadracco.modak.main.viewmodel.MainBindingDelegate
import com.sebadracco.modak.main.viewmodel.MainPresenterDelegate
import kotlinx.coroutines.launch

class DetailViewModel (
    private val detailViewUseCase: DetailUseCase,
    override val bindingDelegate: DetailBindingDelegate,
    private val presenterDelegate: DetailPresenterDelegate = DetailPresenterDelegate(
        bindingDelegate
    )
) : BaseViewModel(bindingDelegate, presenterDelegate) {

    fun getWork(id: Int) {
        viewModelScope.launch {
            bindingDelegate.showProgressPostValue()
            when (val response = detailViewUseCase.invoke(id)) {
                is BaseResultWrapper.ApiError -> presenterDelegate.showUnknownErrorPostValue()

                is BaseResultWrapper.ApiSuccess -> presenterDelegate.showDetailArtWork(response.value)
            }
        }
    }


    fun showProgressPostValue() {
        bindingDelegate.showProgressPostValue()
    }

    fun hideProgressPostValue() {
        bindingDelegate.hideProgressPostValue()
    }



}