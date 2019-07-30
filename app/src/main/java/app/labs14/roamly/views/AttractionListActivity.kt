package app.labs14.roamly.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.labs14.roamly.R
import app.labs14.roamly.adapters.AttractionListAdapter
import app.labs14.roamly.models.*
import app.labs14.roamly.utils.VectorDrawableUtils
import app.labs14.roamly.viewModels.AttractionViewModel
import app.labs14.roamly.viewModels.ItineraryViewModel
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.attraction_list_content.view.*
import kotlinx.android.synthetic.main.itinerary_detail.*
import java.util.ArrayList

// Basil 7/24/2019

class AttractionListActivity : AppCompatActivity() {

    private lateinit var attractionViewModel: AttractionViewModel

    var itineraryId:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itinerary_detail)

        var bundle: Bundle? = intent.extras
        itineraryId = bundle!!.getLong("id")
        toolbar.title = bundle!!.getString("title")

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv_attraction_list.layoutManager = LinearLayoutManager(this)
        rv_attraction_list.setHasFixedSize(true)

        var adapter = AttractionListAdapter()

        rv_attraction_list.adapter = adapter
        attractionViewModel = ViewModelProviders.of(this).get(AttractionViewModel::class.java)

        attractionViewModel.getAttractionById(itineraryId).observe(this, Observer<List<Attraction>> {
            adapter.submitList(it)
        })

        adapter.setOnItemClickListener(object : AttractionListAdapter.OnItemClickListener {
            override fun onItemClick(attraction: Attraction) {
            }
        })
    }


    class SimpleItemRecyclerViewAdapter(
        private val values: ArrayList<TimeLineModel>,
        private val attribs: TimelineAttributes
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag
                /*if (twoPane) {
                    val fragment = ItineraryDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItineraryDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.itinerary_detail_container, fragment)
                        .commit()
                } else {*/
                Log.i("EXPAND", " Item $v clicked")
                val intent = Intent(v.context, AttractionListActivity::class.java).apply {
                    putExtra("item_id", item.toString())
                }
                v.context.startActivity(intent)
                /*}*/
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            /*val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.attraction_list_content, parent, false)*/

            val layoutInflater = LayoutInflater.from(parent.context)
            val view: View

            view = if (attribs.orientation == Orientation.HORIZONTAL) {
                layoutInflater.inflate(R.layout.item_timeline_horizontal, parent, false)
            } else {
                layoutInflater.inflate(R.layout.attraction_list_content, parent, false)
            }

            return ViewHolder(view, viewType)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]

            /*holder.tlDate.text = item.date
            holder.tlTitle.text = item.message
            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }*/


            when {
                item.status == OrderStatus.INACTIVE -> {
                    holder.tlTimeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, R.drawable.ic_marker_inactive, attribs.markerColor)
                }
                item.status == OrderStatus.ACTIVE -> {
                    holder.tlTimeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, R.drawable.ic_marker_active, attribs.markerColor)
                }
                else -> {
                    holder.tlTimeline.setMarker(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_marker), attribs.markerColor)
                }
            }

            /*if (item.date.isNotEmpty()) {
                holder.tlDate.visibility = View.VISIBLE
                holder.tlDate.text = DateTimeUtils.parseDateTime(item.date, "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
            } else
                holder.tlDate.visibility = View.GONE
            holder.tlTitle.text = item.message*/

        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View, viewType: Int) : RecyclerView.ViewHolder(view) {


            //internal var expandableListView: ExpandableListView? = view.findViewById(R.id.expandableListView)
            internal var adapter: ExpandableListAdapter? = null
            internal var titleList: List<String>? = null

            val data: HashMap<String, List<String>>
                get() {
                    val listData = HashMap<String, List<String>>()

                    val starting = ArrayList<String>()
                    starting.add("San Fransisco, CA")
                    starting.add("Personal Car")

                    val museum = ArrayList<String>()
                    museum.add("Golden Gate Ruins")
                    museum.add("142 Golden Gate Way")
                    museum.add("9:30 am")

                    val lunch = ArrayList<String>()
                    lunch.add("Joni's Slop Shop")
                    lunch.add("5543 Main Street")
                    lunch.add("12:00 pm")

                    val swimming = ArrayList<String>()
                    swimming.add("Beach, Atlantic Ocean")
                    swimming.add("Anywhere along coastal route 1")
                    swimming.add("2:30 pm")

                    listData["Starting"] = starting
                    listData["Museum"] = museum
                    listData["Lunch"] = lunch
                    listData["Swimming"] = swimming

                    return listData
                }
/*


            init {

                if (expandableListView != null) {
                    val listData = data
                    titleList = ArrayList(listData.keys)
                    adapter = CustomExpandableListAdapter(view.context, titleList as ArrayList<String>, listData)
                    expandableListView!!.setAdapter(adapter)

                    expandableListView!!.setOnGroupExpandListener { groupPosition ->
                        Toast.makeText(view.context, (titleList as ArrayList<String>)[groupPosition] + " List Expanded.", Toast.LENGTH_SHORT).show()
                    }

                    expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                        Toast.makeText(view.context, (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.", Toast.LENGTH_SHORT).show()
                    }

                    expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                        Toast.makeText(view.context, "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition), Toast.LENGTH_SHORT).show()
                        false
                    }
                }
            }
*/

            /*val tlDate: TextView = view.text_timeline_date
            val tlTitle: TextView = view.text_timeline_title*/
            val tlTimeline: TimelineView = view.timeline

            init {
                tlTimeline.initLine(viewType)
                tlTimeline.markerSize = attribs.markerSize
                tlTimeline.setMarkerColor(attribs.markerColor)
                tlTimeline.isMarkerInCenter = attribs.markerInCenter
                tlTimeline.linePadding = attribs.linePadding
                tlTimeline.lineWidth = attribs.lineWidth
                tlTimeline.setStartLineColor(attribs.startLineColor, viewType)
                tlTimeline.setEndLineColor(attribs.endLineColor, viewType)
                tlTimeline.lineStyle = attribs.lineStyle
                tlTimeline.lineStyleDashLength = attribs.lineDashWidth
                tlTimeline.lineStyleDashGap = attribs.lineDashGap
            }
        }
    }

}