package com.sebadracco.modak.core.base

import android.app.Application
import com.google.firebase.FirebaseApp
import com.sebadracco.modak.KotlinApplication

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        PerformanceManager.initPerformance()
        KotlinApplication.onCreate(this, this, KotlinApplication.getModules())
    }


}
