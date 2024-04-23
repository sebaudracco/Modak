package com.sebadracco.modak.main.view

import BaseFragment
import BaseViewModel
import DetailEntityResponse
import Event
import IOnItemClickViewHolder
import MainAdapter
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sebadracco.core.base.util.NetworkUtils
import com.sebadracco.modak.R
import com.sebadracco.modak.databinding.MainViewFragmentBinding
import com.sebadracco.modak.detail.view.DetailViewActivity
import com.sebadracco.modak.detail.view.DetailViewInput
import com.sebadracco.modak.main.viewmodel.MainViewModel
import observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<MainViewFragmentBinding>() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var adapter: MainAdapter
    private var artWork: List<DetailEntityResponse> = arrayListOf()

    override fun getViewModel(): BaseViewModel = mainViewModel

    override fun bindObserversToLiveData() {
        observe(mainViewModel.bindingDelegate.showProgress, this::showProgress)
        observe(mainViewModel.bindingDelegate.hideProgress, this::hideProgress)
        observe(mainViewModel.bindingDelegate.showUnknownError, this::showUnknownError)
        observe(mainViewModel.bindingDelegate.showlistOfArtWork, this::showlistOfArtWork)
    }


    override fun viewOnReady() {
        initViews()
    }


    private fun showlistOfArtWork(event: Event<List<DetailEntityResponse>?>) {
        event.getContentIfNotHandled().let { it ->
            it?.apply {
                it?.let { results ->
                    if (results.isEmpty()) {
                        getScreenError()
                    } else {
                        artWork = results
                        adapter.let {
                            it.dataList = artWork as List<DetailEntityResponse>?
                            it.context = requireActivity()
                            it.setData(artWork)
                            it.notifyDataSetChanged()
                        }
                    }
                } ?: run {
                    getScreenError()
                }
            }
        }
    }

    private fun getScreenError() {
        bindingView.genericError.root.toVisible()
        bindingView.genericError.tvActionServiceError.setOnClickListener {
            bindingView.genericError.root.toGone()
            initViews()
        }
    }

    private fun getScreenConnectionError() {
        bindingView.notNetwork.root.toVisible()
        bindingView.notNetwork.btnReload.setOnClickListener {
            bindingView.notNetwork.root.toGone()
            initViews()
        }
    }


    private fun initViews() {
        initViewAdapter()
        setView()
        search()
    }

    private fun search() {
        if (checkBasicConnection()) {
            mainViewModel.getArticles()
        } else {
            getScreenConnectionError()
        }
    }

    private fun setView() {
        initViewAdapter()
    }


    private fun initViewAdapter() {
        adapter = MainAdapter(
            context = requireActivity(),
            dataList = arrayListOf(),
            onItemClickListener = (object : IOnItemClickViewHolder {

                override fun onItemClick(
                    caller: View?,
                    position: Int,
                    artWork: DetailEntityResponse?
                ) {
                    if (isActionEnable()) {
                        artWork?.id?.let {
                            onPickArticleScreen(it)
                        }?: kotlin.run {
                            Snackbar.make(
                                requireView(),
                                R.string.general_error_message,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }

                    }
                }
            })
        )
        bindingView.rvarticles.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        bindingView.rvarticles.adapter = adapter
    }

    private fun onPickArticleScreen(id: Int) {
        requireActivity().startActivity(
            DetailViewActivity.getIntent(
                activity = requireActivity(),
                args = DetailViewInput(id)
            )
        )
    }


    private fun showProgress(event: Event<Unit>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                bindingView.progress.vProgress.toVisible()
            }
        }
    }

    private fun hideProgress(event: Event<Unit>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                bindingView.progress.vProgress.toGone()
            }
        }
    }


    private fun showUnknownError(event: Event<Unit>) {
        event.getContentIfNotHandled().let {
            it?.apply {
                bindingView.genericError.root.toVisible()
                bindingView.genericError.tvActionServiceError.setOnClickListener {
                    bindingView.genericError.root.toGone()
                    mainViewModel.getArticles()
                }
            }
        }
    }


    private fun checkBasicConnection(): Boolean {
        val networkUtils = NetworkUtils(this.requireContext())
        return if (!networkUtils.isNetworkConnected()) {
            Snackbar.make(
                requireView(),
                R.string.no_internet_limited_content,
                Snackbar.LENGTH_LONG
            ).show()
            false
        } else {
            true
        }
    }

}