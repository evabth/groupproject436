package com.example.groupproject

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class TeamView : LinearLayout {

    private var titleText: TextView
    private var rosterText: TextView

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
        
        rosterText = TextView(context)
        rosterText.textSize = 16f
        rosterText.gravity = Gravity.CENTER_HORIZONTAL
        var rosterString = ""
        for (player in roster) {
            rosterString += "#" + player.number + " " + player.name + " (" + player.position + ")\n"
        }
        rosterText.text = rosterString
        scrollView.addView(rosterText)
        addView(scrollView, scrollParams)
    }
}
