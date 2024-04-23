package com.sebadracco.modak.detail.view

import BaseActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.sebadracco.modak.R
import com.sebadracco.modak.databinding.ActivityDetailBinding

class DetailViewActivity : BaseActivity<ActivityDetailBinding>() {

    override fun viewOnReady() {
        super.viewOnReady()
        checkInputParams()
    }

    private fun checkInputParams() {
        val inputParams = intent.getParcelableExtra<DetailViewInput>(
            INPUT_VIEW_DATA_ACTIVITY_KEY
        )
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.content_fragment_detail) as NavHostFragment
        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.detail_view_view_navigation)

        navHostFragment.navController.setGraph(graph, Bundle().apply
        {
            putParcelable("inputViewData", inputParams)
        })

    }

    companion object {
        const val INPUT_VIEW_DATA_ACTIVITY_KEY = "inputViewData"

        fun getCallingIntent(context: Context?): Intent {
            return Intent(context, DetailViewActivity::class.java)
        }

        fun getIntent(activity: FragmentActivity, args: DetailViewInput): Intent {
            val intent = Intent(activity, DetailViewActivity::class.java)
            intent.putExtra(INPUT_VIEW_DATA_ACTIVITY_KEY, args)
            return intent
        }
    }
}