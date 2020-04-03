package com.big.imageloader.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.big.imageloader.R
import com.big.imageloader.data.remote.response.search_response.Value
import com.big.imageloader.ui.fragment.GetNextItems
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class SelectableViewAdapter(
    private var imageList: MutableList<Value>,
    private var glide: RequestManager
) :
    RecyclerView.Adapter<SelectableViewAdapter.ImageViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    lateinit var requestForNextItem: GetNextItems

    override fun getItemCount() = imageList.size

    companion object {
        val TAG = "SelectableViewAdapter"
    }

    fun setOnItemClickListener(
        itemClickListener: View.OnClickListener,
        requestForNextItem: GetNextItems
    ) {
        onClickListener = itemClickListener
        this.requestForNextItem = requestForNextItem
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position], position)
        if (position == imageList.size - 1) {
            requestForNextItem.callForNext()
        }
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val selectableImage: ImageView = view.findViewById(R.id.iv_holder_adapter)
        private val progressCircle: ProgressBar = view.findViewById(R.id.progress_circle)

        fun bind(city: Value, position: Int) {

            itemView.tag = position
            itemView.setOnClickListener(onClickListener)

            glide.load(city.thumbnail)
                .centerCrop()
                .error(R.drawable.ic_error_outline_black_24dp)
                .placeholder(R.drawable.ic_cloud_download_black_24dp)
                .addListener(object : RequestListener<Drawable?> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressCircle.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressCircle.visibility = View.GONE
                        return false
                    }
                })
                .into(selectableImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_selectable_image, parent, false)
    )


}