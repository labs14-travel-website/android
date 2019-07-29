package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "attraction_table",
    foreignKeys =
    [(ForeignKey(
        entity = Itinerary::class
        , parentColumns = ["itinerary_id"]
        , childColumns = ["attraction_id"]
        , onUpdate = ForeignKey.CASCADE
        , onDelete = ForeignKey.CASCADE
    ))]
)
data class Attraction(
    val itinerary_id: Long,
    var name: String,
    var startTime: Long,
    var endTime: Long,
    var alarmBefore:Long,
    var alarmAfter:Long,
    var description:String,
    var lat:Double,
    var lng:Double,
    var address:String,
    var phoneNum:String,
    var transportType: Int,
    var transportEta:Long,
    var transportLabel:String) {

    @PrimaryKey(autoGenerate = true)
    var attraction_id: Long = 0
}
