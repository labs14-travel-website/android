package com.github.vipulasri.timelineview.sample.model

import android.icu.util.TimeZone
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable