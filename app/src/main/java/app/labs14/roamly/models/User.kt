package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

class User(
    var name: String = ""
) {
    var Itineraries = listOf<Itinerary>()
}