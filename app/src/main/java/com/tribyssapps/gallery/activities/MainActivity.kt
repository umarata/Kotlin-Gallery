package com.tribyssapps.gallery.activities

import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tribyssapps.gallery.R
import com.tribyssapps.gallery.adapters.AlbumRVAdapter
import com.tribyssapps.gallery.models.Album
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var albums: ArrayList<Album> = ArrayList()
    private val projection =
            arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA)
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        val cursor = applicationContext.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                null, MediaStore.Images.Media.DATE_ADDED + " DESC"
        )!!

        val temp = java.util.ArrayList<Album>(cursor.count)
        val albumSet = HashSet<String>()
        var file: File

        if (cursor.moveToNext()) {
            cursor.moveToFirst()
            do {
                if (Thread.interrupted()) {
                    return
                }
                val album = cursor.getString(cursor.getColumnIndex(projection.get(0)))
                val image = cursor.getString(cursor.getColumnIndex(projection.get(1)))

                file = File(image)
                val fileExists = file.exists()
                val fileNotAddedInAlbumSet = !albumSet.contains(album)
                if (fileExists && fileNotAddedInAlbumSet) {
                    temp.add(Album(album, image))
                    albumSet.add(album)
                }
            } while (cursor.moveToNext())
        }
        albums.clear()
        albums.addAll(temp)
        var staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = AlbumRVAdapter(this, albums)

    }
}