package app.labs14.roamly.models

import android.icu.util.TimeZone
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//shoon 2019/07/26
@Entity(tableName = "businesshours_table")
class BusinessHoursModel(
        var timezone: TimeZone?=null,
        var period:Period,
        var monday:OpenHourModel,
        var tuesday:OpenHourModel,
        var wednesday:OpenHourModel,
        var thursday:OpenHourModel,
        var friday:OpenHourModel,
        var saturday:OpenHourModel,
        var sunday:OpenHourModel,
        var onetime:OpenHourModel,
        var everyday:OpenHourModel
) {
        @PrimaryKey(autoGenerate = true)
        var id: Long= 0
}
