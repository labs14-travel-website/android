package app.labs14.roamly.notifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.labs14.roamly.R
import kotlinx.android.synthetic.main.activity_notification_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_result)

        if (intent.getBooleanExtra("notification", false)) { //Just for confirmation
            txtTitleView.text = intent.getStringExtra("title")
            txtMsgView.text = intent.getStringExtra("message")
        }
    }
}