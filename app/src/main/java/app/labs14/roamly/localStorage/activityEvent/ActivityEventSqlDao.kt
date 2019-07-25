package app.labs14.roamly.localStorage.activityEvent

//Brandon Lively - 07-25-2019

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import app.labs14.roamly.localStorage.DbHelper
import app.labs14.roamly.models.ActivityEvent

import java.util.ArrayList

class ActivityEventSqlDao(context: Context) {


    fun addActivityEvent(activityEvent: ActivityEvent) {
        val values =
            getContentValues(activityEvent)
        val insert = db.insert(ActivityEventDbContract.ActivityEventEntry.TABLE_NAME, null, values)
    }

    val allActivityEvents: List<ActivityEvent>
        get() {
            val cursor = db.rawQuery(
                "SELECT * FROM " + ActivityEventDbContract.ActivityEventEntry.TABLE_NAME,
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
            ActivityEventDbContract.ActivityEventEntry.TABLE_NAME,
            getContentValues(activityEvent),
            ActivityEventDbContract.ActivityEventEntry._ID + "=?",
            arrayOf(activityEvent.id.toString())
        )
    }

    fun deleteActivityEvent(activityEvent: ActivityEvent) {
        val affectedRows = db.delete(
            ActivityEventDbContract.ActivityEventEntry.TABLE_NAME,
            ActivityEventDbContract.ActivityEventEntry._ID + "=?",
            arrayOf(activityEvent.id.toString())
        )
    }

    private fun getActivityEvent(cursor: Cursor): ActivityEvent {
        var index: Int = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry._ID)
        val id = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_NAME)
        val name = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_START_TIME)
        val startTime = cursor.getLong(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_END_TIME)
        val endTime = cursor.getLong(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_ALARM_BEFORE)
        val alarmBefore = cursor.getLong(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_ALARM_AFTER)
        val alarmAfter = cursor.getLong(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_START_LAT)
        val startLat = cursor.getDouble(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_START_LNG)
        val startLng = cursor.getDouble(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_END_LAT)
        val endLat = cursor.getDouble(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_END_LON)
        val endLng = cursor.getDouble(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_ADDRESS)
        val address = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_PHONE_NUM)
        val phoneNum = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_DESCRIPTION)
        val description = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_TRANSPORT_TYPE)
        val transportType = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_TRANSPORT_ETA)
        val transportEta = cursor.getLong(index)

        index = cursor.getColumnIndexOrThrow(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_TRANSPORT_LABEL)
        val transportLabel = cursor.getString(index)

        return ActivityEvent(
                id,
                name,
                startTime,
                endTime,
                alarmBefore,
                alarmAfter,
                startLat,
                startLng,
                endLat,
                endLng,
                address,
                phoneNum,
                description,
                transportType,
                transportEta,
                transportLabel)
    }

    companion object {
        private lateinit var db: SQLiteDatabase

        fun addActivityEvent(activityEvent: ActivityEvent) {
            val values = getContentValues(
                activityEvent
            )

            val insert = db.insert(ActivityEventDbContract.ActivityEventEntry.TABLE_NAME, null, values)
        }

        private fun getContentValues(activityEvent: ActivityEvent): ContentValues {
            val values = ContentValues()
            values.put(ActivityEventDbContract.ActivityEventEntry._ID,                         activityEvent.id)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_NAME,            activityEvent.name)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_START_TIME,      activityEvent.startTime)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_END_TIME,        activityEvent.endTime)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_ALARM_BEFORE,    activityEvent.alarmBefore)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_ALARM_AFTER,     activityEvent.alarmAfter)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_START_LAT,       activityEvent.startLat)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_START_LNG,       activityEvent.startLng)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_END_LAT,         activityEvent.endLat)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_END_LON,         activityEvent.endLng)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_ADDRESS,         activityEvent.address)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_PHONE_NUM,       activityEvent.phoneNum)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_DESCRIPTION,     activityEvent.description)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_TRANSPORT_TYPE,  activityEvent.transportType)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_TRANSPORT_ETA,   activityEvent.transportEta)
            values.put(ActivityEventDbContract.ActivityEventEntry.COLUMN_NAME_TRANSPORT_LABEL, activityEvent.transportLabel)
            return values
        }
    }
}