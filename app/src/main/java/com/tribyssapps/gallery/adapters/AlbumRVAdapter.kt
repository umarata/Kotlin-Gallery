package com.tribyssapps.gallery.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tribyssapps.gallery.GlideApp
import com.tribyssapps.gallery.R
import com.tribyssapps.gallery.activities.ImagesInAlbumActivity
import com.tribyssapps.gallery.models.Album

class AlbumRVAdapter(val context: Context, val albumList: ArrayList<Album>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.album_items, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        GlideApp.with(context)
                .load(albumList.get(position).cover)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop().dontAnimate().into(viewHolder.imageViewAlbumCover)
        viewHolder.textViewAlbumName.text = albumList[position].name
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, ImagesInAlbumActivity::class.java)
            intent.putExtra("album", albumList[position].name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewAlbumCover = itemView.findViewById<ImageView>(R.id.imageViewAlbumCover)
        val textViewAlbumName = itemView.findViewById<TextView>(R.id.textViewAlbumName)
    }
}