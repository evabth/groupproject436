package com.example.groupproject

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
class MainActivity : AppCompatActivity() {

    data class Team(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 1. Get view references
        teamSelection = findViewById(R.id.teamSelection)
        submitButton = findViewById(R.id.submitButton)

        // get database reference
        db = FirebaseFirestore.getInstance()

        // 2) Load teams and populate the dropdown when ready
        loadTeams { teams ->
            setupTeamDropdown(teams)
        }

    }

    private fun loadTeams(onLoaded: (List<Team>) -> Unit) {
        // First, test fetching a single document directly
        db.collection("teams").document("1").get()
            .addOnSuccessListener { doc ->
                Log.d("MainActivity", "Single doc test - exists: ${doc.exists()}")
                Log.d("MainActivity", "Single doc test - data: ${doc.data}")
            }
            .addOnFailureListener { e ->
                Log.e("MainActivity", "Single doc test failed", e)
            }

        // Now fetch all teams
        db.collection("teams")
            .get()
            .addOnSuccessListener { snapshot ->
                Log.d("MainActivity", "Snapshot isEmpty: ${snapshot.isEmpty}")
                Log.d("MainActivity", "Snapshot size: ${snapshot.size()}")
                Log.d("MainActivity", "Snapshot isFromCache: ${snapshot.metadata.isFromCache}")
                Log.d("MainActivity", "Raw documents: ${snapshot.documents}")
                
                val teams = snapshot.documents.mapNotNull { doc ->
                    Log.d("MainActivity", "Doc ID: ${doc.id}, Data: ${doc.data}")
                    val team = doc.toObject(Team::class.java)
                    Log.d("MainActivity", "Loaded team: $team")
                    team
                }
                Log.d("MainActivity", "Total teams loaded: ${teams.size}")
                onLoaded(teams)
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