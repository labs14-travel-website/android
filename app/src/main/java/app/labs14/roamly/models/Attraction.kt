package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "attraction_table",
    foreignKeys =
    [(ForeignKey(
        entity = Itinerary::class
        , parentColumns = ["itin_id"]
        , childColumns = ["itinerary_id"]
        , onUpdate = ForeignKey.CASCADE
        , onDelete = ForeignKey.CASCADE
    ))]
)
class Attraction(
    val itinerary_id: Int,
    var name: String,
    var startTime: Long,
    var endTime: Long,
    var alarmBefore: Long,
    var alarmAfter: Long,
    var description: String,
    var lat: String,
    var lng: String,
    var address: String,
    var phoneNum: String,
    var transportType: Int,
    var transportEta: Long,
    var transportLabel: String
) {

    @ColumnInfo(name = "attraction_id")
    @PrimaryKey(autoGenerate = true)
    var attraction_id: Int = 0

    constructor(
        itinerary_id: Int,
        name: String,
        startTime: Long,
        endTime: Long,
        description: String,
        lat: String,
        lng: String,
        address: String,
        phoneNum: String
    ) : this(itinerary_id,name,startTime,endTime,0,0,description,lat,lng,address,phoneNum,0,0,"")
}
