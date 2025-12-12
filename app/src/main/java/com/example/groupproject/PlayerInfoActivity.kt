package com.example.groupproject

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PlayerInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val name = intent.getStringExtra("name") ?: ""
        val number = intent.getStringExtra("number")
        val position = intent.getStringExtra("position")
        val avg_assists = intent.getStringExtra("avg_assists")
        val avg_blocks = intent.getStringExtra("avg_blocks")
        val avg_minutes = intent.getStringExtra("avg_minutes")
        val avg_points = intent.getStringExtra("avg_points")
        val avg_rebounds = intent.getStringExtra("avg_rebounds")
        val avg_steals = intent.getStringExtra("avg_steals")
        val games_played = intent.getStringExtra("games_played")

        val playerInfoView = PlayerInfoView(this, name, number, position, avg_assists, avg_blocks, avg_minutes, avg_points, avg_rebounds, avg_steals, games_played)

        // favorite button
        val pref = getSharedPreferences(packageName + "_preferences", Context.MODE_PRIVATE)
        val favoriteButton = Button(this)
        favoriteButton.text = "Favorite"
        favoriteButton.setOnClickListener {
            pref.edit().putString("favoritePlayer", name).apply()
            Toast.makeText(this, "Favorited " + name, Toast.LENGTH_SHORT).show()
        }
        val favParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        favParams.gravity = Gravity.CENTER_HORIZONTAL
        favParams.topMargin = 16
        playerInfoView.addView(favoriteButton, favParams)

        val backButton = Button(this)
        backButton.text = "Back"
        backButton.setOnClickListener { finish() }
        val backParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        backParams.gravity = Gravity.CENTER_HORIZONTAL
        backParams.topMargin = 16
        playerInfoView.addView(backButton, backParams)

        setContentView(playerInfoView)
    }
}