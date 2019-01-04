package com.test.fvba.testapp.ui.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

abstract class BaseViewHolder<T : ViewDataBinding>(protected var mBinding: T) : RecyclerView.ViewHolder(mBinding.root) {
    val binding: ViewDataBinding
        get() = mBinding

}
