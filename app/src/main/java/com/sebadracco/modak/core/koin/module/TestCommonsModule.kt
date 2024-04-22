package com.sebadracco.modak.core.koin.module


import com.sebadracco.modak.core.base.apiTestModule
import com.sebadracco.modak.KotlinApplication.concatenate
import com.sebadracco.modak.detail.module.detailViewModule
import com.sebadracco.modak.main.module.mainViewModule
import org.koin.core.module.Module


object TestCommonsModule{

    fun getModules(): List<Module>{
        return concatenate(
            getApiModules()
        )
    }

    private fun getApiModules(): List<Module>{
        return listOf(
            apiTestModule,
            testApiArticleModule,
            apiAuthModule,
            mainViewModule,
            detailViewModule
        )
    }
}