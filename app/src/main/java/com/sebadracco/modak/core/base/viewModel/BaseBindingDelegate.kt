import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


abstract class BaseBindingDelegate{

    //region Generic actions
    //region Show progress view
    private val _showProgressView = MutableLiveData<Event<Unit>>()
    val showProgressView: LiveData<Event<Unit>> get() = _showProgressView
    fun showProgressViewPostValue() {
        _showProgressView.postValue(Event(Unit))
    }
    //endregion

    //region Hide progress view
    private val _hideProgressView = MutableLiveData<Event<Unit>>()
    val hideProgressView: LiveData<Event<Unit>> get() = _hideProgressView
    fun hideProgressViewPostValue() {
        _hideProgressView.postValue(Event(Unit))
    }
    //endregion

    //region Show Error Internet Connection
    private val _showErrorInternetConnection = MutableLiveData<Event<Unit>>()
    val showErrorInternetConnection: LiveData<Event<Unit>> get() = _showErrorInternetConnection
    fun showErrorInternetConnectionPostValue() {
        _showErrorInternetConnection.postValue(Event(Unit))
    }
    //endregion

    //region Hide Error Internet Connection
    private val _hideErrorInternetConnection = MutableLiveData<Event<Unit>>()
    val hideErrorInternetConnection: LiveData<Event<Unit>> get() = _hideErrorInternetConnection
    fun hideErrorInternetConnectionPostValue() {
        _hideErrorInternetConnection.postValue(Event(Unit))
    }
    //endregion

    //region Show Default BE Error
    private val _showDefaultBeError = MutableLiveData<Event<ExceptionModel>>()
    val showDefaultBeError: LiveData<Event<ExceptionModel>> get() = _showDefaultBeError
    fun showDefaultBeErrorPostValue(agoraExceptionModel: ExceptionModel) {
        _showDefaultBeError.postValue(Event(agoraExceptionModel))
    }
    //endregion

    //region Hide Default BE Error
    private val _hideDefaultBeError = MutableLiveData<Event<Unit>>()
    val hideDefaultBeError: LiveData<Event<Unit>> get() = _hideDefaultBeError
    fun hideDefaultBeErrorPostValue() {
        _hideDefaultBeError.postValue(Event(Unit))
    }
    //endregion

    //region Show Generic error
    private val _showGenericError = MutableLiveData<Event<ExceptionModel>>()
    val showGenericError: LiveData<Event<ExceptionModel>> get() = _showGenericError
    fun showGenericErrorPostValue(model: ExceptionModel) {
        _showGenericError.postValue(Event(model))
    }
    //endregion

    //region Hide Generic error
    private val _hideGenericError = MutableLiveData<Event<Unit>>()
    val hideGenericError: LiveData<Event<Unit>> get() = _hideGenericError
    fun hideGenericErrorPostValue() {
        _hideGenericError.postValue(Event(Unit))
    }
    //endregion

    //region Show Timeout error
    private val _showTimeOutError = MutableLiveData<Event<Unit>>()
    val showTimeOutError: LiveData<Event<Unit>> get() = _showTimeOutError
    fun showTimeOutErrorPostValue() {
        _showTimeOutError.postValue(Event(Unit))
    }
    //endregion

    //region Hide Generic error
    private val _hideTimeOutError = MutableLiveData<Event<Unit>>()
    val hideTimeOutError: LiveData<Event<Unit>> get() = _hideTimeOutError
    fun hideTimeOutErrorPostValue() {
        _hideTimeOutError.postValue(Event(Unit))
    }
    //endregion

    //region showLogout
    private val _showLogout = MutableLiveData<Event<Unit>>()
    val showLogout: LiveData<Event<Unit>> get() = _showLogout
    fun showLogoutPostValue() {
        _showLogout.postValue(Event(Unit))
    }
    //endregion

    //region showSuccessLogout
    private val _showSuccessLogout = MutableLiveData<Event<Unit>>()
    val showSuccessLogout: LiveData<Event<Unit>> get() = _showSuccessLogout
    fun showSuccessLogoutPostValue() {
        _showSuccessLogout.value = Event(Unit)
    }
    //endregion
}