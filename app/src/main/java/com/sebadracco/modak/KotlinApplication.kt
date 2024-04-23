package com.sebadracco.modak

import android.app.Application
import android.content.Context
import com.sebadracco.modak.core.base.apiTestModule
import com.sebadracco.modak.core.koin.module.TestCommonsModule
import com.sebadracco.modak.core.koin.module.apiAuthModule
import com.sebadracco.modak.core.koin.module.testApiArticleModule
import com.sebadracco.modak.main.module.mainViewModule
import org.koin.android.ext.koin.androidContext



import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.java.KoinJavaComponent

object KotlinApplication {
    val API_BASE_URL = "https://api.artic.edu/api/v1/"
    fun onCreate(androidContext: Context, application: Application, list: List<Module>) {
        startKoin {
            androidLogger(Level.INFO)
            androidContext(androidContext)
            koin.loadModules(
                concatenate(
                    list,
                    listOf(
                        apiTestModule,
                        testApiArticleModule,
                        apiAuthModule,
                    )
                )
            )
        }
        KoinJavaComponent.getKoin().setProperty("BASE_URL", API_BASE_URL)
    }

    fun getModules(): List<Module> {
        return concatenate(
            TestCommonsModule.getModules(),
        )
    }

    fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
    }
}