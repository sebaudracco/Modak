package com.sebadracco.modak.core.base.view

import DetailEntityResponse
import IOnItemClickViewHolder
import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(
    binding: ViewBinding,
    private val onItemClickListener: IOnItemClickViewHolder? = null
) : RecyclerView.ViewHolder(binding.root) {
    lateinit var dataList: DetailEntityResponse
    init {
        binding.root.setOnClickListener {
            if (isActionEnable()) onItemClickListener?.onItemClick(it, adapterPosition, dataList)
        }
    }

    var data: T? = null
        private set

    open fun bindingDataInHolder(data: T) {
        this.data = data
    }

    private var mLastClickTime: Long = 0

    protected fun isActionEnable(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return false
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return true
    }

}