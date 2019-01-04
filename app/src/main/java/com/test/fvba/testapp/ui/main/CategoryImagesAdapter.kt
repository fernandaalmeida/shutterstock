package com.test.fvba.testapp.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.fvba.testapp.R
import com.test.fvba.testapp.data.model.ImagesCategory
import kotlinx.android.synthetic.main.image_item.view.*

class CategoryImagesAdapter(private val presenter: MainFragmentPresenter<MainFragmentView>, private var context: Context) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /* Private Attributes ****************************************************************************/

    private lateinit var imagesAdapter: ImagesAdapter

    /* Public Methods *******************************************************************************/

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate(R.layout.image_item, parent, false))
    }

    override fun getItemCount(): Int {
       return presenter.getImagesCategoryRowsCount()
    }

    fun updateData(images: ArrayList<ImagesCategory>) {
        presenter.updateImagesList(images)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position, presenter.imagesList[position])
    }

    /* Inner Classes ****************************************************************************/

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //        var flag: ImageView = itemView.iv_country_flag
        fun bind(position: Int, item: ImagesCategory) {

            itemView.category_name.text = item.category.name
            imagesAdapter = ImagesAdapter(presenter, position)
            itemView.rv_image_list.adapter = imagesAdapter

        }


    }

}