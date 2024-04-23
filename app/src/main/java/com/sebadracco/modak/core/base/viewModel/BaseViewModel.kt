
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.context.KoinContextHandler

abstract class BaseViewModel(
    open val bindingDelegate: BaseBindingDelegate,
    private val presentationDelegate: BasePresenterDelegate
) : ViewModel() {
    
    fun callLogout() {
        viewModelScope.launch {
            presentationDelegate.showProgressView()
            presentationDelegate.showSuccessLogout()
        }
    }

}