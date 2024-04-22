package com.sebadracco.modak.detail.datasource.repository

import DetailEntityResponse
import com.sebadracco.modak.detail.datasource.entity.DetailModel
import com.sebadracco.modak.detail.datasource.service.IDetailService
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val iService: IDetailService,
) : IDetailRepository {


    @Throws(Exception::class)
    override suspend fun getArtWorksById(params: Int): DetailModel {
        return iService.getArtWorksById(params)
    }
}