package com.example.jordan.enviroapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import com.example.jordan.enviroapp.RecycleViews.ProjectDataStruct;
import com.example.jordan.enviroapp.RecycleViews.ProjectInfoStruct;

import static com.example.jordan.enviroapp.DataBase.DatabaseContract.ProjectData.TABLE_NAME_DATA;
import static com.example.jordan.enviroapp.DataBase.DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT;

public class ProjectInfoReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 21; //final Version
    public static final String DATABASE_NAME = "project.db";
    public static final String SQL_CREATE_PROJECT_TABLE = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT
            + " (" + DatabaseContract.ProjectInfoTable.COLUMN_NAME_ID  + " INTEGER PRIMARY KEY " + "," + DatabaseContract.ProjectInfoTable.COLUMN_NAME_PROJECTNAME + " TEXT " + ","
            + DatabaseContract.ProjectInfoTable.COLUMN_NAME_LOCATIONTEXT + " TEXT " + "," + DatabaseContract.ProjectInfoTable.COLUMN_NAME_SUBJECT + " TEXT " + ","
            + DatabaseContract.ProjectInfoTable.COLUMN_NAME_GROUPMEMBERS + " TEXT "
            + ")";

    public static final String SQL_CREATE_PROJECTDATA_TABLE = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.ProjectData.TABLE_NAME_DATA
            + " (" + DatabaseContract.ProjectData.COLUMN_NAME_ID  + " INTEGER PRIMARY KEY " + "," + DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_LONG + " TEXT " + ","  + DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_Alt + " TEXT " + ","
            + DatabaseContract.ProjectData.COLUMN_NAME_NOTES + " TEXT " + ", " + DatabaseContract.ProjectData.COLUMN_PROJECTINFO_ID  + " INTEGER " +  ","
            + DatabaseContract.ProjectData.COLUMN_NAME_IMAGE_PATH + " TEXT " + ","
            + "FOREIGN KEY(projectid) REFERENCES " +  DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT + "(_ID))";


    public static final String SQL_PROJECT_INFO_QUERY = "SELECT * FROM  " + DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT;
    public static final String SQL_PROJECT_DATA_QUERY = "SELECT * FROM  " + DatabaseContract.ProjectData.TABLE_NAME_DATA;

    public static final String SQL_PROJECT_INFO_QUERY_Update = "SELECT * FROM  " + DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT ;
    public static final String SQL_PROJECT_DATA_QUERY_Update = "SELECT * FROM  " + DatabaseContract.ProjectData.TABLE_NAME_DATA;


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
     * File Path
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


    /**
     * Project queries fill up the arrays below, to then be sent to the recycler view.
     * Checks the last entry to save the picture with the largest id possible to make it easier to connect data with correct
     * pictures.
     *
     * Needs updated query to only select max ID for Project Data
     * */

    public static int getAllProjects(SQLiteDatabase db){
        Cursor cursor = db.rawQuery(ProjectInfoReaderDbHelper.SQL_PROJECT_INFO_QUERY_Update, null);
        ProjectInfoStruct projectDataStruct = null;

        if (cursor.moveToFirst()){
            do{
                projectDataStruct = new ProjectInfoStruct();
                projectDataStruct.setId(cursor.getInt(0));
                projectDataStruct.setProjectName(cursor.getString(1));
                projectDataStruct.setLocationText(cursor.getString(2));
                projectDataStruct.setSubject(cursor.getString(3));
                projectDataStruct.setGroupMembers(cursor.getString(4));

            }while (cursor.moveToNext());
        }

        if (projectDataStruct == null){
            return 0;
        }

        int getlargestId = projectDataStruct.getId();
        return getlargestId;
    }

    /**
     * Project queries fill up the arrays below, to then be sent to the recycler view.
     * Checks the last entry to save the picture with the largest id possible to make it easier to connect data with correct
     * pictures
     * */

    public static long getAllProjectData(SQLiteDatabase db){
        //Generating query.
        Cursor cursor = db.rawQuery(ProjectInfoReaderDbHelper.SQL_PROJECT_DATA_QUERY_Update, null);
        ProjectDataStruct projectDataStruct = new ProjectDataStruct();

        boolean firstEntry = true;
        if (cursor.moveToFirst()){
            firstEntry = false;
            do{
                projectDataStruct = new ProjectDataStruct();
                projectDataStruct.setId(cursor.getInt(0));
                projectDataStruct.setLongatiude(cursor.getString(1));
                projectDataStruct.setLatiude(cursor.getString(2));
                projectDataStruct.setNotes(cursor.getString(3));
                projectDataStruct.setProjectId(cursor.getInt(5));
            }while (cursor.moveToNext());
        }

        int getlargestIdData;

        if (firstEntry)
            getlargestIdData = 0;
        else
            getlargestIdData = projectDataStruct.getId();

        System.out.println("Project Data: " + getlargestIdData);

        return getlargestIdData;
    }


    public static long insertNewProject(String getProjectName, String locationTest, String projecetSubjectText, String GroupMembers, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ProjectInfoTable.COLUMN_NAME_PROJECTNAME, getProjectName);
        values.put(DatabaseContract.ProjectInfoTable.COLUMN_NAME_LOCATIONTEXT, locationTest);
        values.put(DatabaseContract.ProjectInfoTable.COLUMN_NAME_SUBJECT, projecetSubjectText);
        values.put(DatabaseContract.ProjectInfoTable.COLUMN_NAME_GROUPMEMBERS, GroupMembers);

        //Get Generated Key.
        long  generatedRow = db.insert(TABLE_NAME_PROJECT, null, values); //insert values into the database via values, map relation.
        return generatedRow;
    }

    public static long insertProjectData(long generatedRow , String latstr, String longstr, String mCurrentPhotoPath , TextView textCapt, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_LONG, longstr);
        values.put(DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_Alt, latstr);
        values.put(DatabaseContract.ProjectData.COLUMN_NAME_NOTES, textCapt.getText().toString());
        if (mCurrentPhotoPath == null){
            values.put(DatabaseContract.ProjectData.COLUMN_NAME_IMAGE_PATH, "");
        }else{
            values.put(DatabaseContract.ProjectData.COLUMN_NAME_IMAGE_PATH, mCurrentPhotoPath);
        }
        values.put(DatabaseContract.ProjectData.COLUMN_PROJECTINFO_ID, generatedRow); // Insert the generated Row Here.
        long finalEval = db.insert(TABLE_NAME_DATA, null, values);

        //Get Generated Key.
        return finalEval;
    }

    /*
    * if the Project is already created then add the data to the old project.
    * */

    public static long insertProjectDataOld(int id,  String latstr, String longstr, String mCurrentPhotoPath , TextView textCapt, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_LONG, longstr);
        values.put(DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_Alt, latstr);
        values.put(DatabaseContract.ProjectData.COLUMN_NAME_NOTES, textCapt.getText().toString());
        if (mCurrentPhotoPath == null){
            values.put(DatabaseContract.ProjectData.COLUMN_NAME_IMAGE_PATH, "");
        }else{
            values.put(DatabaseContract.ProjectData.COLUMN_NAME_IMAGE_PATH, mCurrentPhotoPath);
        }
        values.put(DatabaseContract.ProjectData.COLUMN_PROJECTINFO_ID, id); // Insert the generated Row Here.
        long finalEval = db.insert(TABLE_NAME_DATA, null, values);
        //Get Generated Key.
        return finalEval;
    }


}
