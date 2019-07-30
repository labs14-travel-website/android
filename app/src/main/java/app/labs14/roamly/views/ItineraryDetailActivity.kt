package app.labs14.roamly.views

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.labs14.roamly.R
import app.labs14.roamly.models.*
import app.labs14.roamly.utils.DateTimeUtils
import app.labs14.roamly.utils.Utils
import app.labs14.roamly.utils.VectorDrawableUtils
import app.labs14.roamly.viewmodels.ItineraryViewModel
import app.labs14.roamly.views.viewmodels.NoteViewModel
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.activity_itinerary_detail.*
import kotlinx.android.synthetic.main.item_timeline.view.*
import java.util.ArrayList
import androidx.lifecycle.Observer//shoon 2019/07/26
import androidx.recyclerview.widget.ItemTouchHelper
import app.labs14.roamly.adapters.ItineraryAdapter
import app.labs14.roamly.adapters.NoteAdapter
import kotlinx.android.synthetic.main.activity_note.*

// Basil 7/24/2019

/**
 * An activity representing a single Itinerary detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItineraryListActivity].
 */
class ItineraryDetailActivity : AppCompatActivity() {
    //shoon 2019/07/26
    companion object {
        const val ADD_ITINERARY_REQUEST = 1
        const val EDIT_ITINERARY_REQUEST = 2
    }

    private lateinit var itineraryViewModel: ItineraryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
 //       setContentView(R.layout.activity_itinerary_detail)
 //       setSupportActionBar(detail_toolbar)
        setContentView(R.layout.activity_timeline)

//shoon 2019/07/26
        // I made this section to show how to seperate adapter and connecting data
        //with using note recycler view. So please reconnect to Basil's recycler view

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(false)
        var adapter = ItineraryAdapter()
        recycler_view.adapter = adapter

        itineraryViewModel = ViewModelProviders.of(this).get(ItineraryViewModel::class.java)

        itineraryViewModel.getAllItineraries().observe(this, Observer<List<ItineraryModel>> {
            adapter.submitList(it)
        })
//////////////////////////////////////////////////////////////////////





        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    //    setupRecyclerView(rv_itinerary_details)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        /*if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = ItineraryDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        ItineraryDetailFragment.ARG_ITEM_ID,
                        intent.getStringExtra(ItineraryDetailFragment.ARG_ITEM_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.itinerary_detail_container, fragment)
                .commit()
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                navigateUpTo(Intent(this, ItineraryListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    private fun setupRecyclerView(recyclerView: RecyclerView) {
        //val temporaries = Array(25) { i -> "XYZ $i" }

        //default values
        var mAttributes: TimelineAttributes = TimelineAttributes(
            markerSize = Utils.dpToPx(20f, this),
            markerColor = ContextCompat.getColor(this, R.color.material_grey_500),
            markerInCenter = true,
            linePadding = Utils.dpToPx(2f, this),
            startLineColor = ContextCompat.getColor(this, R.color.colorAccent),
            endLineColor = ContextCompat.getColor(this, R.color.colorAccent),
            lineStyle = TimelineView.LineStyle.NORMAL,
            lineWidth = Utils.dpToPx(2f, this),
            lineDashWidth = Utils.dpToPx(4f, this),
            lineDashGap = Utils.dpToPx(2f, this)
        )

        val mDataList = ArrayList<TimeLineModel>()
        mDataList.add(TimeLineModel("Item successfully delivered", "", OrderStatus.INACTIVE))
        mDataList.add(TimeLineModel("Courier is out to deliver your order", "2017-02-12 08:00", OrderStatus.ACTIVE))
        mDataList.add(TimeLineModel("Item has reached courier facility at New Delhi", "2017-02-11 21:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Item has been given to the courier", "2017-02-11 18:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Item is packed and will dispatch soon", "2017-02-11 09:30", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Order is being readied for dispatch", "2017-02-11 08:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Order processing initiated", "2017-02-10 15:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Order confirmed by seller", "2017-02-10 14:30", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Order placed successfully", "2017-02-10 14:00", OrderStatus.COMPLETED))


        //recyclerView.adapter = SimpleItemRecyclerViewAdapter(mDataList, mAttributes)


        val mLayoutManager = if (mAttributes.orientation == Orientation.HORIZONTAL) {
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        } else {
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        recyclerView.layoutManager = mLayoutManager

        recyclerView.adapter = SimpleItemRecyclerViewAdapter(mDataList, mAttributes)
    }

    class SimpleItemRecyclerViewAdapter(
        private val values: ArrayList<TimeLineModel>,
        private val attribs: TimelineAttributes
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as String
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
                val intent = Intent(v.context, ItineraryDetailActivity::class.java).apply {
                    putExtra("item_id", item)
                }
                v.context.startActivity(intent)
                /*}*/
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            /*val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_timeline, parent, false)*/

            val  layoutInflater = LayoutInflater.from(parent.context)
            val view: View

            view = if (attribs.orientation == Orientation.HORIZONTAL) {
                layoutInflater.inflate(R.layout.item_timeline_horizontal, parent, false)
            } else {
                layoutInflater.inflate(R.layout.item_timeline, parent, false)
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
                    holder.tlTimeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, R.drawable.ic_marker_active,  attribs.markerColor)
                }
                else -> {
                    holder.tlTimeline.setMarker(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_marker), attribs.markerColor)
                }
            }

            if (item.date.isNotEmpty()) {
                holder.tlDate.visibility = View.VISIBLE
                holder.tlDate.text = DateTimeUtils.parseDateTime(item.date, "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
            } else
                holder.tlDate.visibility = View.GONE

            holder.tlTitle.text = item.message
            //shoon 2019/07/26
            val spacer="                                "
            holder.tlContent.text=spacer+"▼"
            holder.tlContent.setOnClickListener(){
                    it->
                if(holder.tlContent.text==spacer+"▼") {
                    holder.tlContent.text=item.message+"\n"+item.message+"\n"+item.message+"\n"+item.message+"\n"+spacer+"▲"
                }else{
                    holder.tlContent.text=spacer+"▼"
                }
            }
            //

        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View, viewType: Int) : RecyclerView.ViewHolder(view) {
            val tlDate: TextView = view.text_timeline_date
            val tlTitle: TextView = view.text_timeline_title
            val tlContent: TextView = view.text_timeline_content
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
