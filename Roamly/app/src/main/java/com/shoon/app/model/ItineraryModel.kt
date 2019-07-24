package com.github.vipulasri.timelineview.sample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class ItineraryModel(
    val id: Long?=0,
    var description:String?="Nothing paticular",
    var location: LocationModel?=null,
    var activities: ActivityModel?=null
) : Parcelable