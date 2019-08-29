package app.labs14.roamly.adapters

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import app.labs14.roamly.R
import app.labs14.roamly.models.Alarm
import app.labs14.roamly.models.Attraction
import app.labs14.roamly.models.Orientation

import app.labs14.roamly.models.TimelineAttributes
import app.labs14.roamly.utils.VectorDrawableUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.attraction_list_content_vertical.view.*
import kotlinx.android.synthetic.main.itinerary_list_content_vertical.view.*


import java.util.*
import kotlin.collections.ArrayList


class AttractionListAdapter(private val mAttributes: TimelineAttributes) : androidx.recyclerview.widget.ListAdapter<Attraction, AttractionListAdapter.AttractionHolder>(DIFF_CALLBACK) {
    lateinit var nextAttraction: Attraction


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Attraction>() {
            override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
                return oldItem.attraction_id == newItem.attraction_id
            }

            override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
                return oldItem.attraction_id == newItem.attraction_id &&
                        oldItem.description == newItem.description
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionHolder {

        //shoon 2019/08/01
        val  layoutInflater = LayoutInflater.from(parent.context)

        val view: View
        view = if (mAttributes.orientation == Orientation.HORIZONTAL) {
            layoutInflater.inflate(R.layout.attraction_list_content_horizontal, parent, false)
        } else {
            layoutInflater.inflate(R.layout.attraction_list_content_vertical, parent, false)
        }
        return AttractionHolder(view, viewType)
    }
    var queueAlarm=ArrayList<Alarm>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: AttractionHolder, position: Int) {
        val currentAttraction: Attraction = getItem(position)
        var context = holder.tvAddress.context


        holder.itemView.tv_transport_info.setOnClickListener{

            var gmmIntentUri = Uri.parse("geo:${getAttractionAt(position).lat}, ${getAttractionAt(position).lng}")
            var mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        }
        setMarker(holder, R.drawable.ic_marker_inactive, mAttributes.markerColor)
        holder.cardColor.setCardBackgroundColor(mAttributes.bgColorRegular!!)

        holder.tvAddress.setTextColor(mAttributes.textColorRegular!!)
        holder.tvDescription.setTextColor(mAttributes.textColorRegular!!)
        holder.tvPhoneNum.setTextColor(mAttributes.textColorRegular!!)
        holder.tvStartTime.setTextColor(mAttributes.textColorRegular!!)
        holder.tvTitle.setTextColor(mAttributes.textColorRegular!!)
        holder.tvTransportInfo.setTextColor(mAttributes.textColorRegular!!)
        holder.tvEndTime.setTextColor(mAttributes.textColorRegular!!)
        holder.itemView.cv_attraction_background.setOnClickListener {
            when (it.ll_expandable.visibility) {
                View.VISIBLE -> {
                    it.ll_expandable.visibility = View.GONE
                    setMarker(holder, R.drawable.ic_marker_inactive, mAttributes.markerColor)
                    holder.cardColor.setCardBackgroundColor(mAttributes.bgColorRegular!!)
                    holder.tvStartTime.setTextColor(mAttributes.textColorRegular!!)
                    holder.tvTitle.setTextColor(mAttributes.textColorRegular!!)
                    holder.tvEndTime.setTextColor(mAttributes.textColorRegular!!)
                    holder.cardColor.setCardBackgroundColor(mAttributes.bgColorRegular!!)
                }
                View.GONE -> {
                    it.ll_expandable.visibility = View.VISIBLE
                    setMarker(holder, R.drawable.ic_marker_active, mAttributes.markerColor)
                    holder.tvAddress.setTextColor(mAttributes.textColorExpanded!!)
                    holder.tvDescription.setTextColor(mAttributes.textColorExpanded!!)
                    holder.tvPhoneNum.setTextColor(mAttributes.textColorExpanded!!)
                    holder.tvStartTime.setTextColor(mAttributes.textColorExpanded!!)
                    holder.tvTitle.setTextColor(mAttributes.textColorExpanded!!+5)
                    holder.tvTransportInfo.setTextColor(mAttributes.textColorExpanded!!)
                    holder.tvEndTime.setTextColor(mAttributes.textColorExpanded!!)
                    holder.cardColor.setCardBackgroundColor(mAttributes.bgColorExpanded!!)
                }
            }
        }

        currentAttraction.startTime
        currentAttraction.endTime

        holder.cvTransport.setBackgroundColor(mAttributes.bgColorRegular!!)
        if (position <= itemCount - 2) {
            if(position == 0){
                holder.timeline.setStartLineColor(holder.tvAddress.context.resources.getColor(R.color.colorBlack), 0)
            } else{
            }

            nextAttraction = getAttractionAt(position)
            currentAttraction.transportEta = position * 3600000L
            holder.timelineTravel.marker = holder.tvAddress.context.getDrawable(R.drawable.ic_directions_car_black_24dp)
            holder.itemView.cv_attraction_transport.visibility = View.VISIBLE
            holder.timeline.initLine(0)
        } else {
            holder.itemView.cv_attraction_transport.visibility = View.GONE
            //TODO: Change this to invisible
            holder.timelineTravel.setMarker(holder.tvAddress.context.getDrawable(R.drawable.ic_marker_inactive), holder.tvAddress.context.getColor(R.color.colorWhite))
            holder.timeline.setEndLineColor(holder.tvAddress.context.resources.getColor(R.color.colorBlack), 0)
        }

        val etaHours = ((currentAttraction.transportEta) / 3600000)
        val etaMinutes = ((currentAttraction.transportEta) % 3600000) / 60000
        if (etaHours == 0L) {
            holder.tvTransportInfo.text = "ETA ${etaMinutes}min     ${currentAttraction.transportLabel}"
        } else {
            holder.tvTransportInfo.text = "ETA  ${etaHours}Hr  ${etaMinutes}min     ${currentAttraction.transportLabel}"
        }

        holder.itemView.cv_attraction_transport.setOnClickListener {
            //TODO: pass coordinates off to google maps for navigation
        }

        holder.timeline.initLine(0)
        holder.timelineTravel.initLine(0)
        holder.tvDescription.text = currentAttraction.description
        holder.tvAddress.text = currentAttraction.address
        holder.tvTitle.text = currentAttraction.name
        holder.tvStartTime.text = Date(currentAttraction.startTime).toString()
        holder.tvEndTime.text = Date(currentAttraction.endTime).toString()
        holder.tvPhoneNum.text = currentAttraction.phoneNum

        setMarker(holder, R.drawable.ic_marker_inactive, mAttributes.markerColor)
    }

    //will be useful for modifying data
    fun getAttractionAt(position: Int): Attraction {
        return getItem(position)
    }

    inner class AttractionHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var tvStartTime = itemView.tv_attraction_start_time
        var tvEndTime = itemView.tv_attraction_end_time
        var tvTitle = itemView.tv_attraction_name
        var tvDescription = itemView.tv_attraction_description
        var tvAddress = itemView.tv_attraction_address
        var tvPhoneNum = itemView.tv_phone_num
        var timelineTravel = itemView.timeline2
        var cardColor = itemView.cv_attraction_background
        var cvTransport = itemView.cv_attraction_transport
        var tvTransportInfo = itemView.tv_transport_info
        val timeline = itemView.timeline
        init {
            timeline.initLine(viewType)
            timeline.markerSize = mAttributes.markerSize
            timeline.setMarkerColor(mAttributes.markerColor)
            timeline.isMarkerInCenter = mAttributes.markerInCenter
            timeline.linePadding = mAttributes.linePadding

            timeline.lineWidth = mAttributes.lineWidth
            timeline.setStartLineColor(mAttributes.startLineColor, viewType)
            timeline.setEndLineColor(mAttributes.endLineColor, viewType)
            timeline.lineStyle = mAttributes.lineStyle
            timeline.lineStyleDashLength = mAttributes.lineDashWidth
            timeline.lineStyleDashGap = mAttributes.lineDashGap


            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(attraction: Attraction)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {

        this.listener = listener
    }

    private fun setMarker(holder: AttractionHolder, drawableResId: Int, colorFilter: Int) {
        holder.timeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, drawableResId, colorFilter)
    }
}