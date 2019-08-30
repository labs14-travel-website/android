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
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import app.labs14.roamly.R
import app.labs14.roamly.models.Alarm
import app.labs14.roamly.models.Orientation

import app.labs14.roamly.models.TimelineAttributes
import app.labs14.roamly.utils.VectorDrawableUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.alarm_list_content_vertical.view.*
import kotlinx.android.synthetic.main.itinerary_list_content_vertical.view.*


import java.util.*
import kotlin.collections.ArrayList


class AlarmListAdapter(private val mAttributes: TimelineAttributes) : androidx.recyclerview.widget.ListAdapter<Alarm, AlarmListAdapter.AlarmHolder>(DIFF_CALLBACK) {


    lateinit var nextAlarm: Alarm


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Alarm>() {
            override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem.alarm_id == newItem.alarm_id
            }

            override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem.alarm_id == newItem.alarm_id &&
                        oldItem.title == newItem.title
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmHolder {

        //shoon 2019/08/01
        val  layoutInflater = LayoutInflater.from(parent.context)

        val view: View
        view = if (mAttributes.orientation == Orientation.HORIZONTAL) {
            layoutInflater.inflate(R.layout.alarm_list_content_horizontal, parent, false)
        } else {
            layoutInflater.inflate(R.layout.alarm_list_content_vertical, parent, false)
        }
        return AlarmHolder(view, viewType)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: AlarmHolder, position: Int) {

        val currentAlarm: Alarm = getItem(position)


        holder.cardColor.setCardBackgroundColor(mAttributes.bgColorRegular!!)

        holder.tvAlarmID.setTextColor(mAttributes.textColorRegular!!)
        holder.tvAttraction.setTextColor(mAttributes.textColorRegular!!)
        holder.tvAlarmTime.setTextColor(mAttributes.textColorRegular!!)
        holder.tvAlarmTitle.setTextColor(mAttributes.textColorRegular!!)
        holder.tvAlarmMessage.setTextColor(mAttributes.textColorRegular!!)


        holder.tvAlarmID.text = currentAlarm.alarm_id.toString()
        holder.tvAttraction.text = currentAlarm.attraction_id.toString()
        holder.tvAlarmTime.text = currentAlarm.timeAlarm.toString()
        holder.tvAlarmTitle.text = currentAlarm.title
        holder.tvAlarmMessage.text = currentAlarm.message


    }

    //will be useful for modifying data
    fun getAlarmAt(position: Int): Alarm {
        return getItem(position)
    }

    inner class AlarmHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        var tvAlarmID = itemView.tv_alarm_id
        var tvAttraction = itemView.tv_atracttion_id
        var tvAlarmTime = itemView.tv_timeAlarm
        var tvAlarmTitle = itemView.tv_alarm_title
        var tvAlarmMessage = itemView.tv_alarm_message
        var cardColor=itemView.cv_alarm_background
        init {

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(alarm: Alarm)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {

        this.listener = listener
    }

}