package com.sebadracco.modak.detail.datasource.entity

import com.google.gson.annotations.SerializedName


data class DetailModel(
    @SerializedName("data") val detailData: DetailResponse
)

data class DetailResponse(
    @SerializedName("title") val title: String,
    @SerializedName("title_display") val titleDisplay: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("short_description") val shortDescription: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("date_display")
    val date: String?,
    @SerializedName("list_description")
    val listDescription: String?,
    @SerializedName("api_link")
    val link: String?,
)
