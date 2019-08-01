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
}