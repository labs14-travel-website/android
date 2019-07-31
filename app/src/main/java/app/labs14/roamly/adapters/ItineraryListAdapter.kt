package app.labs14.roamly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.labs14.roamly.R
import app.labs14.roamly.models.Itinerary
import app.labs14.roamly.models.Orientation
import app.labs14.roamly.models.TimelineAttributes
import kotlinx.android.synthetic.main.itinerary_list_content_vertical.view.*

class ItineraryListAdapter(private val mAttributes: TimelineAttributes) : androidx.recyclerview.widget.ListAdapter<Itinerary, ItineraryListAdapter.ItineraryHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Itinerary>() {
            override fun areItemsTheSame(oldItem: Itinerary, newItem: Itinerary): Boolean {
                return oldItem.itinerary_id == newItem.itinerary_id
            }

            override fun areContentsTheSame(oldItem: Itinerary, newItem: Itinerary): Boolean {
                return oldItem.itinerary_id == newItem.itinerary_id &&
                        oldItem.description == newItem.description &&
                        oldItem.destinationName == newItem.destinationName
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryHolder {


        val  layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        view = if (mAttributes.orientation == Orientation.HORIZONTAL) {
            layoutInflater.inflate(R.layout.itinerary_list_content_vertical, parent, false)
        } else {
            layoutInflater.inflate(R.layout.itinerary_list_content_vertical, parent, false)
        }
        return ItineraryHolder(view)
    }

    override fun onBindViewHolder(holder: ItineraryHolder, position: Int) {

        val currentItinerary: Itinerary = getItem(position)

      //  holder.tvDescription.text = currentItinerary.description //shoon 2019/08/01
        holder.tvDescription.text = currentItinerary.itinerary_id.toString()+" "+currentItinerary.description //shoon 2019/08/01
        holder.tvDestination.text = currentItinerary.destinationName
    }

    fun getItineraryAt(position: Int): Itinerary {
        return getItem(position)
    }

    inner class ItineraryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {

                tvDescription.setBackgroundColor(mAttributes.endLineColor) //fOR TESTING


                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var tvDestination: TextView = itemView.content
        var tvDescription: TextView = itemView.id_text

    }

    interface OnItemClickListener {
        fun onItemClick(itinerary: Itinerary)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}