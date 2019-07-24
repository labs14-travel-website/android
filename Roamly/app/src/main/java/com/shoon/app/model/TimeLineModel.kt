package app.labs14.roamly.model

import android.os.Parcelable
import com.github.vipulasri.timelineview.sample.model.ItineraryModel
import kotlinx.android.parcel.Parcelize

/**
 * Created by Vipul Asri on 05-12-2015.
 */
@Parcelize
class TimeLineModel(
    var message: String,
    var date: String,
    var status: OrderStatus,
    var itinerary: ItineraryModel
) : Parcelable
