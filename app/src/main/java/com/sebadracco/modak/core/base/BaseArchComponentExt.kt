package com.sebadracco.modak.core.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.*
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.sebadracco.modak.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.Serializable
import java.text.DateFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun TextView.strikeThrough(shouldStrike: Boolean = true) {
    paintFlags = if (shouldStrike) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
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
fun View.convertDpToPx(dp: Int) = (Resources.getSystem().displayMetrics.density * dp).toInt()

fun View.margins(start: Int = 0, top: Int = 0, end: Int = 0, bottom: Int = 0) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(
        convertDpToPx(start),
        convertDpToPx(top),
        convertDpToPx(end),
        convertDpToPx(bottom)
    )
}

fun View.constraintsParent() {
    val layParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        ConstraintLayout.LayoutParams.MATCH_PARENT
    )
    layParams.startToStart = ConstraintSet.PARENT_ID
    layParams.endToEnd = ConstraintSet.PARENT_ID
    layParams.topToTop = ConstraintSet.PARENT_ID
    layParams.bottomToBottom = ConstraintSet.PARENT_ID

    layoutParams = layParams
}

fun View.constraintStartAndTopParent(marginStart:Int = 0, topMargin:Int = 0) {
    val layParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.WRAP_CONTENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    )
    layParams.startToStart = ConstraintSet.PARENT_ID
    layParams.topToTop = ConstraintSet.PARENT_ID
    layParams.marginStart = marginStart
    layParams.topMargin = topMargin

    layoutParams = layParams
}

fun View.constraintEndAndTopParent(marginEnd:Int = 0, topMargin:Int = 0) {
    val layParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.WRAP_CONTENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    )
    layParams.endToEnd = ConstraintSet.PARENT_ID
    layParams.topToTop = ConstraintSet.PARENT_ID
    layParams.marginEnd = marginEnd
    layParams.topMargin = topMargin

    layoutParams = layParams
}

fun View.makeBitmap(): Bitmap = run {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    canvas.drawColor(ContextCompat.getColor(context, R.color.darkGrey))
    draw(canvas)
    bmp
}

fun String.clearAmount(): String {
    return this.replace("[S/ ,.]".toRegex(), "")
}

fun String.clearPercentage(): String {
    return this.replace("[% ,.]".toRegex(), "")
}

fun String.toStringOrNull(): String? {
    return if (this.isEmpty()) null else this
}


private fun String.toNewPeruvianCurrencyFormat(): String {
    return replace("S/.", "S/")
}



fun View.focusAndShowKeyboard() {
    /**
     * This is to be called when the window already has focus.
     */
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                // We still post the call, just in case we are being notified of the windows focus
                // but InputMethodManager didn't get properly setup yet.
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
    requestFocus()
    if (hasWindowFocus()) {
        // No need to wait for the window to get focus.
        showTheKeyboardNow()
    } else {
        // We need to wait until the window gets focus.
        viewTreeObserver.addOnWindowFocusChangeListener(
                object : ViewTreeObserver.OnWindowFocusChangeListener {
                    override fun onWindowFocusChanged(hasFocus: Boolean) {
                        // This notification will arrive just before the InputMethodManager gets set up.
                        if (hasFocus) {
                            this@focusAndShowKeyboard.showTheKeyboardNow()
                            // It’s very important to remove this listener once we are done.
                            viewTreeObserver.removeOnWindowFocusChangeListener(this)
                        }
                    }
                })
    }
}



fun <V> Map<String, V>.toBundle(bundle: Bundle = Bundle()): Bundle = bundle.apply {
    forEach {
        val k = it.key
        val v = it.value
        when (v) {
            is IBinder -> putBinder(k, v)
            is Bundle -> putBundle(k, v)
            is Byte -> putByte(k, v)
            is ByteArray -> putByteArray(k, v)
            is Char -> putChar(k, v)
            is CharArray -> putCharArray(k, v)
            is CharSequence -> putCharSequence(k, v)
            is Float -> putFloat(k, v)
            is FloatArray -> putFloatArray(k, v)
            is Parcelable -> putParcelable(k, v)
            is Serializable -> putSerializable(k, v)
            is Short -> putShort(k, v)
            is ShortArray -> putShortArray(k, v)
            else -> throw IllegalArgumentException("$v is of a type that is not currently supported")
        }
    }
}

fun Dialog.setTransparentBackground() {
    setOnShowListener {
        val bottomSheet = findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.setBackgroundResource(android.R.color.transparent)
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ResponseBody.stringSuspending() =
        withContext(Dispatchers.IO) { string() }

fun Context.goToActivity(activity: Activity, classs: Class<*>?) {
    val intent = Intent(activity, classs)
    startActivity(intent)
    activity.finish()
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun <T : Parcelable> Activity.getViewInputForActivity(): T? {
    if (this.intent != null &&
            this.intent.getParcelableExtra<T>("inputViewData") != null) {
        return (this.intent.getParcelableExtra("inputViewData") as? T)
    }
    return null
}

fun Long.toDate(): String {
    val locale = Locale("es", "pe")
    val sym = DateFormatSymbols.getInstance(locale)
    sym.shortMonths = arrayOf("Ene -", "Feb -", "Mar -", "Abr -", "May -", "Jun -", "Jul -", "Ago -", "Sep -", "Oct -", "Nov -", "Dic -")
    val a = SimpleDateFormat("dd MMM HH:mm", sym)
    return a.format(Date(this))
}

fun String.toEpocDate(): Long {
    var millis: Long = 0
    val sdf = SimpleDateFormat("dd/MM/yy")
    try {
        val dateToLong: Date = sdf.parse(this)
        millis = dateToLong.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return millis
}

fun Long.dateDiff(): Int {
    val locale = Locale("es", "pe")
    val todayDate = Calendar.getInstance(locale).timeInMillis
    val diff = this - todayDate
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    if (days.toInt() == 0) {
        val date = Calendar.getInstance(locale)
        val today = Calendar.getInstance(locale)
        date.timeInMillis = this
        if (date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
            && date.get(Calendar.YEAR) == today.get(Calendar.YEAR)
        ) {
            return 0
        }
    }
    if (days.toInt() < 0) {
        return -1
    }
    return days.toInt() + 1
}

fun Long.calculateIsNewPromotion(): Boolean {
    val currentTime = System.currentTimeMillis()
    val diffTime: Long = currentTime - this
    val numOfHours = TimeUnit.MILLISECONDS.toHours(diffTime)
    return numOfHours in 0..24
}

suspend fun <T> Task<T>.await(): T {
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException(
                        "Task $this was cancelled normally.")
            } else {
                result
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else cont.resume(result)
            } else {
                cont.resumeWithException(e)
            }
        }
}
}

fun String.isDeepLinkToMainScreen(): Boolean {
    return this.contains("wallet")
            || this.contains("contacts")
            || this.contains("movement")
}