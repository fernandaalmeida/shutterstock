package com.test.fvba.testapp.ui.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.test.fvba.testapp.data.model.ImageData
import com.test.fvba.testapp.databinding.MainImageDataItemBinding
import com.test.fvba.testapp.ui.base.BaseViewHolder


class ImagesAdapter(private val presenter: SearchFragmentPresenter<SearchFragmentView>) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    /* Private Attributes ****************************************************************************/


    /* Public Methods *******************************************************************************/

    override fun getItemCount(): Int {
        return presenter.getImagesRowsCount()
    }

    fun updateData(imageList: ArrayList<ImageData>) {
        presenter.updateImagesList(imageList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val binding = MainImageDataItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(presenter.imagesList[position])
        }
    }

    /* Inner Classes ****************************************************************************/

    inner class ViewHolder(binding: MainImageDataItemBinding) : BaseViewHolder<MainImageDataItemBinding>(binding) {
        fun bind(item: ImageData) {
            mBinding.image = item
            binding.executePendingBindings()
        }
    }

}