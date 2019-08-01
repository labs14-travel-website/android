package app.labs14.roamly.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.labs14.roamly.adapters.ItineraryListAdapter
import app.labs14.roamly.R
import app.labs14.roamly.models.Itinerary
import app.labs14.roamly.viewModels.ItineraryViewModel
import kotlinx.android.synthetic.main.activity_itinerary_list.*
import kotlinx.android.synthetic.main.itinerary_list.*
import kotlinx.android.synthetic.main.itinerary_list_content.*

// Basil 7/24/2019
/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [AttractionListActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItineraryListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false
    private lateinit var itineraryViewModel: ItineraryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itinerary_list)

        if (rv_itinerary_details2 != null) {
            twoPane = true
        }
         setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rv_itinerary_list.layoutManager = LinearLayoutManager(this)
        rv_itinerary_list.setHasFixedSize(true)

        var adapter = ItineraryListAdapter()

        rv_itinerary_list.adapter = adapter
        itineraryViewModel = ViewModelProviders.of(this).get(ItineraryViewModel::class.java)

        itineraryViewModel.getAllItineraries().observe(this, Observer<List<Itinerary>> {
            adapter.submitList(it)
        })

        adapter.setOnItemClickListener(object : ItineraryListAdapter.OnItemClickListener {
            override fun onItemClick(itinerary: Itinerary) {


                var intent = Intent(baseContext, AttractionListActivity::class.java)
                intent.putExtra("id",itinerary.itinerary_id)
                intent.putExtra("title", itinerary.destinationName)
/*
                val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@ItineraryListActivity, //2
                        iv_itinerary_background, // 3
                        "attraction_transition_image")*/

                startActivity(intent)
            }
        })
    }



}
