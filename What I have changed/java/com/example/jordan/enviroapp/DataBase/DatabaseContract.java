package com.example.jordan.enviroapp.DataBase;

import android.provider.BaseColumns;

/**
 * Created by Jordan on 10/04/2017. Schema created, defines layout of the Database. Tables implemented are Information based on form details along with actions taking with the projecets.
 */

    public final class DatabaseContract {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "test1.db";
        public static final String TEXT_TYPE = " Text";
        public static final String COMMA_SEP = ",";

    //private Constructor so caller does not init the class
    private DatabaseContract(){}

    public static class ProjectInfoTable implements BaseColumns{
        public static final String TABLE_NAME_PROJECT= "projectinfo";
        public static final String COLUMN_NAME_ID = "_ID";
        public static final String COLUMN_NAME_PROJECTNAME = "projectname";
        public static final String COLUMN_NAME_LOCATIONTEXT = "locationText";
        public static final String COLUMN_NAME_SUBJECT = "subject";
        public static final String COLUMN_NAME_GROUPMEMBERS = "groupmembers";
    }

    public static class ProjectData implements BaseColumns{
        public static final String TABLE_NAME_DATA = "projectdata";
        public static final String COLUMN_NAME_ID = "_ID";
        public static final String COLUMN_NAME_LOCATION_LONG = "Long";
        public static final String COLUMN_NAME_LOCATION_Alt = "Alt";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_PROJECTINFO_ID = "projectid";
    }
}
