package com.example.jordan.enviroapp;


import android.Manifest;

import android.content.ContentValues;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jordan.enviroapp.DataBase.DatabaseContract;
import com.example.jordan.enviroapp.DataBase.ProjectInfoReaderDbHelper;



import static com.example.jordan.enviroapp.DataBase.DatabaseContract.ProjectInfoTable.COLUMN_NAME_PROJECTNAME;
import static com.example.jordan.enviroapp.DataBase.DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT;

public class RecordLocations extends AppCompatActivity {
    private Button recordBtn;
    private TextView textCapt;
    private SQLiteDatabase db;
    private long generatedRow;
    String getProjectName, locationTest, projecetSubjectText, GroupMembers;
    private LocationManager locationManager;
    private LocationListener locationlistener;
    String latstr;
    String longstr;
    boolean locEnabled; //Global Var, acting as a kind of out proc
    int iDfromRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        iDfromRecyclerView = bundle.getInt("ProjectId");
        System.out.println(iDfromRecyclerView);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationlistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double longitude = location.getLongitude();
                double lat = location.getLatitude();
                 latstr = Double.toString(lat);
                 longstr = Double.toString(longitude);
                Toast.makeText(getApplicationContext(),"Latitude " + latstr +  "  Longitude: "  + longitude, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        /* Permission check */


        db = new ProjectInfoReaderDbHelper(this).getWritableDatabase();

        recordBtn = (Button) findViewById(R.id.btncapture);
        textCapt = (TextView) findViewById(R.id.addNotes);

        String contentValues = getIntent().getStringExtra("ValueMap");
        System.out.println(contentValues);

        /**
         * Capture Data held within the NewProjectForm, implemented here to insert The Generated Key with the record.
         */

        Bundle bundle2 = getIntent().getExtras();
        getProjectName = bundle2.getString("projectNameText");
        locationTest = bundle2.getString("locationText");
        projecetSubjectText = bundle2.getString("projectSubjectText");
        GroupMembers = bundle2.getString("groupMembersText");


        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long newRowId;
                if (ActivityCompat.checkSelfPermission(RecordLocations.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RecordLocations.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);

                    return;
                } else {
                    //cont conf
                    getLocations();
                }

                if (iDfromRecyclerView > 0) {
                    getLocations();
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    latstr = Double.toString(location.getLatitude());
                    longstr = Double.toString(location.getLongitude());

                    ContentValues values2 = new ContentValues();
                    values2.put(DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_LONG, longstr);
                    values2.put(DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_Alt, latstr);
                    values2.put(DatabaseContract.ProjectData.COLUMN_NAME_NOTES, textCapt.getText().toString());
                    values2.put(DatabaseContract.ProjectData.COLUMN_PROJECTINFO_ID, iDfromRecyclerView); // Insert the generated Row Here.

                    long finalEval = db.insert(DatabaseContract.ProjectData.TABLE_NAME_DATA, null, values2);

                } else {
                    if (locEnabled == true) { //global var acting as a locEnabled.
                        getLocations();
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        latstr = Double.toString(location.getLatitude());
                        longstr = Double.toString(location.getLongitude());


                        System.out.println(latstr);
                        System.out.println(longstr);

                        ContentValues values = new ContentValues();
                        values.put(DatabaseContract.ProjectInfoTable.COLUMN_NAME_PROJECTNAME, getProjectName);
                        values.put(DatabaseContract.ProjectInfoTable.COLUMN_NAME_LOCATIONTEXT, locationTest);
                        values.put(DatabaseContract.ProjectInfoTable.COLUMN_NAME_SUBJECT, projecetSubjectText);
                        values.put(DatabaseContract.ProjectInfoTable.COLUMN_NAME_GROUPMEMBERS, GroupMembers);

                        //Get Generated Key.
                        generatedRow = db.insert(TABLE_NAME_PROJECT, null, values); //insert values into the database via values, map relation.

                        ContentValues values2 = new ContentValues();
                        values2.put(DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_LONG, longstr);
                        values2.put(DatabaseContract.ProjectData.COLUMN_NAME_LOCATION_Alt, latstr);
                        values2.put(DatabaseContract.ProjectData.COLUMN_NAME_NOTES, textCapt.getText().toString());
                        values2.put(DatabaseContract.ProjectData.COLUMN_PROJECTINFO_ID, generatedRow); // Insert the generated Row Here.


                        long finalEval = db.insert(DatabaseContract.ProjectData.TABLE_NAME_DATA, null, values2);

                        if (finalEval > 0) {
                            Toast.makeText(getApplicationContext(), "Location Saved", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Location could not be saved, please check your location settings.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void onRequestPermissionsResults(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLocations();
                return;
        }
    }

    private void getLocations() {
        locEnabled = true;
        locationManager.requestLocationUpdates("gps", 0, 0, locationlistener); //Really unsure what I am supposed to do here, any ideas? I would like to support lower API'S but found it difficult to find the right soluition for this. Thank you :)
    }
}


