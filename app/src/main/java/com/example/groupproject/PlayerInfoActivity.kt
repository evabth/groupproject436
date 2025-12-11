package com.example.groupproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PlayerInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val name = intent.getStringExtra("name")
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
        setContentView(playerInfoView)


    }
}