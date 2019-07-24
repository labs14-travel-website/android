package com.github.vipulasri.timelineview.sample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ActivityModel(
        val id:Long,
        val name : String,
        var location: LocationModel,
        var arrivalTime:Long,
        var departureTime:Long,
        var alarmBefore:Long,
        var alarmEnding:Long,
        var comment:String

) : Parcelable



