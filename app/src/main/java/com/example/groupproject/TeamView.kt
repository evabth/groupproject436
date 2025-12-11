package com.example.groupproject

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class TeamView : LinearLayout {

    private var titleText: TextView
    //private var rosterText: TextView

    private var onPlayerClick: ((Model.Player) -> Unit)? = null

    fun setOnPlayerClickListener(listener: (Model.Player) -> Unit) {
        onPlayerClick = listener
    }

    constructor(context: Context, teamName: String, roster: List<Model.Player>) : super(context) {
        this.orientation = VERTICAL
        this.setPadding(32, 32, 32, 32)

        // team title
        titleText = TextView(context)
        titleText.text = teamName
        titleText.textSize = 28f
        titleText.gravity = Gravity.CENTER
        addView(titleText)

        // roster header
        var rosterHeader = TextView(context)
        rosterHeader.text = "Roster"
        rosterHeader.textSize = 22f
        rosterHeader.gravity = Gravity.CENTER
        rosterHeader.setPadding(0, 32, 0, 16)
        addView(rosterHeader)

        // roster list in scroll view
        var scrollView = ScrollView(context)
        var scrollParams = LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f)

        // Changed each player to be it's own text view
        // this allows us to enable event handling when a player is clicked
        val rosterLayout = LinearLayout(context).apply {
            orientation = VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
        }

        for (player in roster) {
            val playerView = TextView(context).apply {
                text = "#${player.number} ${player.name} (${player.position})"
                textSize = 16f
                gravity = Gravity.CENTER_HORIZONTAL
                setPadding(0, 8, 0, 8)
                isClickable = true
                isFocusable = true

                setOnClickListener {
                    Toast.makeText(context, "Pressed!", Toast.LENGTH_SHORT).show()
                    //onPlayerClick?.invoke(player)
                }
            }
            rosterLayout.addView(playerView)
        }

        scrollView.addView(rosterLayout)
        addView(scrollView, scrollParams)

        // ------------------
        
        //rosterText = TextView(context)
        //rosterText.textSize = 16f
        //rosterText.gravity = Gravity.CENTER_HORIZONTAL
        //var rosterString = ""
        //for (player in roster) {
        //    rosterString += "#" + player.number + " " + player.name + " (" + player.position + ")\n"
        //}
        //rosterText.text = rosterString
        //scrollView.addView(rosterText)
        //addView(scrollView, scrollParams)
    }
}
