package app.labs14.roamly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.labs14.roamly.R
import app.labs14.roamly.data.Constants
import app.labs14.roamly.models.Itinerary
import app.labs14.roamly.models.Orientation
import app.labs14.roamly.models.TimelineAttributes
import kotlinx.android.synthetic.main.itinerary_list_content_vertical.view.*

import java.util.*

class ItineraryListAdapter(private val mAttributes: TimelineAttributes) : androidx.recyclerview.widget.ListAdapter<Itinerary, ItineraryListAdapter.ItineraryHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Itinerary>() {
            override fun areItemsTheSame(oldItem: Itinerary, newItem: Itinerary): Boolean {
                return oldItem.itinerary_id == newItem.itinerary_id
            }

            override fun areContentsTheSame(oldItem: Itinerary, newItem: Itinerary): Boolean {
                return oldItem.itinerary_id == newItem.itinerary_id &&
                        oldItem.timeVisited == newItem.timeVisited &&
                        oldItem.destinationName == newItem.destinationName
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryHolder {

      val  layoutInflater = LayoutInflater.from(parent.context)
      val itemView: View
      itemView = if (mAttributes.orientation == Orientation.HORIZONTAL) {

          layoutInflater.inflate(R.layout.itinerary_list_content_vertical, parent, false)
      } else {
          layoutInflater.inflate(R.layout.itinerary_list_content_vertical, parent, false)
      }

      return ItineraryHolder(itemView)

    }

    override fun onBindViewHolder(holder: ItineraryHolder, position: Int) {

        val currentItinerary: Itinerary = getItem(position)


        holder.tvYearVisted.text = Constants.itineraryCardFormat.format(Date(currentItinerary.timeVisited * 1000L))

        holder.tvDestination.text = currentItinerary.destinationName

        var imageResource : Int = R.drawable.stockitinimages1

        when(position%10){
            0 -> imageResource = R.drawable.stockitinimages1
            1 -> imageResource = R.drawable.stockitinimages2
            2 -> imageResource = R.drawable.stockitinimages3
            3 -> imageResource = R.drawable.stockitinimages4
            4 -> imageResource = R.drawable.stockitinimages5
            5 -> imageResource = R.drawable.stockitinimages6
            6 -> imageResource = R.drawable.stockitinimages7
            7 -> imageResource = R.drawable.stockitinimages8
            8 -> imageResource = R.drawable.stockitinimages9
            9 -> imageResource = R.drawable.stockitinimages10
        }
        holder.ivBackgroundImage.setImageResource(imageResource)
    }

    fun getItineraryAt(position: Int): Itinerary {
        return getItem(position)
    }

    inner class ItineraryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {

             //   tvDescription.setBackgroundColor(mAttributes.endLineColor) //fOR TESTING


                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var tvDestination: TextView = itemView.tv_destination_name
        var tvYearVisted: TextView = itemView.tv_year_visited
        var ivBackgroundImage : ImageView = itemView.iv_itinerary_background

    }

    interface OnItemClickListener {
        fun onItemClick(itinerary: Itinerary)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}