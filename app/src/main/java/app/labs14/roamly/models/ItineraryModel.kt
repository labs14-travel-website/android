package app.labs14.roamly.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


//shoon 2019/07/26
@Entity(tableName = "itinerary_table")
data class ItineraryModel(
    var title: String?="Nothing paticular",
    var startTime:Long?=1,
    var endTime:Long?=2,
    var description:String?="Nothing to say but you need to pay attention if described here something",
  //  var locationID: LocationModel?=null,
  //  var activities: ActivityModel?=null,
    var priority: Int?=1

) {
    @PrimaryKey(autoGenerate = true)
    var id: Long= 0
}

