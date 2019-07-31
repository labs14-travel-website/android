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

    var itineraryId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itinerary_detail)

        var bundle: Bundle? = intent.extras
        itineraryId = bundle!!.getInt("id", 0)
        toolbar.title = bundle.getString("title","Title")

        initRecyclerView()
        //mockData()
    }

    private fun mockData(){

        attractionViewModel.insert(Attraction(4,"Alcatraz Historical Tour", 1564486657,1564496100,25,25,"Explore the prison island","37.773972", "-122.431297", "California", "(611) 934-0394",4,1564485950,"Bicycle"))
        attractionViewModel.insert(Attraction(4,"Golden Gate Ruins", 1564486657,1564496100,25,25,"Experience ancient times","37.773972", "-122.431297", "California", "(985) 744-4351",4,1564485950,"Bicycle"))

        attractionViewModel.insert(Attraction(5,"Temple Hopping", 1564486657,1564496100,25,25,"See the exquisitely preserved temples","13.736717", "100.523186", "Thailand", "(477) 697-6290",5,1564485950,"Tuk-tuk"))
        attractionViewModel.insert(Attraction(5,"Yoga Teacher Training", 1564486657,1564496100,25,25,"Join an advanced yoga seminar","13.736717", "100.523186", "Thailand", "(515) 696-1405",5,1564485950,"Tuk-tuk"))
        attractionViewModel.insert(Attraction(5,"Tiger Petting", 1564486657,1564496100,25,25,"Which is the furriest?","13.736717", "100.523186", "Thailand", "(321) 633-3550",5,1564485950,"Tuk-tuk"))

        attractionViewModel.insert(Attraction(6,"Olive Treks", 1564486657,1564496100,25,25,"Visit the olive groves","36.862499", "10.195556", "Tunisia, Africa", "(573) 602-8514",6,1564485950,"Ship"))
        attractionViewModel.insert(Attraction(6,"Camel Racing", 1564486657,1564496100,25,25,"Who says camels can't run?","36.862499", "10.195556", "Tunisia, Africa", "(468) 933-2574",6,1564485950,"Ship"))
        attractionViewModel.insert(Attraction(6,"Soldier Training", 1564486657,1564496100,25,25,"Become a soldier for a day","36.862499", "10.195556", "Tunisia, Africa", "(558) 353-3320",6,1564485950,"Ship"))

        attractionViewModel.insert(Attraction(7,"Fruit Markets", 1564486657,1564496100,25,25,"Pick out some of the juiciest fruit","7.946527", "-1.023194", "Ghana, Africa", "(480) 378-0288",1,1564485950,"Airplane"))
        attractionViewModel.insert(Attraction(7,"Rave Party", 1564486657,1564496100,25,25,"The sounds of the people","7.946527", "-1.023194", "Ghana, Africa", "(689) 880-4165",1,1564485950,"Airplane"))
        attractionViewModel.insert(Attraction(7,"Hammock Massage", 1564486657,1564496100,25,25,"Get massaged by the beach on a hammock","7.946527", "-1.023194", "Ghana, Africa", "(672) 909-9102",1,1564485950,   "Airplane"))

        attractionViewModel.insert(Attraction(8,"Igloo Construction", 1564486657,1564496100,25,25,"Build an ice shelter","-75.2509766", "-0.071389", "Antarctica", "(957) 825-3071",6,1564485950,"Ship"))
        attractionViewModel.insert(Attraction(8,"Research and Measuring", 1564486657,1564496100,25,25,"Volunteer to gather data","-75.2509766", "-0.071389", "Antarctica", "(272) 859-5475",6,1564485950,"Ship"))
        attractionViewModel.insert(Attraction(8,"Snowshoeing", 1564486657,1564496100,25,25,"Hike over the vast snowy desert","-75.2509766", "-0.071389", "Antarctica", "(402) 986-9213",6,1564485950,"Ship"))

        attractionViewModel.insert(Attraction(9,"Wildlife Photography", 1564486657,1564496100,25,25,"Witness breathtaking scenery","48", "74", "Patagonia, Chile", "(734) 894-9467",3,1564485950,"Train"))
        attractionViewModel.insert(Attraction(9,"Horseback Riding", 1564486657,1564496100,25,25,"Saddle up!","48", "74", "Patagonia, Chile", "(429) 858-7774",3,1564485950,"Train"))
        attractionViewModel.insert(Attraction(9,"Paragliding", 1564486657,1564496100,25,25,"Fly through the clouds","48", "74", "Patagonia, Chile", "(813) 287-9376",3,1564485950,"Train"))

        attractionViewModel.insert(Attraction(10,"DJ Jams and Rap Battle", 1564486657,1564496100,25,25,"Get pumped with great impromptu music","40.650002", "-73.949997", "Brooklyn, New York", "(702) 305-6673",7,1564485950,"Car"))
        attractionViewModel.insert(Attraction(10,"Circuit of the Arts", 1564486657,1564496100,25,25,"Local artists showing","40.650002", "-73.949997", "Brooklyn, New York", "(400) 263-7329",7,1564485950,"Car"))
        attractionViewModel.insert(Attraction(10,"Outdoor Basketball Game", 1564486657,1564496100,25,25,"Watch amazing feats of skill","40.650002", "-73.949997", "Brooklyn, New York", "(872) 886-7323",7,1564485950,"Car"))
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