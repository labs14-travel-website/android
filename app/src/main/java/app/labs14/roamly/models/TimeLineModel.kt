package app.labs14.roamly.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Vipul Asri on 05-12-2015.
 */
@Parcelize
class TimeLineModel(
        var message: String,
        var date: String,
        var status: OrderStatus
) : Parcelable
