package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "itinerary_table",
    foreignKeys =
    [(ForeignKey(
        entity = User::class
        , parentColumns = ["user_id"]
        , childColumns = ["itinerary_id"]
        , onUpdate = ForeignKey.CASCADE
        , onDelete = ForeignKey.CASCADE
    ))]
)
class Itinerary(val userId: Long, var description: String) {

    @PrimaryKey(autoGenerate = true)
    var itinerary_id: Long = 0

    @Ignore
    var attractions = listOf<Attraction>()


}