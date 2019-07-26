package app.labs14.roamly.models

import android.os.Parcelable
import androidx.room.Entity



//shoon 2019/07/26
@Entity(tableName = "itinerary_table")
data class UserModel(
        val userID:Long,
        val name : String,
        var email: String,
        var trips: ArrayList<ItineraryModel>
)


