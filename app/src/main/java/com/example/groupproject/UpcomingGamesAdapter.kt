package com.example.groupproject

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.graphics.Color

class GameCardViewHolder(itemView: LinearLayout) : RecyclerView.ViewHolder(itemView) {
    val dateView: TextView = itemView.getChildAt(0) as TextView
    val opponentView: TextView = itemView.getChildAt(1) as TextView
    val WinorLoss: TextView = itemView.getChildAt(2) as TextView

}

class UpcomingGamesAdapter(private val games: List<Model.Game>) :
    RecyclerView.Adapter<GameCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardViewHolder {
        val context = parent.context

        val cardLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            val density = context.resources.displayMetrics.density
            layoutParams = ViewGroup.LayoutParams((150 * density).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
            setPadding((8 * density).toInt(), (8 * density).toInt(), (8 * density).toInt(), (8 * density).toInt())

            setBackgroundColor(0xFFE0E0E0.toInt()) // Light Grey background for the card

            addView(TextView(context).apply { textSize = 16f; setTextColor(0xFF3F51B5.toInt()) })
            addView(TextView(context).apply { textSize = 14f })
            addView(TextView(context).apply { textSize = 14f; setTextColor(0xFF4CAF50.toInt()) })
        }

        return GameCardViewHolder(cardLayout)
    }

    override fun onBindViewHolder(holder: GameCardViewHolder, position: Int) {
        val game = games[position]

        holder.dateView.text = game.date
        holder.opponentView.text = "vs " + game.opponent
        holder.WinorLoss.text = game.outcomeOrTime ?: "TBD"
        if (holder.WinorLoss.text == "W"){
            holder.WinorLoss.setTextColor(Color.parseColor("#00d10e"))
        }else{
            holder.WinorLoss.setTextColor(Color.parseColor("#c7140e"))
        }

    }

    override fun getItemCount() = games.size
}