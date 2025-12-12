package com.example.groupproject

import android.app.AlertDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var db: FirebaseFirestore
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        db = FirebaseFirestore.getInstance()

        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener { finish() }

        var mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0

        // center on USA
        var usa: LatLng = LatLng(39.8283, -98.5795)
        var cameraUpdate = CameraUpdateFactory.newLatLngZoom(usa, 4f)
        map.moveCamera(cameraUpdate)

        var markerListener: MarkerListener = MarkerListener()
        map.setOnMarkerClickListener(markerListener)

        loadTeams()
    }

    fun loadTeams() {
        db.collection("teams").get().addOnSuccessListener { snapshot ->
            for (doc in snapshot.documents) {
                var teamName = doc.getString("name") ?: continue
                var city = doc.getString("city") ?: continue
                geocodeCity(city, teamName)
            }
        }
    }

    fun geocodeCity(city: String, teamName: String) {
        var coder: Geocoder = Geocoder(this)
        try {
            var handler: GeocodingHandler = GeocodingHandler(teamName)
            coder.getFromLocationName(city, 1, handler)
        } catch (e: Exception) {
            Log.w("MapActivity", "exception geocoding: " + e.message)
        }
    }

    inner class MarkerListener : GoogleMap.OnMarkerClickListener {
        override fun onMarkerClick(p0: Marker): Boolean {
            var teamName = p0.title ?: return true

            AlertDialog.Builder(this@MapActivity)
                .setTitle("Select Team")
                .setMessage("Would you like to select " + teamName + "?")
                .setPositiveButton("Yes") { _, _ ->
                    var pref = getSharedPreferences(packageName + "_preferences", Context.MODE_PRIVATE)
                    pref.edit().putString("selectedTeamName", teamName).apply()
                    Toast.makeText(this@MapActivity, "Selected " + teamName, Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No", null)
                .show()

            return true
        }
    }

    inner class GeocodingHandler(var teamName: String) : Geocoder.GeocodeListener {
        override fun onGeocode(p0: List<Address?>) {
            if (p0 != null && p0.size >= 1) {
                var match: Address? = p0.get(0)
                if (match != null) {
                    var lat: Double = match.latitude
                    var long: Double = match.longitude
                    var position: LatLng = LatLng(lat, long)

                    runOnUiThread {
                        var markerOptions: MarkerOptions = MarkerOptions()
                        markerOptions.position(position)
                        markerOptions.title(teamName)
                        map.addMarker(markerOptions)
                    }
                }
            }
        }

        override fun onError(errorMessage: String?) {
            super.onError(errorMessage)
            Log.w("MapActivity", "Geocoding error: " + errorMessage)
        }
    }
}
