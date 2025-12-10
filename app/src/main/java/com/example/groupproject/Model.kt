package com.example.groupproject
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.FirebaseFirestore


// Model that encapsulates the data
// Model will store the team selected on the Main screen
// The model will then load in the roster and schedule associated with the selected team
class Model {

    data class Player(
        val name: String = "",
        val number: String = "",
        val position: String = "",
    )

    data class Game(
        val date: String = "",
        val opponent: String = "",
        // trick to get around the '/' in firebase column name
        // NOTE: I should have stored this in a better way, but retrieving the data was a pain and
        // this was the easiest way I could think of storing it quickly
        @get:PropertyName("outcome/time")
        @set:PropertyName("outcome/time")
        var outcomeOrTime: String? = ""
    )

    // instances of our basketball related data
    private var teamRef: DocumentReference
    private lateinit var roster: List<Player>
    private lateinit var schedule: List<Game>

    // firestore instance
    // get database reference
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Constructor: Initializes the team (passed as a param)
    // Initializes the roster and the schedule lists by calling separate functions
    constructor(teamId: String) {

        teamRef = db.collection("teams").document(teamId)

        // initialize roster list
        initializeRoster()
        // DEBUG
        //Log.w("Model", roster.toString())

        // initialize schedule
        initializeSchedule()
        // DEBUG
        //Log.w("Model", schedule.toString())
    }

    // Reads roster data from firebase and populates the roster list
    // Each element of the roster list will be of type Player
    // Returns nothing
    fun initializeRoster() {
        teamRef.collection("roster").get().addOnSuccessListener { snapshot ->
            val players = snapshot.documents.mapNotNull { doc ->
                val player = doc.toObject(Player::class.java)
                player
            }

            this.roster = players
            Log.w("Model", this.roster.size.toString())
        }.addOnFailureListener { e ->
            Log.e("MainActivity", "Error loading roster", e)
            this.roster = emptyList()
        }
    }

    // Reads schedule data from firebase and populates schedule list
    // Each element of the schedule list will be of type Game
    // Returns nothing
    fun initializeSchedule() {
        teamRef.collection("games").get().addOnSuccessListener { snapshot ->
            val games = snapshot.documents.mapNotNull { doc ->
                val game = doc.toObject(Game::class.java)
                game
            }

            this.schedule = games
            Log.w("Model", this.schedule.size.toString())
        }.addOnFailureListener { e ->
            Log.e("MainActivity", "Error loading schedule", e)
            this.schedule = emptyList()
        }
    }

}