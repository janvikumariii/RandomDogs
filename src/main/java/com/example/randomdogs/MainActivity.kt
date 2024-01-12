package com.example.randomdogs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton


class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //          Remove Action Bar
        supportActionBar?.hide()

        val generateButton : AppCompatButton = findViewById(R.id.generateButton)
        val recentButton : AppCompatButton = findViewById(R.id.recentButton)

        generateButton.setOnTouchListener { _: View, m: MotionEvent ->
            // Perform tasks here
            val action=m.action
            when(action){
                MotionEvent.ACTION_DOWN->{
                    generateButton.setTextColor(Color.GRAY)
                }
                MotionEvent.ACTION_UP->{
                    generateButton.setTextColor(Color.WHITE)

                }
            }
            false
        }

        generateButton.setOnClickListener{
            val intent = Intent(this, GenerateDogs::class.java);
            startActivity(intent)
        }


        recentButton.setOnTouchListener { _: View, m: MotionEvent ->
            // Perform tasks here
            val action=m.action
            when(action){
                MotionEvent.ACTION_DOWN->{
                    recentButton.setTextColor(Color.GRAY)
                }
                MotionEvent.ACTION_UP->{
                    recentButton.setTextColor(Color.WHITE)

                }
            }
            false
        }
        recentButton.setOnClickListener{
            val intent = Intent(this, RecentlyGeneratedDogs::class.java);
            startActivity(intent)
        }

    }

}