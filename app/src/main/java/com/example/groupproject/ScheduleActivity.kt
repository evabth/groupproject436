package com.example.groupproject

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val teamName = intent.getStringExtra("teamName") ?: ""
        val scheduleView = ScheduleView(this, teamName, Model.schedule)
        
        val backButton = Button(this)
        backButton.text = "Back"
        backButton.setOnClickListener { finish() }
        val backParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        backParams.gravity = Gravity.CENTER_HORIZONTAL
        backParams.topMargin = 16
        scheduleView.addView(backButton, backParams)
        
        setContentView(scheduleView)
    }
}
