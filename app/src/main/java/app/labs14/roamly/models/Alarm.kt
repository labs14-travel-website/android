package app.labs14.roamly.models



import androidx.room.*

//Shoon 2019/08/29

@Entity(tableName = "alarm_table")
data class Alarm(
    var attraction_id: Int,
    var timeAlarm: Long,
    var bEnabled:Boolean?=true,
    var title:String,
    var message:String
) {
  /*  constructor(
                    attraction_id: Int,
                    timeAlarm: Long,
                    bEnabled:Boolean?=true,
                    title:String,
                    message:String):this(attraction_id, timeAlarm,bEnabled,title,message)*/
    @PrimaryKey(autoGenerate = true)    var alarm_id: Int=0


}
