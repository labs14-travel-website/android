package app.labs14.roamly.views


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import app.labs14.roamly.R
import app.labs14.roamly.adapters.AlarmListAdapter
import app.labs14.roamly.adapters.AttractionListAdapter
import app.labs14.roamly.models.*
import app.labs14.roamly.viewModels.AlarmViewModel

import app.labs14.roamly.viewModels.AttractionViewModel

import kotlinx.android.synthetic.main.activity_attraction_list.*
import kotlinx.android.synthetic.main.activity_attraction_list.rv_attraction_list



// Basil 7/24/2019

class AlarmListActivity : AppCompatActivity() {

    private lateinit var attractionViewModel: AttractionViewModel
    private lateinit var alarmViewModel: AlarmViewModel
    private lateinit var mAttributes: TimelineAttributes
    var itineraryId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_list)

        var bundle: Bundle? = intent.extras

        itineraryId = bundle!!.getInt("id", 0)
        var title : TextView = tv_attraction_title
        title.text = bundle!!.getString("title","Title")
        mAttributes = bundle!!.getParcelable("attributes")

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv_attraction_list.layoutManager = LinearLayoutManager(this)
        rv_attraction_list.setHasFixedSize(true)

        var adapter = AlarmListAdapter(mAttributes)

        rv_attraction_list.adapter = adapter
        alarmViewModel= ViewModelProviders.of(this).get(AlarmViewModel::class.java)
        alarmViewModel.getAllAlarm().observe(this, Observer<List<Alarm>> {
            adapter.submitList(it)
        })

        adapter.setOnItemClickListener(object : AlarmListAdapter.OnItemClickListener {
            override fun onItemClick(alarm: Alarm) {
            }
        })
    }
}