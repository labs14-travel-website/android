package app.labs14.roamly.localStorage.activityEvent;

//Brandon Lively - 07/25/2019

import android.provider.BaseColumns;

public class ActivityEventDbContract {
    public static class ActivityEventEntry implements BaseColumns {
        public static final String  TABLE_NAME                  = "activity_event";
        public static final String  COLUMN_NAME_NAME            = "name";
        public static final String  COLUMN_NAME_START_TIME      = "start_time";
        public static final String  COLUMN_NAME_END_TIME        = "end_time";
        public static final String  COLUMN_NAME_ALARM_BEFORE    = "alarm_before";
        public static final String  COLUMN_NAME_ALARM_AFTER     = "alarm_after";
        public static final String  COLUMN_NAME_START_LAT       = "start_lat";
        public static final String  COLUMN_NAME_START_LNG       = "start_lng";
        public static final String  COLUMN_NAME_END_LAT         = "end_lat";
        public static final String  COLUMN_NAME_END_LON         = "end_lng";
        public static final String  COLUMN_NAME_ADDRESS         = "address";
        public static final String  COLUMN_NAME_PHONE_NUM       = "phone_num";
        public static final String  COLUMN_NAME_DESCRIPTION     = "description";
        public static final String  COLUMN_NAME_TRANSPORT_TYPE  = "transport_type";
        public static final String  COLUMN_NAME_TRANSPORT_ETA   = "transport_eta";
        public static final String  COLUMN_NAME_TRANSPORT_LABEL = "transport_label";


        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + " ( " +
                _ID                         + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_NAME            + " TEXT," +
                COLUMN_NAME_START_TIME      + " INTEGER," +
                COLUMN_NAME_END_TIME        + " INTEGER," +
                COLUMN_NAME_ALARM_BEFORE    + " INTEGER," +
                COLUMN_NAME_ALARM_AFTER     + " INTEGER," +
                COLUMN_NAME_START_LAT       + " INTEGER," +
                COLUMN_NAME_START_LNG       + " INTEGER," +
                COLUMN_NAME_END_LAT         + " INTEGER," +
                COLUMN_NAME_END_LON         + " INTEGER," +
                COLUMN_NAME_ADDRESS         + " TEXT," +
                COLUMN_NAME_PHONE_NUM       + " TEXT," +
                COLUMN_NAME_DESCRIPTION     + " TEXT," +
                COLUMN_NAME_TRANSPORT_TYPE  + " INTEGER," +
                COLUMN_NAME_TRANSPORT_ETA   + " INTEGER," +
                COLUMN_NAME_TRANSPORT_LABEL + " TEXT" +
                "  );";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
