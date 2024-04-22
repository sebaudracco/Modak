import com.sebadracco.core.base.useCase.ErrorModel

sealed class BaseResultWrapper<out T> {
    data class ApiSuccess<out T>(val value: T): BaseResultWrapper<T>()
    data class ApiError(val error: ErrorModel): BaseResultWrapper<Nothing>()
}