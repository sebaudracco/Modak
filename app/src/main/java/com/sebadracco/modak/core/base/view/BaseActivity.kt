

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.sebadracco.core.base.view.BaseViewActionsInterface
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<T : ViewBinding> :
        AppCompatActivity(), BaseViewActionsInterface {

    lateinit var binding: T

    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = method.invoke(null, layoutInflater) as T
        setContentView(binding.root)
        viewOnReady()
        bindObserversToLiveData()
    }


    open fun isActionEnable(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return false
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return true
    }

}
