package com.big.imageloader.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.big.imageloader.R

class SelectableViewAdapter(private var cities: MutableList<String>) :
    RecyclerView.Adapter<SelectableViewAdapter.ImageViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun getItemCount() = cities.size

    companion object {
        val TAG = "SelectableViewAdapter"
    }

    fun updateImageList(newCities: MutableList<String>) {
        cities.clear()
        cities.addAll(newCities)
        Log.d(TAG, "$cities")
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(itemClickListener: View.OnClickListener) {
        onClickListener = itemClickListener
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val selectableImage: ImageView = view.findViewById(R.id.iv_holder_adapter)

        fun bind(city: String) {
            itemView.tag = city

            itemView.setOnClickListener(onClickListener)
//            selectableImage.setImageIcon()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_selectable_image, parent, false)
    )


}