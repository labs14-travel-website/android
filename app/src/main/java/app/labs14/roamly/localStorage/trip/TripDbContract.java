package app.labs14.roamly.localStorage.trip;

import android.provider.BaseColumns;

public class TripDbContract {
    public static class TripEntry implements BaseColumns {
        public static final String TABLE_NAME                  = "trips";
        public static final String COLUMN_NAME_NAME  = "name";


        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + " ( " +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_NAME + " TEXT" +
                "  );";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
