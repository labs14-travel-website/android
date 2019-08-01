package app.labs14.roamly.models

import androidx.room.*

//Brandon Lively - 07/28/2019

@Entity(
    tableName = "attraction_table"
/*    foreignKeys =
    [(ForeignKey(
        entity = Itinerary::class
        , parentColumns = ["itin_id"]
        , childColumns = ["itin_id"]
        , onUpdate = ForeignKey.CASCADE
        , onDelete = ForeignKey.CASCADE
    ))]*/
)
class Attraction(
    @PrimaryKey
    var attraction_id: Int,
    var itin_id: Int,
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

/*    @ColumnInfo(name = "attraction_id")
    @PrimaryKey(autoGenerate = false)
    var attraction_id: Int = 0*/

    constructor(
        attraction_id: Int,
        itin_id: Int,
        name: String,
        startTime: Long,
        endTime: Long,
        description: String,
        lat: String,
        lng: String,
        address: String,
        phoneNum: String
    ) : this(attraction_id,itin_id,name,startTime,endTime,0,0,description,lat,lng,address,phoneNum,0,0,"")

    constructor(
        attraction_id: Int,
        itin_id: Int,
        name: String,
        lat: String,
        lng: String
    ) : this(attraction_id,itin_id,name,3200000,3200000,0,0,"Description",lat,lng,"Address","Phone Number",0,0,"")
}
