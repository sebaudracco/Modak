package com.sebadracco.modak.detail.usecase

import DetailEntityResponse
import com.sebadracco.core.base.useCase.BaseUseCase
import com.sebadracco.modak.detail.datasource.entity.DetailModel
import com.sebadracco.modak.detail.datasource.repository.IDetailRepository

class DetailUseCase (private val detailViewRepository: IDetailRepository) :
    BaseUseCase<Int, DetailModel>() {

    override suspend fun run(params: Int) : DetailModel {
        return detailViewRepository.getArtWorksById(params)
    }
}