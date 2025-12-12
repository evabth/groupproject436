package com.example.groupproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TeamActivity : AppCompatActivity() {

    private lateinit var teamView: TeamView
    private var teamName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        teamName = intent.getStringExtra("teamName") ?: ""
        setupView()
    }

    override fun onResume() {
        super.onResume()
        // refresh view to show updated favorite
        setupView()
    }

    private fun setupView() {
        val pref = getSharedPreferences(packageName + "_preferences", Context.MODE_PRIVATE)
        val favoritePlayer = pref.getString("favoritePlayer", null)

        teamView = TeamView(this, teamName, Model.roster, favoritePlayer)

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
