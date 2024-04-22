import com.google.gson.annotations.SerializedName


data class ApiSearchResultRawA(
    @SerializedName("data") val internalData: List<DetailEntityResponse>
)

data class DetailEntityResponse(
    @SerializedName("title") val title: String,
    @SerializedName("hero_caption") val titleDisplay: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("short_description") val shortDescription: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("date_display") val date: String?
)


