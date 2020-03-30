package com.big.imageloader.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.big.imageloader.R
import com.big.imageloader.data.remote.response.search_response.Value
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class SelectableViewAdapter(private var imageList: MutableList<Value>,
                            private var picasso: Picasso) :
    RecyclerView.Adapter<SelectableViewAdapter.ImageViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun getItemCount() = imageList.size

    companion object {
        val TAG = "SelectableViewAdapter"
    }

    fun updateImageList(newImages: MutableList<Value>) {
        //TODO UPDATE List
        imageList.clear()
        imageList.addAll(newImages)
        Log.d(TAG, "$imageList")
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(itemClickListener: View.OnClickListener) {
        onClickListener = itemClickListener
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position], position)
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val selectableImage: ImageView = view.findViewById(R.id.iv_holder_adapter)
        private val progressCircle : ProgressBar = view.findViewById(R.id.progress_circle)

        fun bind(city: Value, position:Int) {
            itemView.tag = position

            itemView.setOnClickListener(onClickListener)
            picasso.load(city.thumbnail)
                .placeholder(R.drawable.ic_cloud_download_black_24dp)
                .into(selectableImage, object : Callback {
                    override fun onSuccess() {
                        progressCircle.visibility = View.GONE
                    }

                    override fun onError(ex: Exception) {

                    }
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_selectable_image, parent, false)
    )


}