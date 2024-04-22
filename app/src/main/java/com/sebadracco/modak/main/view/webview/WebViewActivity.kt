package com.sebadracco.modak.main.view.webview

import BaseActivity
import com.sebadracco.modak.databinding.WebviewFragmentBinding


class WebViewActivity
    : BaseActivity<WebviewFragmentBinding>() {

    override fun viewOnReady() {
        super.viewOnReady()
        val url = intent?.extras?.getString("url", "")
        url?.let {
            binding.webView.loadUrl(url)
        }

        val title = intent?.extras?.getString("title", "")
        title?.let {
            binding.toolbar.title = title
        }

    }
}

