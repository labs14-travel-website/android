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
import app.labs14.roamly.viewModels.AttractionViewModel
import app.labs14.roamly.viewModels.ItineraryViewModel
import app.labs14.roamly.viewModels.UserViewModel
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login_google.*


const val RC_SIGN_IN = 123

class LoginGoogleActivity : AppCompatActivity() {


    private lateinit var userViewModel: UserViewModel
    private lateinit var itineraryViewModel: ItineraryViewModel
    private lateinit var attractionViewModel: AttractionViewModel
    lateinit var users:List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_google)

        debugMessages()
        googleLoginInit()
        btn_offline.setOnClickListener { offlineSignOn()}
        mockData()
    }

    private fun debugMessages(){tv_debug.visibility = View.VISIBLE}


    private fun loadUserData(){

        //TODO: if internet connection isn't available
        // or user settings is set to offline getLocalData

    }

    private fun updateUser(updateUsers:List<User>){
        users = updateUsers
    }

    private fun mockData(){


        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.getAllUsers().observe(this, Observer<List<User>>{
            //Everytime user data changes, this block will run
            updateUser(it)
        })

        itineraryViewModel = ViewModelProviders.of(this).get(ItineraryViewModel::class.java)
        attractionViewModel = ViewModelProviders.of(this).get(AttractionViewModel::class.java)

        userViewModel.insert(User("Test User"))
        userViewModel.insert(User("Test User"))


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
