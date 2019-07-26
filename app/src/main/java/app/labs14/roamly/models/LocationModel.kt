package app.labs14.roamly.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize
import java.net.URL

//shoon 2019/07/26
@Entity(tableName = "location_table")
class LocationModel(
        var name: String? ="unknown",
        var address:AddressModel,
        var latlng: LatLng? =null,
        var openHours:BusinessHoursModel?=null,
        var phoneNumber:String?=null,
        var webSite:URL?=null,
        var email:String?=null

) {
        @PrimaryKey(autoGenerate = true)
        var id: Long= 0
}
