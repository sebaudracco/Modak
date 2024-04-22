
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.context.KoinContextHandler

abstract class BaseViewModel(
    open val bindingDelegate: BaseBindingDelegate,
    private val presentationDelegate: BasePresenterDelegate
) : ViewModel() {
    val appPreferencesRepository: AppPreferencesRepository
    by lazy { KoinContextHandler.get().get<AppPreferencesRepository>() }

    fun callLogout() {
        viewModelScope.launch {
            presentationDelegate.showProgressView()
            presentationDelegate.showSuccessLogout()
        }
    }

}