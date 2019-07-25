package app.labs14.roamly.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.labs14.roamly.R
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.activity_itinerary_detail.*
import kotlinx.android.synthetic.main.item_timeline.view.*

// Basil 7/24/2019

/**
 * An activity representing a single Itinerary detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItineraryListActivity].
 */
class ItineraryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itinerary_detail)
        setSupportActionBar(detail_toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView(rv_itinerary_details)

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
        val temporaries = Array(25) { i -> "XYZ $i" }

        recyclerView.adapter = SimpleItemRecyclerViewAdapter(temporaries)
    }

    class SimpleItemRecyclerViewAdapter(
        private val values: Array<String>
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
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_timeline, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.tlDate.text = "Jul 25"
            holder.tlTitle.text = "Subtext"

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tlDate: TextView = view.text_timeline_date
            val tlTitle: TextView = view.text_timeline_title
            val tlTimeline:TimelineView = view.timeline

            init {
                tlTimeline.initLine(1)
                tlTimeline.markerSize = 52
                tlTimeline.setMarkerColor(R.color.colorPrimaryDark)
                tlTimeline.isMarkerInCenter = true
                tlTimeline.linePadding = 5

                tlTimeline.lineWidth = 5
                tlTimeline.setStartLineColor(R.color.colorAccent, 1)
                tlTimeline.setEndLineColor(R.color.colorGrey300, 1)
                tlTimeline.lineStyle = 0
                tlTimeline.lineStyleDashLength = 10
                tlTimeline.lineStyleDashGap = 5
            }
        }
    }

}
