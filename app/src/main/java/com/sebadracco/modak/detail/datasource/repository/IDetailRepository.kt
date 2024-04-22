package com.sebadracco.modak.detail.datasource.repository

import DetailEntityResponse
import com.sebadracco.modak.detail.datasource.entity.DetailModel


interface IDetailRepository {
    @Throws(Exception::class)
    suspend fun getArtWorksById(params: Int): DetailModel

}
