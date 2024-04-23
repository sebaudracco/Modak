package com.sebadracco.modak.detail.datasource.service


import com.sebadracco.modak.detail.datasource.entity.DetailModel
import retrofit2.http.GET
import retrofit2.http.Path

interface IDetailService {

    @GET("events/{id}")
    suspend fun getArtWorksById(
        @Path("id") id : Int,
    ) : DetailModel
}