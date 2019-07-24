package com.github.vipulasri.timelineview.sample.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
class AddressModel(
    var countryID:Long,
    var zipcode:String,
    var state:String,
    var city:String,
    var address:String

) : Parcelable
