package com.example.groupproject

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class TeamView : LinearLayout {

    private var titleText: TextView

    private var onPlayerClick: ((Model.Player) -> Unit)? = null

    fun setOnPlayerClickListener(listener: (Model.Player) -> Unit) {
        onPlayerClick = listener
    }

    fun onClick(player: Model.Player) {
        val intent = Intent(context, PlayerInfoActivity::class.java)
        intent.putExtra("name", player.name)
        intent.putExtra("number", player.number)
        intent.putExtra("position", player.position)
        intent.putExtra("avg_assists", player.avg_assists)
        intent.putExtra("avg_blocks", player.avg_blocks)
        intent.putExtra("avg_minutes", player.avg_minutes)
        intent.putExtra("avg_points", player.avg_points)
        intent.putExtra("avg_rebounds", player.avg_rebounds)
        intent.putExtra("avg_steals", player.avg_steals)
        intent.putExtra("games_played", player.games_played)
        context.startActivity(intent)

    }

    constructor(context: Context, teamName: String, roster: List<Model.Player>, favoritePlayerName: String?) : super(context) {
        this.orientation = VERTICAL
        this.setPadding(32, 32, 32, 32)

        // team title
        titleText = TextView(context)
        titleText.text = teamName
        titleText.textSize = 28f
        titleText.gravity = Gravity.CENTER
        addView(titleText)

        // roster list in scroll view
        var scrollView = ScrollView(context)
        var scrollParams = LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f)

        val rosterLayout = LinearLayout(context).apply {
            orientation = VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
        }

        // find favorite player
        val favoritePlayer = roster.find { it.name == favoritePlayerName }

        // show favorite player at top if exists
        if (favoritePlayer != null) {
            val favHeader = TextView(context).apply {
                text = "Favorite"
                textSize = 20f
                gravity = Gravity.CENTER
                setPadding(0, 16, 0, 8)
            }
            rosterLayout.addView(favHeader)

            val favView = TextView(context).apply {
                text = "#${favoritePlayer.number} ${favoritePlayer.name} (${favoritePlayer.position})"
                textSize = 16f
                gravity = Gravity.CENTER_HORIZONTAL
                setPadding(0, 8, 0, 8)
                isClickable = true
                isFocusable = true
                setOnClickListener { onClick(favoritePlayer) }
            }
            rosterLayout.addView(favView)
        }

        // roster header
        var rosterHeader = TextView(context).apply {
            text = "Roster"
            textSize = 20f
            gravity = Gravity.CENTER
            setPadding(0, 32, 0, 16)
        }
        rosterLayout.addView(rosterHeader)

        // rest of roster (excluding favorite)
        for (player in roster) {
            if (player.name == favoritePlayerName) continue
            
            val playerView = TextView(context).apply {
                text = "#${player.number} ${player.name} (${player.position})"
                textSize = 16f
                gravity = Gravity.CENTER_HORIZONTAL
                setPadding(0, 8, 0, 8)
                isClickable = true
                isFocusable = true
                setOnClickListener { onClick(player) }
            }
            rosterLayout.addView(playerView)
        }

        scrollView.addView(rosterLayout)
        addView(scrollView, scrollParams)
    }
}
