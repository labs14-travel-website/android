package app.labs14.roamly.localStorage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import app.labs14.roamly.models.ActivityEvent

import java.util.ArrayList

class ActivityEventSqlDao(context: Context) {

    val allActivityEvent: List<ActivityEvent>
        get() {
            val cursor = db.rawQuery(
                "SELECT * FROM " + ActivityEventDBContract.ActivityEventEntry.TABLE_NAME,
                arrayOf()
            )

            val rows = ArrayList<ActivityEvent>()
            while (cursor.moveToNext()) {
                rows.add(getActivityEvent(cursor))
            }

            cursor.close()
            return rows
        }

    init {
        val dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun updateActivityEvent(activityEvent: ActivityEvent) {
        val affectedRows = db.update(
            ActivityEventDBContract.ActivityEventEntry.TABLE_NAME,
            getContentValues(activityEvent),
            ActivityEventDBContract.ActivityEventEntry._ID + "=?",
            arrayOf(activityEvent.id.toString())
        )
    }

    fun deleteActivityEvent(activityEvent: ActivityEvent) {
        val affectedRows = db.delete(
            ActivityEventDBContract.ActivityEventEntry.TABLE_NAME,
            ActivityEventDBContract.ActivityEventEntry._ID + "=?",
            arrayOf(activityEvent.id.toString())
        )
    }

    private fun getActivityEvent(cursor: Cursor): ActivityEvent {
        var index: Int = cursor.getColumnIndexOrThrow(ActivityEventDBContract.ActivityEventEntry._ID)
        val id = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDBContract.ActivityEventEntry.COLUMN_NAME_IS_READ)
        val isRead = cursor.getInt(index)

        return ActivityEvent()
    }

    companion object {
        private lateinit var db: SQLiteDatabase

        fun addActivityEvent(activityEvent: ActivityEvent) {
            val values = getContentValues(activityEvent)

            val insert = db.insert(ActivityEventDBContract.ActivityEventEntry.TABLE_NAME, null, values)
        }

        private fun getContentValues(activityEvent: ActivityEvent): ContentValues {
            val values = ContentValues()

            values.put(ActivityEventDBContract.ActivityEventEntry._ID, activityEvent.id)
            return values
        }
    }
}