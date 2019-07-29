package app.labs14.roamly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.labs14.roamly.models.Note
import app.labs14.roamly.R

import kotlinx.android.synthetic.main.note_item.view.*

//shoon 2019/07/26
class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteHolder>(DIFF_CALLBACK) {


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote: Note = getItem(position)

        holder.textViewTitle.text = currentNote.title
        holder.textViewPriority.text = currentNote.priority.toString()
        holder.textViewDescription.text = currentNote.description
    }

    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var expandableListView: ExpandableListView = itemView.findViewById(R.id.expandableListView2)
        internal var adapter: ExpandableListAdapter? = null
        internal var titleList: List<String> ? = null
        val data: HashMap<String, List<String>>
            get() {
                val listData = HashMap<String, List<String>>()

                val redmiMobiles = ArrayList<String>()
                redmiMobiles.add("Redmi Y2")
                redmiMobiles.add("Redmi S2")
                redmiMobiles.add("Redmi Note 5 Pro")
                redmiMobiles.add("Redmi Note 5")
                redmiMobiles.add("Redmi 5 Plus")
                redmiMobiles.add("Redmi Y1")
                redmiMobiles.add("Redmi 3S Plus")

                val micromaxMobiles = ArrayList<String>()
                micromaxMobiles.add("Micromax Bharat Go")
                micromaxMobiles.add("Micromax Bharat 5 Pro")
                micromaxMobiles.add("Micromax Bharat 5")
                micromaxMobiles.add("Micromax Canvas 1")
                micromaxMobiles.add("Micromax Dual 5")

                val appleMobiles = ArrayList<String>()
                appleMobiles.add("iPhone 8")
                appleMobiles.add("iPhone 8 Plus")
                appleMobiles.add("iPhone X")
                appleMobiles.add("iPhone 7 Plus")
                appleMobiles.add("iPhone 7")
                appleMobiles.add("iPhone 6 Plus")

                val samsungMobiles = ArrayList<String>()
                samsungMobiles.add("Samsung Galaxy S9+")
                samsungMobiles.add("Samsung Galaxy Note 7")
                samsungMobiles.add("Samsung Galaxy Note 5 Dual")
                samsungMobiles.add("Samsung Galaxy S8")
                samsungMobiles.add("Samsung Galaxy A8")
                samsungMobiles.add("Samsung Galaxy Note 4")

                listData["Redmi"] = redmiMobiles
                listData["Micromax"] = micromaxMobiles
                listData["Apple"] = appleMobiles
                listData["Samsung"] = samsungMobiles

                return listData
            }
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
            if (expandableListView != null) {
                val listData = data
                titleList = ArrayList(listData.keys)
                adapter = CustomExpandableListAdapter(itemView.context, titleList as ArrayList<String>, listData)
                expandableListView!!.setAdapter(adapter)

                expandableListView!!.setOnGroupExpandListener {

                }

                expandableListView!!.setOnGroupCollapseListener { groupPosition ->

                }

                expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->

                    false
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_title
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewDescription: TextView = itemView.text_view_description








    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
