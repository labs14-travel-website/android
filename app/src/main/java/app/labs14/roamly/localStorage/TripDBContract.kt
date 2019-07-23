package app.labs14.roamly.localStorage

import android.provider.BaseColumns

class TripDBContract {
    class TripEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "trips"
            const val COLUMN_NAME_IS_READ = "is_read"
            const val _ID  = ""

            const val SQL_CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME
                    + " ( " +
                    BaseColumns._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_IS_READ + " INTEGER" +
                    "  );")

            const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        }
    }
}
