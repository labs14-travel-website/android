package app.labs14.roamly

import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.labs14.roamly.localStorage.activityEvent.ActivityEventSqlDao
import app.labs14.roamly.localStorage.DbHelper
import app.labs14.roamly.localStorage.trip.TripSqlDao
import app.labs14.roamly.localStorage.user.UserSqlDao
import app.labs14.roamly.models.ActivityEvent
import app.labs14.roamly.models.User
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login_google.*


const val RC_SIGN_IN = 123
private var tripSqlDao: TripSqlDao? = null
private var activityEventSqlDao: ActivityEventSqlDao? = null
private var userSqlDao: UserSqlDao? = null

class LoginGoogleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_google)

        debugMessages()
        sqlDbInit()
        googleLoginInit()
        btn_offline.setOnClickListener { offlineSignOn()}
    }

    private fun debugMessages(){tv_debug.visibility = View.VISIBLE}

    private fun sqlDbInit(){
        tripSqlDao = TripSqlDao(this)
        activityEventSqlDao = ActivityEventSqlDao(this)
        userSqlDao = UserSqlDao(this)
        val helper = DbHelper(this)
        val writableDatabase = helper.writableDatabase

        Thread(Runnable {
            val trips = tripSqlDao?.allTrips
            val activityEvents = activityEventSqlDao?.allActivityEvents
            val users = userSqlDao?.allUsers}).start()

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
        //TODO : intent that directs to trip view activity
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
