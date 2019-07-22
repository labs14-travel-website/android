package app.labs14.roamly

import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import app.labs14.roamly.localStorage.DbHelper
import app.labs14.roamly.localStorage.SqlDao
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import app.labs14.roamly.R
import kotlinx.android.synthetic.main.activity_login_google.*


const val RC_SIGN_IN = 123
private var dbDao: SqlDao? = null

class LoginGoogleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_google)
        SqlDbInit()
        GoogleAuthInit()
    }

    fun SqlDbInit(){
        dbDao = SqlDao(this)
        val helper = DbHelper(this)
        val writableDatabase = helper.writableDatabase

        Thread(Runnable {
            val trips = dbDao?.allTrips }).start()
    }

    fun GoogleAuthInit(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_button.visibility = View.VISIBLE
        tv_name.visibility = View.GONE
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

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val token = ""
            if (account != null) {
                tv_name.text = account.displayName
                val token = account.idToken
            }

            val headermap = hashMapOf<String,String>()
            headermap.put("authorization", token)

            sign_in_button.visibility = View.GONE
            tv_name.visibility = View.VISIBLE
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
            tv_name.visibility = View.GONE
        }
    }
}
