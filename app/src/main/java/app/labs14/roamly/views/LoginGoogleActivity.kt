package app.labs14.roamly. views

import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.labs14.roamly.data.NetworkAdapter
import app.labs14.roamly.R
import app.labs14.roamly.adapters.AttractionListAdapter
import app.labs14.roamly.models.*
import app.labs14.roamly.notifications.NotificationUtils
import app.labs14.roamly.utils.BitmapConversion
import app.labs14.roamly.utils.Utils
import app.labs14.roamly.viewModels.AttractionViewModel
import app.labs14.roamly.viewModels.ItineraryViewModel
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.github.vipulasri.timelineview.TimelineView
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login_google.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.lang.ArithmeticException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


const val RC_SIGN_IN = 123

class LoginGoogleActivity : AppCompatActivity() {
    private lateinit var mAdapter: AttractionListAdapter
    private lateinit var mAttributes: TimelineAttributes
    //Test Notification
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private var mNotified = false
 //   private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var itineraryViewModel: ItineraryViewModel
    private lateinit var attractionViewModel: AttractionViewModel
    lateinit var currentUser:User
    var response = ""



    //Apollo prep shoon 2019/08/07
    companion object {
        private const val BASE_URL = "https://roamly-staging.herokuapp.com/gql"
        private const val ROAMLY_ACCESS_TOKENS = ""
    }

    val okHttpClient = OkHttpClient.Builder()
        .authenticator { _, response ->
            response.request().newBuilder().addHeader("Authorization", "Bearer 0").build()
        }.build()

    val apolloClient = ApolloClient.builder()
        .serverUrl(BASE_URL)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_google)

        initViewModels()
        googleLoginInit()
        btn_offline.setOnClickListener { offlineSignOn()}
        networkTest()
        setupApollo()
        //default values
        mAttributes = TimelineAttributes(
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
        btn_option.setOnClickListener{viewOption()}
       // mockData()
        //notificationTest()
    }
    private fun viewOption(){
        TimelineAttributesBottomSheet.showDialog(supportFragmentManager, mAttributes, object: TimelineAttributesBottomSheet.Callbacks {
            override fun onAttributesChanged(attributes: TimelineAttributes) {
                mAttributes = attributes
                initAdapter()
            }
        })
    }

    private fun initAdapter() {

        val mLayoutManager = if (mAttributes.orientation == Orientation.HORIZONTAL) {
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        } else {
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        recyclerView.layoutManager = mLayoutManager

        mAdapter = AttractionListAdapter( mAttributes)
        recyclerView.adapter = mAdapter
    }

    var users: Users?= Users(ArrayList<User>(1))
    //Apollo prep shoon 2019/08/07
    private fun setupApollo(){
        val query = AllInfoQuery.builder().build()

         apolloClient.query(query).enqueue(object : ApolloCall.Callback<AllInfoQuery.Data>() {
             override fun onFailure(e: ApolloException) {
                 print(e.toString())
             }

             override fun onResponse(response: Response<AllInfoQuery.Data>) {

                 var allusers = response.data()?.users()
                tv_debug.text= /*allusers?.size.toString()+allusers?.get(1).toString()+*/"  ID="+ allusers?.get(0)?.id().toString()


                    var tempItineraries: MutableList<Itinerary> = ArrayList<Itinerary>(10)
                 for(i in 0..(allusers?.size ?: 1)
                     -1){

                    var tempUser:User=User(
                        allusers?.get(i)?.id().toString(),allusers?.get(i)?.name().toString(),allusers?.get(i)?.name().toString(),tempItineraries)
                    users?.user?.add(tempUser)
                     var allusers = response.data()?.users()
                     tv_debug.text= "name="+ allusers?.get(i)?.name().toString()
                }

             }
         })
    }


    fun networkTest(){
        //thread { response =  NetworkAdapter.httpRequest("https://roamly-staging.herokuapp.com/city/image?q=New+York") }
    }

    private fun notificationTest(){
        if (!mNotified) {
            NotificationUtils().setNotification(mNotificationTime, this)
        }
    }

    private fun initViewModels(){
        itineraryViewModel = ViewModelProviders.of(this).get(ItineraryViewModel::class.java)
        attractionViewModel = ViewModelProviders.of(this).get(AttractionViewModel::class.java)
    }

    private fun googleLoginInit(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_button.visibility = View.VISIBLE
        sign_in_button.setSize(SignInButton.SIZE_STANDARD)
        sign_in_button.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun offlineSignOn(){
        val intent = Intent(this, ItineraryListActivity::class.java)

        intent.putExtra("attributes",mAttributes)

//        intent.putExtra("users",users)
        startActivity(intent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) = try {
        val account = completedTask.getResult(ApiException::class.java)
        val token = ""
        if (account != null) {
            val token = account.idToken
        }

        val headermap = hashMapOf<String,String>()
        headermap["authorization"] = token

        sign_in_button.visibility = View.GONE
        var result = ""
        Thread {
            result = NetworkAdapter.httpRequest(
                "https://roamly-staging.herokuapp.com/api/auth", NetworkAdapter.POST, headermap
            )
        }.run { start() }
       //TODO: Parse out user data from result.
    } catch (e: ApiException) {
        e.printStackTrace()
        sign_in_button.visibility = View.VISIBLE
    }

    private fun mockData(){
        itineraryViewModel.deleteAllItineraries()
        itineraryViewModel.insert(Itinerary(1,"Bali", 1564486657))

        itineraryViewModel.insert(Itinerary(2,"Vegas", 1564485950))
//        itineraryViewModel.getAllItineraries()
        attractionViewModel.insert(Attraction(3,1,"Ocean Snorkeling", 1564486657,1564496100,25,25,"Get close to seastars and manatees","-8.409518", "115.188919", "Bali, Indonesia", "(525) 264-1082",1,1564485950,"Airplane"))
        attractionViewModel.insert(Attraction(4,2,"Jungle Treking", 1564486657,1564496100,25,25,"Get lost in the green","-8.409518", "115.188919", "Bali, Indonesia", "(406) 703-6279",1,1564485950,"Airplane"))



    }
}
