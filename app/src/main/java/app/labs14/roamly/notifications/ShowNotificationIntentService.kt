package app.labs14.roamly.notifications

import android.R
import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import androidx.core.app.NotificationCompat
import app.labs14.roamly.views.LoginGoogleActivity


class ShowNotificationIntentService : IntentService("ShowNotificationIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_SHOW_NOTIFICATION == action) {
                handleActionShow()
            } else if (ACTION_HIDE_NOTIFICATION == action) {
                handleActionHide()
            }
        }
    }

    private fun handleActionShow() {
        showStatusBarIcon(this@ShowNotificationIntentService)
    }

    private fun handleActionHide() {
     //   hideStatusBarIcon(this@ShowNotificationIntentService)
    }

    companion object {
        private val ACTION_SHOW_NOTIFICATION = "my.app.service.action.show"
        private val ACTION_HIDE_NOTIFICATION = "my.app.service.action.hide"

        fun startActionShow(context: Context) {
            val intent = Intent(context, ShowNotificationIntentService::class.java)
            intent.action = ACTION_SHOW_NOTIFICATION
            context.startService(intent)
        }

        fun startActionHide(context: Context) {
            val intent = Intent(context, ShowNotificationIntentService::class.java)
            intent.action = ACTION_HIDE_NOTIFICATION
            context.startService(intent)
        }

        fun showStatusBarIcon(ctx: Context) {
            val builder = NotificationCompat.Builder(ctx)
                .setContentTitle("notification")
                .setSmallIcon(R.drawable.ic_dialog_alert)
                .setOngoing(true)
            val intent = Intent(ctx, LoginGoogleActivity::class.java)
            val pIntent = PendingIntent.getActivity(ctx, 100, intent, 0)
            builder.setContentIntent(pIntent)
            val mNotificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notif = builder.build()
            notif.flags = notif.flags or Notification.FLAG_ONGOING_EVENT
            mNotificationManager.notify(100, notif)
        }
    }
}