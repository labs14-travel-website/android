package app.labs14.roamly.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.labs14.roamly.R
import app.labs14.roamly.adapters.AttractionAdapter
import app.labs14.roamly.adapters.CustomExpandableListAdapter

import app.labs14.roamly.adapters.ItineraryListAdapter
import app.labs14.roamly.models.*
import app.labs14.roamly.utils.Utils
import app.labs14.roamly.utils.VectorDrawableUtils
import app.labs14.roamly.viewModels.AttractionViewModel
import app.labs14.roamly.viewModels.ItineraryViewModel
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.activity_itinerary_detail.*
import kotlinx.android.synthetic.main.activity_timeline.*
import kotlinx.android.synthetic.main.item_timeline.view.*
import java.util.ArrayList

// Basil 7/24/2019

/**
 * An activity representing a single Itinerary detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItineraryListActivity].
 */
class ItineraryDetailActivity : AppCompatActivity() {

    private lateinit var itineraryViewModel: ItineraryViewModel
    private lateinit var attractionViewModel: AttractionViewModel
    var itineraryId:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        setSupportActionBar(detail_toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var bundle: Bundle? = intent.extras
        itineraryId = bundle!!.getLong("id")

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(false)
        var adapter = AttractionAdapter()
        recycler_view.adapter = adapter



        attractionViewModel = ViewModelProviders.of(this).get(AttractionViewModel::class.java)
        attractionViewModel.getAttractionById(itineraryId).observe(this, Observer<List<Attraction>> {
            adapter.submitList(it)
        })
 //       setupRecyclerView(rv_itinerary_details)

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



}