package com.sebadracco.modak.detail.view

import BaseFragment
import BaseViewModel
import Event
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.sebadracco.core.base.util.NetworkUtils
import com.sebadracco.modak.R
import com.sebadracco.modak.core.base.getViewInputForActivity
import com.sebadracco.modak.databinding.DetailViewFragmentBinding
import com.sebadracco.modak.detail.datasource.entity.DetailModel
import com.sebadracco.modak.detail.datasource.entity.DetailResponse
import com.sebadracco.modak.detail.viewModel.DetailViewModel
import observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<DetailViewFragmentBinding>() {

    private val detailViewModel: DetailViewModel by viewModel()
    private var artWork: DetailResponse? = null
    private var viewInput: DetailViewInput? = null

    override fun getViewModel(): BaseViewModel = detailViewModel

    override fun bindObserversToLiveData() {
        observe(detailViewModel.bindingDelegate.showProgress, this::showProgress)
        observe(detailViewModel.bindingDelegate.hideProgress, this::hideProgress)
        observe(detailViewModel.bindingDelegate.showUnknownError, this::showUnknownError)
        observe(detailViewModel.bindingDelegate.showDetailArtWork, this::showDetailArtWork)
    }


    override fun viewOnReady() {
        checkInput()
        getViewdetail()
        setListeners()
    }

    private fun setListeners() {
        bindingView.toolbar.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun getViewdetail() {
        if (checkBasicConnection()) {
            initViews()
        } else {
            getScreenConnectionError()
        }
    }

    private fun checkInput() {
        viewInput = requireActivity().getViewInputForActivity()
    }

    private fun showDetailArtWork(event: Event<DetailModel?>) {
        event.getContentIfNotHandled().let { it ->
            it?.let { detail ->
                artWork = detail.detailData
                setImagequality(detail.detailData.imageUrl)
                bindingView.expandedTitle.text = detail.detailData.title
                bindingView.description.text = if (detail.detailData.description != null) {
                    HtmlCompat.fromHtml(
                        detail.detailData.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                } else {
                    "UnAvailable Description"
                }
                bindingView.metaData.text = detail.detailData.link
                bindingView.toolbar.title = detail.detailData.title
                bindingView.toolbar.setTitleTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray_underline
                    ))
                bindingView.location.text = detail.detailData.location
            } ?: run {
                getScreenError()
            }
        }
    }

    private fun setImagequality(imageUrl: String?) {
        val options = RequestOptions()
            .dontAnimate()
            .dontTransform()

        Glide.with(this)
            .load(imageUrl)
            .apply(options)
            .error(R.drawable.placeholder_large)
            .placeholder(R.color.darkGrey)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(bindingView.image)
    }

    private fun getScreenError() {
        bindingView.genericError.root.toVisible()
        bindingView.genericError.tvActionServiceError.setOnClickListener {
            bindingView.genericError.root.toGone()
        }
    }

    private fun getScreenConnectionError() {
        bindingView.notNetwork.root.toVisible()
        bindingView.notNetwork.btnReload.setOnClickListener {
            bindingView.notNetwork.root.toGone()
            viewInput?.id?.let {
                detailViewModel.getWork(it)
            } ?: kotlin.run {
                getScreenError()
            }
        }
    }


    private fun initViews() {
        viewInput?.id?.let {
            detailViewModel.getWork(it)
        } ?: kotlin.run {
            getScreenError()
        }
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
                    viewInput?.id?.let {
                        detailViewModel.getWork(it)
                    } ?: kotlin.run {
                        getScreenError()
                    }
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