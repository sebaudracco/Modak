package com.sebadracco.core.base.view

import BaseViewConfigurator
import Event
import ExceptionModel


/**
 * Interface for basic actions for View
 */
interface BaseViewActionsInterface{
    /**
     * Initialize views
     */
    fun viewOnReady() {
        //Sometimes there is nothing to bind
        //that's why the default behavior
    }

    /**
     * Set observers on ViewModel
     */
    fun bindObserversToLiveData() {
        //Sometimes there is nothing to bind
        //that's why the default behavior
    }

    fun retryCallHttp(){}

    fun primaryBtnErrorDialog(textButton: String, deeplink: String, requestCode: Int){}

    fun secondaryBtnErrorDialog(textButton: String, deeplink: String, requestCode: Int){}

    fun btnClickGenericErrorDialog(){}

    fun showProgressView(event: Event<Unit>){}
    fun hideProgressView(event: Event<Unit>){}

    /**
     * We show generic error dialog
     * for 422 BackEnd error
     */
    fun showGenericError(event: Event<ExceptionModel>){}
    fun hideGenericError(event: Event<Unit>){}

    /**
     * We show generic error dialog
     * for 422 BackEnd error
     */
    fun showTimeOutError(event: Event<Unit>){}
    fun hideTimeOutError(event: Event<Unit>){}

    /**
     * We show generic error dialog
     * for 422 BackEnd error
     */
    fun showDefaultBeError(event: Event<ExceptionModel>){}
    fun hideDefaultBeError(event: Event<Unit>){}
    //endregion
    //region Internet Connection Error
    /**
     * We show default view
     * to when internet connection is out
     */
    fun showErrorInternetConnection(event: Event<Unit>){}
    fun hideErrorInternetConnection(event: Event<Unit>){}
    /**
     * Tag 422 and 500 error when be showed
     */

    /**
     * Custom configurations to customize some base behaviors.
     */
    fun setConfigurationObject(baseViewConfigurator: BaseViewConfigurator) {}
    //endregion

    fun showLogout(event: Event<Unit>) {}
    fun showSuccessLogout(event: Event<Unit>) {}
}
