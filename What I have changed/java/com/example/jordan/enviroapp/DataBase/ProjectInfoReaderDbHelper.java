package com.example.jordan.enviroapp.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.jordan.enviroapp.RecycleViews.ProjectDataStruct;
import com.example.jordan.enviroapp.RecycleViews.ProjectInfoStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 09/05/2017.
 */

public class ProjectInfoReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 13; //final verVer
    public static final String DATABASE_NAME = "project.db";
    public static final String SQL_CREATE_PROJECT_TABLE = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT
            + " (" + DatabaseContract.ProjectInfoTable.COLUMN_NAME_ID  + " INTEGER PRIMARY KEY " + "," + DatabaseContract.ProjectInfoTable.COLUMN_NAME_PROJECTNAME + " TEXT " + ","
            + DatabaseContract.ProjectInfoTable.COLUMN_NAME_LOCATIONTEXT + " TEXT " + "," + DatabaseContract.ProjectInfoTable.COLUMN_NAME_SUBJECT + " TEXT " + ","
            + DatabaseContract.ProjectInfoTable.COLUMN_NAME_GROUPMEMBERS + " TEXT "
            + ")";

    public static final String SQL_CREATE_PROJECTDATA_TABLE = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.ProjectData.TABLE_NAME_DATA
            + " (" + DatabaseContract.ProjectData.COLUMN_NAME_ID  + "INTEGER PRIMARY KEY " + "," + DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_LONG + " TEXT " + ","  + DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_Alt + " TEXT " + ","
            + DatabaseContract.ProjectData.COLUMN_NAME_NOTES + " TEXT " + ", " + DatabaseContract.ProjectData.COLUMN_PROJECTINFO_ID  + " INTEGER " +  ","
            + "FOREIGN KEY(projectid) REFERENCES " +  DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT + "(_ID))";


    public static final String SQL_PROJECT_INFO_QUERY = "SELECT * FROM  " + DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT;
    public static final String SQL_PROJECT_DATA_QUERY = "SELECT * FROM  " + DatabaseContract.ProjectData.TABLE_NAME_DATA;



    /***
     * Database Struct
     * ProjectTable
     * Id
     * ProjectName
     * Location
     *Subject
     *GroupMembers
     * */

    /***
     * Database Struct
     * ProjecetData
     * Id
     * LocationLong
     * LocationAlt
     *Notes
     *projectid Ref Id in ProjectTable
     * */


    public ProjectInfoReaderDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PROJECTDATA_TABLE);
        db.execSQL(SQL_CREATE_PROJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ProjectData.TABLE_NAME_DATA);

        onCreate(db);
    }

}
