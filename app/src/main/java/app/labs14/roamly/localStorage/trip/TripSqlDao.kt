package app.labs14.roamly.localStorage.trip

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import app.labs14.roamly.localStorage.DbHelper
import app.labs14.roamly.localStorage.user.UserDbContract
import app.labs14.roamly.models.Trip

import java.util.ArrayList

class TripSqlDao(context: Context) {

    fun addTrip(trip: Trip) {
        val values = getContentValues(trip)
        val insert = db.insert(UserDbContract.UserEntry.TABLE_NAME, null, values)
    }

    val allTrips: List<Trip>
        get() {
            val cursor = db.rawQuery(
                "SELECT * FROM " + TripDbContract.TripEntry.TABLE_NAME,
                arrayOf()
            )

            val rows = ArrayList<Trip>()
            while (cursor.moveToNext()) {
                rows.add(getTrip(cursor))
            }

            cursor.close()
            return rows
        }

    init {
        val dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun updateTrip(trip: Trip) {
        val affectedRows = db.update(
            TripDbContract.TripEntry.TABLE_NAME,
            getContentValues(trip),
            TripDbContract.TripEntry._ID + "=?",
            arrayOf(trip.id.toString())
        )
    }

    fun deleteTrip(trip: Trip) {
        val affectedRows = db.delete(
            TripDbContract.TripEntry.TABLE_NAME,
            TripDbContract.TripEntry._ID + "=?",
            arrayOf(trip.id.toString())
        )
    }

    private fun getTrip(cursor: Cursor): Trip {
        var index: Int = cursor.getColumnIndexOrThrow(TripDbContract.TripEntry._ID)
        val id = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(TripDbContract.TripEntry.COLUMN_NAME_NAME)
        val isRead = cursor.getInt(index)

        return Trip()
    }

    companion object {
        private lateinit var db: SQLiteDatabase

        fun addTrip(trip: Trip) {
            val values = getContentValues(trip)

            val insert = db.insert(TripDbContract.TripEntry.TABLE_NAME, null, values)
        }

        private fun getContentValues(trip: Trip): ContentValues {
            val values = ContentValues()

            values.put(TripDbContract.TripEntry._ID, trip.id)
            return values
        }
    }
}