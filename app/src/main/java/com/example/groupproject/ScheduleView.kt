package com.example.groupproject

import android.content.Context
import android.view.Gravity
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.TextView

class ScheduleView : LinearLayout {

    private var calendarView: CalendarView
    private var gameInfoText: TextView
    private var schedule: List<Model.Game>
    private var teamName: String

    constructor(context: Context, teamName: String, schedule: List<Model.Game>) : super(context) {
        this.schedule = schedule
        this.teamName = teamName
        this.orientation = VERTICAL

        // calendar view
        calendarView = CalendarView(context)
        addView(calendarView)

        // text view for game info
        gameInfoText = TextView(context)
        gameInfoText.textSize = 20f
        gameInfoText.text = teamName + " Schedule"
        gameInfoText.gravity = Gravity.CENTER
        var textParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        textParams.topMargin = 32
        addView(gameInfoText, textParams)
        
        // date change listener
        calendarView.setOnDateChangeListener { _, year, month, day ->
            var days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            var months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
            
            var calendar = java.util.Calendar.getInstance()
            calendar.set(year, month, day)
            var dayOfWeek = days[calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1]
            
            var dateString = dayOfWeek + ", " + months[month] + " " + day
            showGameForDate(dateString)
        }
    }

    private fun showGameForDate(date: String) {
        val game = schedule.find { it.date == date }
        if (game != null) {
            gameInfoText.text = teamName + " vs " + game.opponent + "\n" + (game.outcomeOrTime ?: "")
        } else {
            gameInfoText.text = "No game on " + date
        }
    }
}