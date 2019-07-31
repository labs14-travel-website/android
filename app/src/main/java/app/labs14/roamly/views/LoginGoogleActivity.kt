package app.labs14.roamly.views

import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.labs14.roamly.data.NetworkAdapter
import app.labs14.roamly.R
import app.labs14.roamly.models.Attraction
import app.labs14.roamly.models.Itinerary
import app.labs14.roamly.models.User
import app.labs14.roamly.notifications.NotificationUtils
import app.labs14.roamly.viewModels.AttractionViewModel
import app.labs14.roamly.viewModels.ItineraryViewModel
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login_google.*
import java.util.*


const val RC_SIGN_IN = 123

class LoginGoogleActivity : AppCompatActivity() {

    //Test Notification
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
    private var mNotified = false


    private lateinit var itineraryViewModel: ItineraryViewModel
    private lateinit var attractionViewModel: AttractionViewModel
    lateinit var currentUser:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_google)

        initViewModels()
        debugMessages()
        googleLoginInit()
        btn_offline.setOnClickListener { offlineSignOn()}
        notificationTest()
      //  mockData()
    }

    private fun debugMessages(){tv_debug.visibility = View.VISIBLE}

    private fun notificationTest(){
        if (!mNotified) {
            NotificationUtils().setNotification(mNotificationTime, this)
        }
    }

    private fun loadUserData(){

        //TODO: if internet connection isn't available
        // or user settings is set to offline getLocalData

    }

    private fun initViewModels(){
        itineraryViewModel = ViewModelProviders.of(this).get(ItineraryViewModel::class.java)
        attractionViewModel = ViewModelProviders.of(this).get(AttractionViewModel::class.java)
    }

    private fun updateAttraction(updateAttraction:List<Attraction>,pos:Int){
    }

    private fun updateItinerary(updateItinerary:List<Itinerary>){
    }

    private fun updateUser(){

    }

    private fun mockData(){
        itineraryViewModel.deleteAllItineraries()

        itineraryViewModel.insert(Itinerary("Bali", "This place is rad"))
        itineraryViewModel.insert(Itinerary("Vegas", "What happens here, stays here"))
        itineraryViewModel.insert(Itinerary("Fuji", "Climb it!"))
        itineraryViewModel.insert(Itinerary("San Francisco", "Brapp brapp!"))
        itineraryViewModel.insert(Itinerary("Thailand", "Coconut beaches"))
        itineraryViewModel.insert(Itinerary("Tunisia", "An olive branch"))
        itineraryViewModel.insert(Itinerary("Ghana", "Colorful markets by the cities"))
        itineraryViewModel.insert(Itinerary("Antarctica", "See a 24-hour day of sunlight"))
        itineraryViewModel.insert(Itinerary("Patagonia", "Vast expanses of landscapes and beasts"))
        itineraryViewModel.insert(Itinerary("Brooklyn", "Come walk in the gentrified inner city"))

        attractionViewModel.insert(Attraction(1,"Ocean Snorkeling", 1564486657,1564496100,25,25,"Get close to seastars and manatees","-8.409518", "115.188919", "Bali, Indonesia", "(525) 264-1082",1,1564485950,"Airplane"))
        attractionViewModel.insert(Attraction(1,"Jungle Treking", 1564486657,1564496100,25,25,"Get lost in the green","-8.409518", "115.188919", "Bali, Indonesia", "(406) 703-6279",1,1564485950,"Airplane"))
        attractionViewModel.insert(Attraction(1,"Swimming with Sharks", 1564486657,1564496100,25,25,"Don't be eaten","-8.409518", "115.188919", "Bali, Indonesia", "(403) 214-5135",1,1564485950,"Airplane"))

        attractionViewModel.insert(Attraction(2,"Live Music", 1564486657,1564496100,25,25,"Come for a famous star","36.114647", "-115.172813", "Washington", "(670) 480-9095",2,1564485950,"Walking"))
        attractionViewModel.insert(Attraction(2,"Comedy Show", 1564486657,1564496100,25,25,"Laugh at a silly gag","36.114647", "-115.172813", "Washington", "(992) 420-0332",2,1564485950,"Walking"))
        attractionViewModel.insert(Attraction(2,"Casino Gambling", 1564486657,1564496100,25,25,"Have fun tossing $ away","36.114647", "-115.172813", "Washington", "(849) 729-8349",2,1564485950,"Walking"))

        attractionViewModel.insert(Attraction(3,"Downhill Skiing", 1564486657,1564496100,25,25,"Ski down the majestic mountain","35.360638", "138.729050", "Mount Fuji, Japan", "(663) 619-6104",3,1564485950,"Train"))
        attractionViewModel.insert(Attraction(3,"Haunted Forest Tour", 1564486657,1564496100,25,25,"Explore the scary forest","35.360638", "138.729050", "Mount Fuji, Japan", "(888) 282-4410",3,1564485950,"Train"))
        attractionViewModel.insert(Attraction(3,"Mountain Climbing", 1564486657,1564496100,25,25,"Take oxygen","35.360638", "138.729050", "Mount Fuji, Japan", "(950) 585-1665",3,1564485950,"Train"))

        attractionViewModel.insert(Attraction(4,"Fisherman's Wharf", 1564486657,1564496100,25,25,"Hear the sea lions and eat seafood","37.773972", "-122.431297", "California", "(463) 939-1809",4,1564485950,"Bicycle"))
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

    private fun getLocalUserData(){
    }

    private fun getServerUserData(){
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
        startActivity(intent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
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
    }
}
