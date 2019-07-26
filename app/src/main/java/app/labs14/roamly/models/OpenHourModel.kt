package app.labs14.roamly.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//shoon 2019/07/26
@Entity(tableName = "openhour_table")
class OpenHourModel (
        val startTime:Long,
        val endTime:Long
) {
        @PrimaryKey(autoGenerate = true)
        var id: Long= 0
}
