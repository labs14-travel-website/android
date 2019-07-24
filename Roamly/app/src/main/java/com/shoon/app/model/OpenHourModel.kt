package com.github.vipulasri.timelineview.sample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class OpenHourModel (
        val startTime:Long,
        val endTime:Long
):Parcelable