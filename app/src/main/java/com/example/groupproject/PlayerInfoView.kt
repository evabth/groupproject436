package com.example.groupproject

import android.widget.LinearLayout
import android.content.Context
import android.view.Gravity
import android.widget.ScrollView
import android.widget.TextView


class PlayerInfoView : LinearLayout {

    private var nameText : TextView
    private var statsText : TextView


    constructor(context: Context, name: String?, number: String?, position: String?, avgAssists: String?, avgBlocks: String?, avgMinutes: String?, avgPoints: String?, avgRebounds: String?, avgSteals: String?, gamesPlayed: String?) : super(context) {
        this.orientation = VERTICAL
        this.setPadding(32, 32, 32, 32)

        // player name
        nameText = TextView(context)
        nameText.text = name
        nameText.textSize = 28f
        nameText.gravity = Gravity.CENTER
        addView(nameText)

        // roster header
        var secondView = TextView(context)
        secondView.text = "${number}, ${position}"
        secondView.textSize = 22f
        secondView.gravity = Gravity.CENTER
        secondView.setPadding(0, 32, 0, 16)
        addView(secondView)

        // roster list in scroll view
        var scrollView = ScrollView(context)
        var scrollParams = LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f)

        statsText = TextView(context)
        statsText.textSize = 16f
        statsText.gravity = Gravity.CENTER_HORIZONTAL
        var rosterString = "Average Assists: ${avgAssists}\n"
        rosterString += "Average Blocks: ${avgBlocks}\n"
        rosterString += "Average Minutes: ${avgMinutes}\n"
        rosterString += "Average Points: ${avgPoints}\n"
        rosterString += "Average Rebounds: ${avgRebounds}\n"
        rosterString += "Average Steals: ${avgSteals}\n"
        rosterString += "Games Played: ${gamesPlayed}\n"


        statsText.text = rosterString
        scrollView.addView(statsText)
        addView(scrollView, scrollParams)

    }

}