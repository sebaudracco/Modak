import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.Spanned
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull


class ErrorDialog : Dialog {
    var tvTitle: TextView? = null
    var tvDetail: TextView? = null
    var btnDialog: Button? = null
    var ivError: ImageView? = null
    private val requestCode: Int
    private var listener: OnClickListener?

    private constructor(
        @NonNull context: Context,
        layoutId: Int,
        drawableId: Int,
        title: String,
        detail: String,
        txtButton: String,
        listener: OnClickListener,
        requestCode: Int
    ) : super(context) {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layoutId)
        //  ButterKnife.bind(this)
        setCancelable(false)
        this.requestCode = requestCode
        this.listener = listener
        ivError!!.setImageResource(drawableId)
        tvTitle!!.text = title
        tvDetail!!.text = detail
        btnDialog!!.text = txtButton
    }

    private constructor(
        @NonNull context: Context,
        layoutId: Int,
        drawableId: Int,
        title: String,
        detail: Spanned,
        txtButton: String,
        listener: OnClickListener,
        requestCode: Int
    ) : super(context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layoutId)
        //ButterKnife.bind(this)
        setCancelable(false)
        this.requestCode = requestCode
        this.listener = listener
        ivError!!.setImageResource(drawableId)
        tvTitle!!.text = title
        tvDetail!!.text = detail
        btnDialog!!.text = txtButton
    }

    fun onBtnRight() {
        if (listener != null) {
            listener!!.btnClick(requestCode)
        }
        dismiss()
    }

    interface OnClickListener {
        fun btnClick(requestCode: Int)
    }

    companion object {
        fun newInstance(
            activity: Activity,
            layoutId: Int,
            drawableId: Int,
            title: String,
            detail: String,
            txtButton: String,
            listener: OnClickListener,
            requestCode: Int
        ): Dialog {
            return ErrorDialog(
                activity,
                layoutId,
                drawableId,
                title,
                detail,
                txtButton,
                listener,
                requestCode
            )
        }

        fun newInstance(
            activity: Activity,
            layoutId: Int,
            drawableId: Int,
            title: String,
            detail: Spanned,
            txtButton: String,
            listener: OnClickListener,
            requestCode: Int
        ): Dialog {
            return ErrorDialog(
                activity,
                layoutId,
                drawableId,
                title,
                detail,
                txtButton,
                listener,
                requestCode
            )
        }
    }
}