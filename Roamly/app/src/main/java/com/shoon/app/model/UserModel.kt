package com.github.vipulasri.timelineview.sample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class UserModel(
        val userID:Long,
        val name : String,
        var email: String,
        var trips:ArrayList<ItineraryModel>
): Parcelable


