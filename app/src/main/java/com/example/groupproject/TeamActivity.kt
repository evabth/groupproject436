package com.example.groupproject

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val teamName = intent.getStringExtra("teamName") ?: ""
        val teamView = TeamView(this, teamName, Model.roster)
        
        val scheduleButton = Button(this)
        scheduleButton.text = "View Schedule"
        scheduleButton.setOnClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            intent.putExtra("teamName", teamName)
            startActivity(intent)
        }
        teamView.addView(scheduleButton)
        
        val backButton = Button(this)
        backButton.text = "Back"
        backButton.setOnClickListener { finish() }
        val backParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        backParams.gravity = Gravity.CENTER_HORIZONTAL
        backParams.topMargin = 16
        teamView.addView(backButton, backParams)
        
        setContentView(teamView)
    }
}
