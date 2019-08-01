package app.labs14.roamly.adapters

import android.os.Build
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import app.labs14.roamly.R
import app.labs14.roamly.models.Attraction
import app.labs14.roamly.utils.VectorDrawableUtils
import kotlinx.android.synthetic.main.attraction_list_content.view.*
import java.util.*
import java.util.concurrent.TimeUnit


class AttractionListAdapter : androidx.recyclerview.widget.ListAdapter<Attraction, AttractionListAdapter.AttractionHolder>(DIFF_CALLBACK) {

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
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.attraction_list_content, parent, false)
        return AttractionHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: AttractionHolder, position: Int) {
        val currentAttraction: Attraction = getItem(position)


        holder.itemView.cv_attraction_background.setOnClickListener {
            when (it.ll_expandable.visibility) {
                View.VISIBLE -> {
                    it.ll_expandable.visibility = View.GONE
                    setMarker(holder, R.drawable.ic_marker_inactive, R.color.material_grey_500)
                    //it.cv_attraction_background.setBackgroundColor(holder.timeline.context.getColor(R.color.material_purple_300))
                    holder.cardColor.setCardBackgroundColor(holder.timeline.context.getColor(R.color.colorAttractionCardBackground))
                }
                View.GONE -> {
                    it.ll_expandable.visibility = View.VISIBLE
                    setMarker(holder, R.drawable.ic_marker_active, R.color.material_green_500)
                    //it.cv_attraction_background.setBackgroundColor(holder.timeline.context.getColor(R.color.material_blue_600))
                    holder.cardColor.setCardBackgroundColor(holder.timeline.context.getColor(R.color.material_blue_600))
                }
            }
        }

        val etaHours = ((currentAttraction.transportEta) / 3600000)
        val etaMinutes = ((currentAttraction.transportEta) %3600000)/60000
        if(etaHours == 0L){
            holder.tvTransportInfo.text = "ETA ${etaMinutes}min     ${currentAttraction.transportLabel}"
        } else{
            holder.tvTransportInfo.text = "ETA  ${etaHours}Hr  ${etaMinutes}min     ${currentAttraction.transportLabel}"
        }


        holder.itemView.cv_attraction_transport.setOnClickListener{
            //TODO: pass coordinates off to google maps for navigation
        }

        holder.timeline.initLine(0)
        holder.timelineTravel.initLine(0)
        holder.tvDescription.text = currentAttraction.description
        holder.timelineTravel.marker = holder.tvAddress.context.getDrawable(R.drawable.ic_directions_car_black_24dp)
        holder.tvAddress.text = currentAttraction.address
        holder.tvTitle.text = currentAttraction.name
        holder.tvStartTime.text = Date(currentAttraction.startTime).toString()
        holder.tvEndTime.text = Date(currentAttraction.endTime).toString()
        holder.tvPhoneNum.text = currentAttraction.phoneNum

        setMarker(holder, R.drawable.ic_marker_inactive, R.color.material_grey_500)
    }

    //will be useful for modifying data
    fun getAttractionAt(position: Int): Attraction {
        return getItem(position)
    }

    inner class AttractionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var tvStartTime = itemView.tv_attraction_start_time
        var tvEndTime = itemView.tv_attraction_end_time
        var tvTitle = itemView.tv_attraction_name
        var tvDescription = itemView.tv_attraction_description
        var tvAddress = itemView.tv_attraction_address
        var tvPhoneNum = itemView.tv_phone_num
        var timeline = itemView.timeline
        var timelineTravel = itemView.timeline2
        var cardColor = itemView.cv_attraction_background
        var tvTransportInfo = itemView.tv_transport_info

    }

    interface OnItemClickListener {
        fun onItemClick(attraction: Attraction)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    private fun setMarker(holder: AttractionHolder, drawableResId: Int, colorFilter: Int) {
        holder.timeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, drawableResId, ContextCompat.getColor(holder.itemView.context, colorFilter))
    }


}