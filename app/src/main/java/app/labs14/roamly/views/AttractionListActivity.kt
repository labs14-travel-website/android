package app.labs14.roamly.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.labs14.roamly.R
import app.labs14.roamly.adapters.AttractionListAdapter
import app.labs14.roamly.models.*
import app.labs14.roamly.viewModels.AttractionViewModel
import kotlinx.android.synthetic.main.activity_attraction_list.*

// Basil 7/24/2019

class AttractionListActivity : AppCompatActivity() {

    private lateinit var attractionViewModel: AttractionViewModel

    var itineraryId:Int = 0
    private lateinit var mAttributes: TimelineAttributes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_list)

        var bundle: Bundle? = intent.extras
        itineraryId = bundle!!.getInt("id", 0)

        var title : TextView = tv_attraction_title
        title.text = bundle.getString("title","Title")

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv_attraction_list.layoutManager = LinearLayoutManager(this)
        rv_attraction_list.setHasFixedSize(true)

        var adapter = AttractionListAdapter(mAttributes)

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