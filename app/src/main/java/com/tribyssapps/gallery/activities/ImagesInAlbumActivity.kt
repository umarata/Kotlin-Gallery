package com.tribyssapps.gallery.activities

import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tribyssapps.gallery.R
import com.tribyssapps.gallery.adapters.ImagesInAlbumRVAdapter
import com.tribyssapps.gallery.models.Image
import java.io.File

class ImagesInAlbumActivity : AppCompatActivity() {
    private var images: ArrayList<Image> = ArrayList()
    lateinit var recyclerView: RecyclerView
    private val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_in_album)
        recyclerView = findViewById(R.id.recyclerView)
        val album = intent.getStringExtra("album") + ""
        supportActionBar.apply {
            title = album
        }
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " =?", arrayOf<String>(album), MediaStore.Images.Media.DATE_ADDED
                + " DESC")
        if (cursor == null) return
        val temp: ArrayList<Image> = ArrayList<Image>(cursor.count)

        if (cursor.moveToNext()) {
            cursor.moveToFirst()
            do {
                if (Thread.interrupted()) {
                    return
                }
                val id = cursor.getLong(cursor.getColumnIndex(projection.get(0)))
                val name = cursor.getString(cursor.getColumnIndex(projection.get(1)))
                val path = cursor.getString(cursor.getColumnIndex(projection.get(2)))
                val file = File(path)
                if (file.exists()) {
                    temp.add(Image(id, name, path, false))
                }
            } while (cursor.moveToNext())
        }
        cursor.close()

        images.clear()
        images.addAll(temp)
        var staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = ImagesInAlbumRVAdapter(this, images)
    }
}