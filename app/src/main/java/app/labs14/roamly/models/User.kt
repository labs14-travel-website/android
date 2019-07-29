package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(tableName = "user_table")

class User(
    var name: String = ""
) {

    @PrimaryKey(autoGenerate = true)
    var user_id: Long = 0

    @Ignore
    var Itineraries = listOf<Itinerary>()
}