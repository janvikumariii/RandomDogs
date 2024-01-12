package com.example.randomdogs

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecentlyGeneratedDogs : AppCompatActivity() {
    val imageDatabaseHelper = ImageDatabaseHelper

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_generated_dogs)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val scrollView: HorizontalScrollView = findViewById(R.id.scrollView)
        val button1: AppCompatButton = findViewById(R.id.button1)
        val linearLayout: LinearLayout = findViewById(R.id.linearLayout)


        // Restore the cache data from SharedPreferences
        val sharedPreferences = getSharedPreferences("my_app_shared_prefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("image_cache_data", null)
        val editor = sharedPreferences.edit()

        if (json != null) {
            val type = object : TypeToken<LinkedHashMap<String, String>>() {}.type
            val cacheData: LinkedHashMap<String, String> = Gson().fromJson(json, type)
            ImageDatabaseHelper.lruCache.evictAll()
            for ((key, value) in cacheData) {
                ImageDatabaseHelper.lruCache.put(key, value)
            }
        }
        // Getting all the cache data (ImageURL)
        val allImageUrls = imageDatabaseHelper.getAllImageUrls()

        if (allImageUrls.isEmpty()) {
        } else {
            for (i in 0..allImageUrls.size - 1) {
                val imageView = ImageView(this)
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.setPadding(0, 0, 20, 0)
                Glide.with(this)
                    .load(allImageUrls[i])
                    .onlyRetrieveFromCache(true)
                    .into(imageView)
                val layoutParams = LinearLayout.LayoutParams(
                    (400 * resources.displayMetrics.density).toInt(),
                    (300 * resources.displayMetrics.density).toInt()
                )
                imageView.layoutParams = layoutParams
                linearLayout.addView(imageView)
            }
        }

        button1.setOnTouchListener { _: View, m: MotionEvent ->
            // Perform tasks here
            val action = m.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    button1.setTextColor(Color.GRAY)
                    imageDatabaseHelper.clearUrls()
                    editor.remove("image_cache_data")
                    editor.apply()
                    Glide.get(this).clearMemory();
                    linearLayout.removeAllViews()
                }

                MotionEvent.ACTION_UP -> {
                    button1.setTextColor(Color.WHITE)

                }
            }
            false
        }
    }

}