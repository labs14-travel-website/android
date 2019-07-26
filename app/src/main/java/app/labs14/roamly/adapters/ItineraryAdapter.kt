package app.labs14.roamly.adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.labs14.roamly.R
import app.labs14.roamly.models.ItineraryModel
import kotlinx.android.synthetic.main.note_item.view.*

//shoon 2019/07/26
class ItineraryAdapter : ListAdapter<ItineraryModel, ItineraryAdapter.ItineraryHolder>(DIFF_CALLBACK) {


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItineraryModel>() {
            override fun areItemsTheSame(oldItem: ItineraryModel, newItem: ItineraryModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItineraryModel, newItem: ItineraryModel): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ItineraryHolder(itemView)
    }


    override fun onBindViewHolder(holder: ItineraryAdapter.ItineraryHolder, position: Int) {
        val currentItinerary: ItineraryModel = getItem(position)

        holder.textViewTitle.text = currentItinerary.title
        holder.textViewPriority.text = currentItinerary.priority.toString()
        holder.textViewDescription.text = currentItinerary.description
        //shoon 2019/07/26
        val spacer="                                "
        holder.textViewDescription.text=spacer+"▼"
        holder.textViewDescription.setOnClickListener(){
                it->
            if(holder.textViewDescription.text==spacer+"▼") {
                holder.textViewDescription.text=currentItinerary.title+"\n"+currentItinerary.startTime+"\n"+currentItinerary.endTime+"\n"+currentItinerary.priority+"\n"+spacer+"▲"
            }else{
                holder.textViewDescription.text=spacer+"▼"
            }
        }
        //
    }

    fun getItinearyAt(position: Int): ItineraryModel {
        return getItem(position)
    }

    inner class ItineraryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_title
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_description
    }

    interface OnItemClickListener {
        fun onItemClick(itinerary: ItineraryModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
