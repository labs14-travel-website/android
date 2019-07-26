package app.labs14.roamly.models

import androidx.room.Entity
import androidx.room.PrimaryKey


//shoon 2019/07/26
@Entity(tableName = "activity_table")
class ActivityModel(
        val name : String,
      //  var location: LocationModel,
        var arrivalTime:Long,
        var departureTime:Long,
        var alarmBefore:Long,
        var alarmEnding:Long,
        var comment:String

) {
        @PrimaryKey(autoGenerate = true)
        var id: Long= 0
}




