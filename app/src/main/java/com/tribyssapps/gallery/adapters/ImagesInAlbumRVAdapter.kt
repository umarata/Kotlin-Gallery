package com.tribyssapps.gallery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tribyssapps.gallery.GlideApp
import com.tribyssapps.gallery.R
import com.tribyssapps.gallery.models.Image

class ImagesInAlbumRVAdapter(val context: Context, val imagesList: ArrayList<Image>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.images_in_album_items,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        GlideApp.with(context)
                .load(imagesList.get(position).path).placeholder(R.mipmap.ic_launcher).centerCrop()
                .dontAnimate().into(viewHolder.imageView)

        viewHolder.textViewImageName.text = imagesList[position].name
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val textViewImageName = itemView.findViewById<TextView>(R.id.textViewImageName)
    }
}