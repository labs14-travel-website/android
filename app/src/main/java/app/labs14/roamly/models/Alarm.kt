package app.labs14.roamly.models



import androidx.room.*

//Shoon 2019/08/29

@Entity(tableName = "alarm_table")
class Alarm(
    @PrimaryKey(autoGenerate = true)
    var alarm_id: Int,
    var atraction_id: Int,
    var timeAlarm: Long
) {



}
