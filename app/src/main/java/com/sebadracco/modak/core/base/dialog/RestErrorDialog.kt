
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.Window
import com.sebadracco.core.modules.base.dialog.MessageError
import com.sebadracco.modak.R

class RestErrorDialog(context: Context,
                      private var model: ExceptionModel,
                      private val listener: OnClickListener?,
                      private val requestCode: Int) : Dialog(context) {

    private fun initView() {
        initializeCustomDialog()
        fillUi()
        setListeners()
    }

    private fun initializeCustomDialog(){
        if (window != null) {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.alert_dialog_error_one_btn_one_link)
        setCancelable(false)
    }

    private fun setListeners() {

      //  btn_affirmative.setOnClickListener{ primaryBtnClicked()}
      //  btn_alternative.setOnClickListener{ secondaryBtnClicked() }
    }

    private fun fillUi() {
        val model:ExceptionModel?  = model
    //    lbl_title.text = model!!.title
    //    lbl_subtitle.text = model!!.detail!!.text
    //    btn_affirmative.text = model.primaryButton!!.text
       // loadImage(model.icon)
        checkOptionalValues(model!!)
    }

    private fun checkOptionalValues(model: ExceptionModel) {
        checkSecondaryBtn(model)
        checkMessageAttribute(model.detail!!)
    }

    private fun checkMessageAttribute(message: MessageError?) {
        /*if (message.attributeText != null) {
            val init = message.text.indexOf(message.attributeText)
            val text = SpannableString(message.text)
            if (init != -1) {
                text.setSpan(StyleSpan(Typeface.BOLD), init, init + message.attributeText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            lbl_subtitle.setText(text, TextView.BufferType.SPANNABLE)
        }*/
    }

    private fun checkSecondaryBtn(model: ExceptionModel) {
        if (model.secondaryButton != null && model.secondaryButton!!.text!!.isNotEmpty()) {
       //     btn_alternative.visibility = View.VISIBLE
            val btnText = SpannableString(model.secondaryButton!!.text)
            btnText.setSpan(UnderlineSpan(), 0, btnText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
       //     btn_alternative.text = btnText
        } else {
       //     btn_alternative.visibility = View.GONE
        }
    }

    private fun loadImage(iconUrl : String) {
    }

    private fun secondaryBtnClicked() {
        dismiss()
       /* if (model.secondaryButton.deeplink != null) {
            listener?.btnSecondaryClick(model.secondaryButton.text, model.secondaryButton.deeplink, requestCode)
        }else{
            listener?.btnSecondaryClick(model.secondaryButton.text, "", requestCode)

        }*/
    }

    private fun primaryBtnClicked() {
        dismiss()
        /*if (model.primaryButton.deeplink != null) {
            val contains = model.primaryButton.deeplink.contains(TunkiDeepLinkUtil.FIELD_DATA)
            val split = model.primaryButton.deeplink.split(TunkiDeepLinkUtil.FIELD_DATA)
            if(contains && split.size == 2 && !split[1].isNullOrEmpty()){
                val body = TunkiDeepLinkUtil.convertModel(model.primaryButton.deeplink)!!.body.toString()
                listener?.btnPrimaryClick(model.primaryButton.text, TunkiDeepLinkUtil.getUrl(body), requestCode)
            } else {
                listener?.btnPrimaryClick(model.primaryButton.text, model.primaryButton.deeplink, requestCode)
            }
        }else{
            listener?.btnPrimaryClick(model.primaryButton.text, "",requestCode)
        }*/
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context, model: ExceptionModel, listener: OnClickListener?, requestCode: Int): RestErrorDialog {
            val dialog = RestErrorDialog(context, model, listener, requestCode)
            dialog.initView()
            return dialog
        }
    }

    interface OnClickListener {
        fun btnPrimaryClick(textButton: String, deeplink: String, requestCode: Int)
        fun btnSecondaryClick(textButton: String, deeplink: String, requestCode: Int)
    }
}