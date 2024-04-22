
import BaseViewModel
import ErrorDialog
import ExceptionModel
import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sebadracco.core.base.view.BaseViewActionsInterface
import com.sebadracco.modak.R
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<T : ViewBinding> : Fragment(), BaseViewActionsInterface {

    lateinit var bindingView: T

    private lateinit var TAG_SCREEN: String

    //Generics views
    private lateinit var progressView: View

    //Error dialog
    private var beDefaultErrorDialog: ErrorDialog? = null


    //No internet connection
    lateinit var noInternetConnectionView: View
    lateinit var retryNoInternetConnectionView: View
    lateinit var btnReload: Button
    lateinit var txtRetryInternetConnection: TextView

    //Error generic
    private var dialogErrorGeneric: Dialog? = null
    private lateinit var errorGenericView: View
    private lateinit var txtRetryErrorGeneric: TextView

    private var mLastClickTime: Long = 0

    //Configurator
    private var viewConfigurator = BaseViewConfigurator()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
        )
        bindingView = method.invoke(null, layoutInflater, container, false) as T
        TAG_SCREEN = "[" + javaClass.simpleName + "]"
        Log.i("SCREEN", TAG_SCREEN)

        return bindingView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGenericsViews()
        viewOnReady()
        bindObserversToLiveData()
    }

    override fun onResume() {
        super.onResume()
        if (viewConfigurator.tagAnalyticsScreen) {
            //do something
        }
    }

    abstract fun getViewModel(): BaseViewModel

    override fun bindObserversToLiveData() {
        observe(getViewModel().bindingDelegate.showProgressView, this::showProgressView)
        observe(getViewModel().bindingDelegate.hideProgressView, this::hideProgressView)
        observe(getViewModel().bindingDelegate.showGenericError, this::showGenericError)
        observe(getViewModel().bindingDelegate.hideGenericError, this::hideGenericError)
        observe(getViewModel().bindingDelegate.showTimeOutError, this::showTimeOutError)
        observe(getViewModel().bindingDelegate.hideTimeOutError, this::hideTimeOutError)
        observe(getViewModel().bindingDelegate.showDefaultBeError, this::showDefaultBeError)
        observe(getViewModel().bindingDelegate.hideDefaultBeError, this::hideDefaultBeError)
        observe(getViewModel().bindingDelegate.showErrorInternetConnection, this::showErrorInternetConnection)
        observe(getViewModel().bindingDelegate.hideErrorInternetConnection, this::hideErrorInternetConnection)
        observe(getViewModel().bindingDelegate.showLogout, this::showLogout)
        observe(getViewModel().bindingDelegate.showSuccessLogout, this::showSuccessLogout)
    }

    private fun initGenericsViews() {
        (view as? ViewGroup)?.let { viewGroup ->
            initToolbar(viewGroup)
            initProgressView(viewGroup)
            initNoInternetConnectionView(viewGroup)
            initGenericError(viewGroup)
        }
    }

    private fun initToolbar(viewGroupRoot: ViewGroup) {
        viewGroupRoot.forEach {
            if (it is Toolbar) {
                it.elevation = 1f
            }
        }
    }

    private fun initProgressView(viewGroupRoot: ViewGroup) {
        progressView = View.inflate(context, R.layout.custom_loading, viewGroupRoot).findViewById(R.id.v_progress)
        progressView.toGone()
    }

    private fun initNoInternetConnectionView(viewGroupRoot: ViewGroup) {
        //Add NoInternetConnectionView
        retryNoInternetConnectionView = View.inflate(context, R.layout.components_errors_retry_connection, viewGroupRoot).findViewById(R.id.v_no_connection)
        noInternetConnectionView = View.inflate(context, R.layout.components_errors_dialog_connection, viewGroupRoot).findViewById(R.id.v_dialog_connection_root)
        noInternetConnectionView.elevation = 2f
        retryNoInternetConnectionView.toGone()
        noInternetConnectionView.toGone()


        btnReload = noInternetConnectionView.findViewById(R.id.btn_reload)
        btnReload.setOnClickListener { ( noInternetConnectionView.toGone())
            if (viewConfigurator.showRetryInternetConnectionView) {
                retryNoInternetConnectionView.toVisible()
            } }
        txtRetryInternetConnection = retryNoInternetConnectionView.findViewById(R.id.tv_action_internet)
        txtRetryInternetConnection.isClickable = true
        txtRetryInternetConnection.setOnClickListener {
            retryNoInternetConnectionView.toGone()
            this@BaseFragment.retryCallHttp()
        }
    }

    private fun initGenericError(viewGroupRoot: ViewGroup) {
        errorGenericView = View.inflate(context, R.layout.components_error_service, viewGroupRoot).findViewById(R.id.v_no_service)
        errorGenericView.toGone()

        txtRetryErrorGeneric = errorGenericView.findViewById(R.id.tv_action_service_error)
        txtRetryErrorGeneric.isClickable = true
        txtRetryErrorGeneric.setOnClickListener {
            errorGenericView.toGone()
            this@BaseFragment.retryCallHttp()
        }
    }


    protected fun isActionEnable(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return false
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return true
    }

    //region Progress View
    override fun showProgressView(event: Event<Unit>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                progressView.toVisible()
            }
        }
    }

    override fun hideProgressView(event: Event<Unit>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                progressView.toGone()
            }
        }
    }

    //endregion
    //region Generic Error
    override fun showGenericError(event: Event<ExceptionModel>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                if (dialogErrorGeneric == null) {
                  // do something about it
                }
                dialogErrorGeneric?.show()
            }
        }
    }

    fun View.toVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.toGone() {
        this.visibility = View.GONE
    }

    fun View.toInvisible() {
        this.visibility = View.INVISIBLE
    }

    fun String.clearAmount(): String {
        return this.replace("[S/ ,.]".toRegex(), "")
    }


    open fun createGenericErrorDialog(listener: ErrorDialog.OnClickListener ): Dialog {
        return ErrorDialog.newInstance(
                requireActivity(),
                R.layout.alert_dialog_error_one,
                R.drawable.ic_error_image,
                "Algo salió mal!",
                "Ups!",
                "ok",
                listener,
                 400)
    }



    override fun hideGenericError(event: Event<Unit>) {
        errorGenericView.toGone()
    }

    //endregion
    //region TimeOut Error
    override fun showTimeOutError(event: Event<Unit>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                if (dialogErrorGeneric == null) {
                  //  do something!
                }
                dialogErrorGeneric?.show()
            }
        }
    }

    open fun createTimeOutErrorDialog(listener: ErrorDialog.OnClickListener ): Dialog {
        return ErrorDialog.newInstance(
            requireActivity(),
            R.layout.alert_dialog_error_one,
            R.drawable.ic_error_image,
            "El servicio no responde",
            "timeout",
            "ok",
            listener,
            400)
    }

    override fun hideTimeOutError(event: Event<Unit>) {
        errorGenericView.toGone()
    }

    //endregion
    //region Default Be Error
    override fun showDefaultBeError(event: Event<ExceptionModel>) {
        event.getContentIfNotHandled().let {
            it?.apply {
          }
        }
    }

    override fun hideDefaultBeError(event: Event<Unit>) {
        beDefaultErrorDialog?.dismiss()
    }
    //endregion

    override fun showErrorInternetConnection(event: Event<Unit>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                noInternetConnectionView.toVisible()
                if (viewConfigurator.showRetryInternetConnectionView) {
                    retryNoInternetConnectionView.toVisible()
                }
            }
        }
    }

    override fun hideErrorInternetConnection(event: Event<Unit>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                retryNoInternetConnectionView.toGone()
                noInternetConnectionView.toGone()
            }
        }
    }

    override fun showLogout(event: Event<Unit>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                getViewModel().callLogout()
            }
        }
    }


    override fun setConfigurationObject(baseViewConfigurator: BaseViewConfigurator) {
        viewConfigurator = baseViewConfigurator
    }
    //endregion
}

data class BaseViewConfigurator(
        val showRetryInternetConnectionView: Boolean = true,
        val tagAnalyticsScreen : Boolean = true
)
