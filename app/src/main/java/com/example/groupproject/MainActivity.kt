package com.example.groupproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
class MainActivity : AppCompatActivity() {

    data class Team(
        val id: String = "",           // Document ID from Firestore
        val abbreviation: String = "",
        val city: String = "",
        val conference: String = "",
        val division: String = "",
        val name: String = "",
        val short_name: String = ""
    )

    private lateinit var teamSelection : AutoCompleteTextView
    private lateinit var submitButton : Button

    // Firestore instance
    private lateinit var db: FirebaseFirestore

    // reference to our model class
    private lateinit var model : Model

    // list of all of our teams
    private lateinit var teams : List<Team>

    // selected team name
    private lateinit var selectedName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 1. Get view references
        teamSelection = findViewById(R.id.teamSelection)
        submitButton = findViewById(R.id.submitButton)
        submitButton.isEnabled = false

        // get database reference
        db = FirebaseFirestore.getInstance()

        // 2) Load teams and populate the dropdown when ready
        loadTeams { teams ->
            setupTeamDropdown(teams)
        }

        // enable submit button when a team is selected
        teamSelection.setOnItemClickListener { _, _, _, _ ->
            selectedName = teamSelection.text.toString()
            val selectedTeam = teams.find { it.name == selectedName }
            if (selectedTeam != null) {
                submitButton.isEnabled = true
                model = Model(selectedTeam.id)
            }
        }

        // Set up functionality for the submit button
        submitButton.setOnClickListener { onSubmit() }

    }

    private fun onSubmit() {
        val selectedTeam = teams.find { it.name == selectedName }
        if (selectedTeam != null) {
            // You now have the full Team object with the document ID
            val intent = Intent(this, TeamActivity::class.java)
            intent.putExtra("teamName", selectedTeam.name)
            startActivity(intent)
        } else {
            // Handle invalid choice (user typed something random)
            Toast.makeText(this, "Please select a valid team", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadTeams(onLoaded: (List<Team>) -> Unit) {
        // fetch all teams
        db.collection("teams")
            .get()
            .addOnSuccessListener { snapshot ->

                val teams = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Team::class.java)?.copy(id = doc.id)
                }
                onLoaded(teams)
                this.teams = teams
            }
            .addOnFailureListener { e ->
                Log.e("MainActivity", "Error loading teams", e)
                onLoaded(emptyList())
            }
    }

    private fun setupTeamDropdown(teams: List<Team>) {
        val teamNames = teams.map { it.name }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            teamNames
        )

        teamSelection.setAdapter(adapter)
    }

}