package com.github.vipulasri.timelineview.sample.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize
import java.net.URL

@Parcelize
class LocationModel(
        val id:Long?=0, //reserved for unknown
        var name: String? ="unknown",
        var address:AddressModel,
        var latlng: LatLng? =null,
        var openHours:BusinessHoursModel?=null,
        var phoneNumber:String?=null,
        var webSite:URL?=null,
        var email:String?=null

) : Parcelable{


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (address != other.address) return false
        if (latlng != other.latlng) return false
        if (openHours != other.openHours) return false
        if (phoneNumber != other.phoneNumber) return false
        if (webSite != other.webSite) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + address.hashCode()
        result = 31 * result + (latlng?.hashCode() ?: 0)
        result = 31 * result + (openHours?.hashCode() ?: 0)
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + (webSite?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "LocationModel(id=$id, name=$name, address=$address, latlng=$latlng, openHours=$openHours, phoneNumber=$phoneNumber, webSite=$webSite, email=$email)"
    }
}