package com.example.groupproject

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_FORMAT = "E, MMM dd"
private val currentYear = SimpleDateFormat("yyyy", Locale.US).format(Date())
class ScheduleView : LinearLayout {

    private var calendarView: CalendarView
    private var gameInfoText: TextView
    private var schedule: List<Model.Game>
    private var upcomingGamesStrip: RecyclerView
    private var teamName: String

    constructor(context: Context, teamName: String, schedule: List<Model.Game>) : super(context) {
        this.schedule = schedule
        this.teamName = teamName
        this.orientation = VERTICAL

        // calendar view
        calendarView = CalendarView(context)
        addView(calendarView)

        //setting up recycler view for past ten game record
        upcomingGamesStrip = RecyclerView(context)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        upcomingGamesStrip.layoutManager = layoutManager

        val upcomingSchedule = filterRecentGames(schedule)

        val gamesAdapter = UpcomingGamesAdapter(upcomingSchedule)
        upcomingGamesStrip.adapter = gamesAdapter

        val stripParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        stripParams.topMargin = 16
        addView(upcomingGamesStrip, stripParams)

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


    private fun filterRecentGames(schedule: List<Model.Game>): List<Model.Game> {

        Log.w("ScheduleView", "Schedule: $schedule")
        val now = Date()
        val fullDateFormatter = SimpleDateFormat("$DATE_FORMAT yyyy", Locale.US)


        val recentGames = schedule
            .mapNotNull { game ->
                try {
                    val dateStringWithYear = "${game.date} $currentYear"

                    val gameDate = fullDateFormatter.parse(dateStringWithYear)

                    if (gameDate != null && gameDate.after(now)) {
                        gameDate to game
                    } else if (gameDate != null) {
                        gameDate to game
                    } else {
                        null
                    }
                } catch (e: ParseException) {
                    Log.e("ScheduleView", "Could not parse date for game: ${game.date}. Error: ${e.message}")
                    null
                }
            }
            .filter { (gameDate, _) ->

                gameDate.before(now)
            }
            .sortedByDescending { (gameDate, _) ->
                gameDate
            }
            .take(10)

            .map { (_, game) ->
                game
            }

        Log.w("ScheduleView", "Recent Games: $recentGames")

        return recentGames

    }
}