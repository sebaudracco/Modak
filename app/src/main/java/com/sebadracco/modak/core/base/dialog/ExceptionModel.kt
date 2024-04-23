
import com.sebadracco.modak.core.base.dialog.MessageError
import java.io.Serializable

class ExceptionModel : Exception, Serializable {
    var title: String? = null
    var code: String? = null
    var icon: String? = null
    var detail: MessageError? = null
    var primaryButton: ButtonError? = null
    var secondaryButton: ButtonError? = null

    constructor() : super("") {
        icon = ""
        detail = MessageError
        primaryButton = ButtonError
    }

    constructor(code: String?) : super(code) {
        this.code = code
    }

    constructor(
        title: String?,
        code: String?,
        icon: String?,
        message: MessageError?,
        primaryButton: ButtonError?,
        secondaryButton: ButtonError?
    ) : super(title) {
        this.title = title
        this.code = code
        this.icon = icon
        detail = message
        this.primaryButton = primaryButton
        this.secondaryButton = secondaryButton
    }

    fun getDetails(): MessageError? {
        return detail
    }

    fun setDetails(detail: MessageError?) {
        this.detail = detail
    }

    fun getPrimaryButtons(): ButtonError? {
        return primaryButton
    }

    fun setPrimaryButtons(primaryButton: ButtonError?) {
        this.primaryButton = primaryButton
    }

    fun getSecondaryButtons(): ButtonError? {
        return secondaryButton
    }

    fun setSecondaryButtons(secondaryButton: ButtonError?) {
        this.secondaryButton = secondaryButton
    }
}