package app.labs14.roamly. views

import  android.os.Bundle
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
import java.util.*
import kotlin.collections.ArrayList



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
        //     mockData()
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
                //   tv_debug.append( allusers?.size.toString()+allusers?.get(1).toString()+"  ID="+ allusers?.get(0)?.id().toString())


                //                itineraryViewModel.deleteAllItineraries()

                var tempItineraries: MutableList<Itinerary> = ArrayList<Itinerary>(10)
                for(i in 0..(allusers?.size ?: 1)
                        -1){
                    var numberOfItinerary=allusers?.get(i)?.itineraries()?.size!!
                    tv_debug.append(numberOfItinerary.toString())
                    if(numberOfItinerary > 0){
                        for(j in 0..numberOfItinerary-1){

                            tv_debug.append(allusers.get(i).itineraries()!!.get(j)?.id().toString())
                            val sizeOfAtraction=allusers.get(i).itineraries()!!.get(j)?.attractions()?.size
                            tv_debug.append("sizeOfAtraction="+sizeOfAtraction.toString())
                        }
                    }
                    var tempUser:User=User(
                        allusers?.get(i)?.id().toString(),allusers?.get(i)?.name().toString(),allusers?.get(i)?.name().toString(),tempItineraries)
                    users?.user?.add(tempUser)
                    tv_debug.append('\n'+i.toString()+" "+tempUser.name+" ")
                    if(tempUser.itineraries.size>0){
                        tv_debug.append(tempUser.itineraries.size.toString())
                    }else{
                        tv_debug.append("No itinerary")
                    }
                    // tv_debug.append(tempUser.name/*allusers?.get(i)?.name().toString()+ allusers?.get(i)?.itineraries()?.get(0)?.id().toString()+ allusers?.get(i)?.itineraries()?.get(0)?.attractions()?.get(0)?.id().toString()+ allusers?.get(i)?.itineraries()?.get(0)?.attractions()?.get(0)?.description()+ allusers?.get(i)?.itineraries()?.get(0)?.attractions()?.get(0)?.address()+ allusers?.get(i)?.itineraries()?.get(0)?.attractions()?.get(0)?.phone()+ allusers?.get(i)?.itineraries()?.get(0)?.attractions()?.get(0)?.placeId().toString()*/)

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
        if (account != null) {

            val personName = account.getDisplayName()
            val personGivenName = account.getGivenName()
            val personFamilyName = account.getFamilyName()
            val personEmail = account.getEmail()
            val personId = account.getId()
            val personPhoto = account.getPhotoUrl()
            tv_debug.text = personName + "," + personGivenName + "," + personFamilyName + "," + personEmail + "," + personId + personPhoto

            val token= account.idToken.toString()
            val headermap = hashMapOf<String, String>()
            headermap["authorization"]=token
            sign_in_button.visibility = View.GONE
            var result = ""
            Thread {
                result = NetworkAdapter.httpRequest(
                    "https://roamly-staging.herokuapp.com/api/auth", NetworkAdapter.POST, headermap
                )
            }.run { start() }
        }else{

        }
    }catch(e: ApiException) {
        e.printStackTrace()
        sign_in_button.visibility = View.VISIBLE
    }







    private fun mockData(){

        itineraryViewModel.deleteAllItineraries()

        itineraryViewModel.insert(Itinerary(1,"Bali", 1564486657))

        itineraryViewModel.insert(Itinerary(2,"Vegas", 1564486657))

        itineraryViewModel.insert(Itinerary(3,"Fuji", 1564486657))

        itineraryViewModel.insert(Itinerary(4,"San Francisco", 1564486657))

        itineraryViewModel.insert(Itinerary(5,"Thailand", 1564486657))

        itineraryViewModel.insert(Itinerary(6,"Tunisia", 1564486657))

        itineraryViewModel.insert(Itinerary(7,"Ghana", 1564486657))

        itineraryViewModel.insert(Itinerary(8,"Antarctica", 1564486657))

        itineraryViewModel.insert(Itinerary(9,"Patagonia", 1564486657))

        itineraryViewModel.insert(Itinerary(10,"Brooklyn", 1564486657))
        // itineraryViewModel.getAllItineraries()
        attractionViewModel.insert(Attraction(11,1,"Ocean Snorkeling", 1564486657,1564496100,25,25,"Get close to seastars and manatees","-8.409518", "115.188919", "Bali, Indonesia", "(525) 264-1082",1,1564485950,"Airplane"))
        attractionViewModel.insert(Attraction(12,9,"Jungle Treking", 1564486657,1564496100,25,25,"Get lost in the green","-8.409518", "115.188919", "Bali, Indonesia", "(406) 703-6279",1,1564485950,"Airplane"))

        attractionViewModel.insert(Attraction(13,10,"Swimming with Sharks", 1572739200,1572748451,25,25,"Don't be eaten","-8.409518", "115.188919", "Bali, Indonesia", "(403) 214-5135",1,1572738653,"Airplane"))
        attractionViewModel.insert(Attraction(14,11,"Live Music", 1564486657,1564496100,25,25,"Come for a famous star","36.114647", "-115.172813", "Washington", "(670) 480-9095",2,1564485950,"Walking"))

        attractionViewModel.insert(Attraction(15,2,"Comedy Show", 1564486657,1564496100,25,25,"Laugh at a silly gag","36.114647", "-115.172813", "Washington", "(992) 420-0332",2,1564485950,"Walking"))

        attractionViewModel.insert(Attraction(16,2,"Casino Gambling", 1572739200,1572748451,25,25,"Have fun tossing $ away","36.114647", "-115.172813", "Washington", "(849) 729-8349",2,1572738653,"Walking"))
        attractionViewModel.insert(Attraction(17,3,"Downhill Skiing", 1564486657,1564496100,25,25,"Ski down the majestic mountain","35.360638", "138.729050", "Mount Fuji, Japan", "(663) 619-6104",3,1564485950,"Train"))

        attractionViewModel.insert(Attraction(18,3,"Haunted Forest Tour", 1564486657,1564496100,25,25,"Explore the scary forest","35.360638", "138.729050", "Mount Fuji, Japan", "(888) 282-4410",3,1564485950,"Train"))

        attractionViewModel.insert(Attraction(19,3,"Mountain Climbing", 1572739200,1572748451,25,25,"Take oxygen","35.360638", "138.729050", "Mount Fuji, Japan", "(950) 585-1665",3,1572738653,"Train"))
        //  attractionViewModel.insert(Attraction(4,"Fishermans Wharf", 1564486657,1564496100,25,25,"Hear the sea lions and eat seafood","37.773972", "-122.431297", "California", "(463) 939-1809",4,1564485950,"Bicycle"))

        //  attractionViewModel.insert(Attraction(4,"Alcatraz Historical Tour", 1564486657,1564496100,25,25,"Explore the prison island","37.773972", "-122.431297", "California", "(611) 934-0394",4,1564485950,"Bicycle"))

        attractionViewModel.insert(Attraction(20,4,"Golden Gate Ruins", 1572739200,1572748451,25,25,"Experience ancient times","37.773972", "-122.431297", "California", "(985) 744-4351",4,1572738653,"Bicycle"))
        attractionViewModel.insert(Attraction(21,5,"Temple Hopping", 1564486657,1564496100,25,25,"See the exquisitely preserved temples","13.736717", "100.523186", "Thailand", "(477) 697-6290",5,1564485950,"Tuk-tuk"))

        attractionViewModel.insert(Attraction(22,5,"Yoga Teacher Training", 1564486657,1564496100,25,25,"Join an advanced yoga seminar","13.736717", "100.523186", "Thailand", "(515) 696-1405",5,1564485950,"Tuk-tuk"))
        attractionViewModel.insert(Attraction(23,5,"Tiger Petting", 1572739200,1572748451,25,25,"Which is the furriest?","13.736717", "100.523186", "Thailand", "(321) 633-3550",5,1572738653,"Tuk-tuk"))
        attractionViewModel.insert(Attraction(24,6,"Olive Treks", 1564486657,1564496100,25,25,"Visit the olive groves","36.862499", "10.195556", "Tunisia, Africa", "(573) 602-8514",6,1564485950,"Ship"))
        attractionViewModel.insert(Attraction(25,6,"Camel Racing", 1564486657,1564496100,25,25,"Who says camels can't run?","36.862499", "10.195556", "Tunisia, Africa", "(468) 933-2574",6,1564485950,"Ship"))
        attractionViewModel.insert(Attraction(26,6,"Soldier Training", 1572739200,1572748451,25,25,"Become a soldier for a day","36.862499", "10.195556", "Tunisia, Africa", "(558) 353-3320",6,1572738653,"Ship"))
        attractionViewModel.insert(Attraction(27,7,"Fruit Markets", 1564486657,1564496100,25,25,"Pick out some of the juiciest fruit","7.946527", "-1.023194", "Ghana, Africa", "(480) 378-0288",1,1564485950,"Airplane"))

        attractionViewModel.insert(Attraction(28,7,"Rave Party", 1564486657,1564496100,25,25,"The sounds of the people","7.946527", "-1.023194", "Ghana, Africa", "(689) 880-4165",1,1564485950,"Airplane"))

        attractionViewModel.insert(Attraction(29,7,"Hammock Massage", 1572739200,1572748451,25,25,"Get massaged by the beach on a hammock","7.946527", "-1.023194", "Ghana, Africa", "(672) 909-9102",1,1572738653,   "Airplane"))

        attractionViewModel.insert(Attraction(30,8,"Igloo Construction", 1564486657,1564496100,25,25,"Build an ice shelter","-75.2509766", "-0.071389", "Antarctica", "(957) 825-3071",6,1564485950,"Ship"))

        attractionViewModel.insert(Attraction(31,8,"Research and Measuring", 1564486657,1564496100,25,25,"Volunteer to gather data","-75.2509766", "-0.071389", "Antarctica", "(272) 859-5475",6,1564485950,"Ship"))

        attractionViewModel.insert(Attraction(32,8,"Snowshoeing", 1572739200,1572748451,25,25,"Hike over the vast snowy desert","-75.2509766", "-0.071389", "Antarctica", "(402) 986-9213",6,1572738653,"Ship"))

        attractionViewModel.insert(Attraction(33,9,"Wildlife Photography", 1564486657,1564496100,25,25,"Witness breathtaking scenery","48", "74", "Patagonia, Chile", "(734) 894-9467",3,1564485950,"Train"))

        attractionViewModel.insert(Attraction(34,9,"Horseback Riding", 1564486657,1564496100,25,25,"Saddle up!","48", "74", "Patagonia, Chile", "(429) 858-7774",3,1564485950,"Train"))

        attractionViewModel.insert(Attraction(35,9,"Paragliding", 1572739200,1572748451,25,25,"Fly through the clouds","48", "74", "Patagonia, Chile", "(813) 287-9376",3,1572738653,"Train"))
        attractionViewModel.insert(Attraction(36,10,"DJ Jams and Rap Battle", 1564486657,1564496100,25,25,"Get pumped with great impromptu music","40.650002", "-73.949997", "Brooklyn, New York", "(702) 305-6673",7,1564485950,"Car"))

        attractionViewModel.insert(Attraction(37,10,"Circuit of the Arts", 1564486657,1564496100,25,25,"Local artists showing","40.650002", "-73.949997", "Brooklyn, New York", "(400) 263-7329",7,1564485950,"Car"))

        attractionViewModel.insert(Attraction(38,10,"Outdoor Basketball Game", 1572739200,1572748451,25,25,"Watch amazing feats of skill","40.650002", "-73.949997", "Brooklyn, New York", "(872) 886-7323",7,1572738653,"Car"))

    }
}
