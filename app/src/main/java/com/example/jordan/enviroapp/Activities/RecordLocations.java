package com.example.jordan.enviroapp.Activities;


import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.jordan.enviroapp.DataBase.DatabaseContract;
import com.example.jordan.enviroapp.DataBase.LinkedList;
import com.example.jordan.enviroapp.DataBase.ProjectInfoReaderDbHelper;
import com.example.jordan.enviroapp.R;
import com.example.jordan.enviroapp.RecycleViews.ProjectDataStruct;
import com.example.jordan.enviroapp.RecycleViews.ProjectInfoStruct;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.jordan.enviroapp.DataBase.DatabaseContract.ProjectData.TABLE_NAME_DATA;
import static com.example.jordan.enviroapp.DataBase.DatabaseContract.ProjectInfoTable.TABLE_NAME_PROJECT;

public class RecordLocations extends AppCompatActivity {
    private Button recordBtn;
    private TextView textCapt;
    private ToggleButton toggleButtonCamera;
    private ImageView pictureTaken;
    private SQLiteDatabase db;
    String getProjectName, locationTest, projecetSubjectText, GroupMembers, latstr, longstr;
    private LocationManager locationManager;
    private LocationListener locationlistener;
    Bitmap bitmapImage;
    boolean locEnabled; //Global Var, acting as a kind of out proc
    int iDfromRecyclerView;
    String mCurrentPhotoPath;

    private long projectIdFileName, projectDataIdFileName;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_locations);

        db = new ProjectInfoReaderDbHelper(this).getWritableDatabase();

        projectIdFileName = 0;
        projectDataIdFileName = 0;

        recordBtn = (Button) findViewById(R.id.btncapture);
        textCapt = (TextView) findViewById(R.id.addNotes);
        toggleButtonCamera = (ToggleButton) findViewById(R.id.Toggle);
        pictureTaken = (ImageView) findViewById(R.id.imageView);

        iDfromRecyclerView = 0;
        //get the id from the recycler View, check's if the incoming activity is from a previous project
        getBundlerProjectId();
        Bundle bundle = getIntent().getExtras();
        iDfromRecyclerView = bundle.getInt("ProjectId");

        System.out.println( "Selected Id: " + iDfromRecyclerView);

        toggleButtonCamera.setTextOff("No");
        toggleButtonCamera.setTextOn("Yes");

        toggleButtonCamera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && iDfromRecyclerView > 0) {
                    dispatchTakePictureIntent(iDfromRecyclerView, ProjectInfoReaderDbHelper.getAllProjectData(db) + 1); // will be the new Id
                } else if (isChecked && iDfromRecyclerView == 0){
                    dispatchTakePictureIntent(ProjectInfoReaderDbHelper.getAllProjects(db) + 1, ProjectInfoReaderDbHelper.getAllProjectData(db) + 1);
                }else if (!isChecked){
                    bitmapImage = null;
                }
            }
        });

        /* Location Manager gives the instance
        to be able to track the users location.
        * */

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationlistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double longitude = location.getLongitude();
                double lat = location.getLatitude();
                latstr = Double.toString(lat);
                longstr = Double.toString(longitude);
                //      Toast.makeText(getApplicationContext(),"Latitude " + latstr +  "  Longitude: "  + longitude, Toast.LENGTH_LONG).show(); Change here to display once data has been saved.
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


        /**
         * Capture Data held within the NewProjectForm, implemented here to insert The Generated Key with the record.
         */
        getBundlerNewProject();


        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        /* Permission check */

                if (ActivityCompat.checkSelfPermission(RecordLocations.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RecordLocations.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);

                    return;
                } else {
                    //cont conf
                    getLocations();
                }

                if (iDfromRecyclerView > 0) {
                    getLocations();
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        latstr = Double.toString(location.getLatitude());
                        longstr = Double.toString(location.getLongitude());

                    }
                    //insert Project data for an old project
                    long finalEval = ProjectInfoReaderDbHelper.insertProjectDataOld(iDfromRecyclerView, latstr, longstr, mCurrentPhotoPath, textCapt, db);
                    projectIdFileName = iDfromRecyclerView;
                    projectDataIdFileName = finalEval;
                    Toast.makeText(getApplicationContext(), "Location Saved", Toast.LENGTH_LONG).show();
                    System.out.println("Actual Id Produced: " + finalEval);
                    db.close();


                } else {
                    if (locEnabled) { //global var acting as a locEnabled.
                        getLocations();
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        latstr = Double.toString(location.getLatitude());
                        longstr = Double.toString(location.getLongitude());
                        //InsertNewProject
                        long newprojectId = ProjectInfoReaderDbHelper.insertNewProject(getProjectName, locationTest, projecetSubjectText, GroupMembers, db);
                        projectIdFileName = newprojectId;
                        //inter new Project data for the new Project
                        long finalEval = ProjectInfoReaderDbHelper.insertProjectData(newprojectId, latstr, longstr, mCurrentPhotoPath, textCapt, db);
                        projectDataIdFileName = finalEval;
                        System.out.println("Actual Id Produced: " + finalEval);
                        db.close();
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

    /**
     * Checks current permissions, if we are allowed to track the user
     * or not. Pretty essential, to the app that the information is required.
     */


    public void onRequestPermissionsResults(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLocations();
                return;
        }
    }

    /*
    * gets the current Location of the user
    * */
    private void getLocations() {
        locEnabled = true;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 0, 0, locationlistener); //Really unsure what I am supposed to do here, any ideas? I would like to support lower API'S but found it difficult to find the right soluition for this. Thank you :)
    }


    /*
    * get data from the NewProjectForm activity
    * */
    private void getBundlerNewProject(){
        Bundle bundle = getIntent().getExtras();
        getProjectName = bundle.getString("projectNameText");
        locationTest = bundle.getString("locationText");
        projecetSubjectText = bundle.getString("projectSubjectText");
        GroupMembers = bundle.getString("groupMembersText");
    }

    /*
    * get ID if the user would like to add more data to a project.
    * */
    private void getBundlerProjectId(){
        Bundle bundle = getIntent().getExtras();
        iDfromRecyclerView = bundle.getInt("ProjectId");
    }


    /*
    * Intent, decribes an action that needs to be taken.
    * Method takes a photo and resumes the current activity. */
    private static int REQUEST_IMAGE_CAPTURE  = 1;
    private void dispatchTakePictureIntent(long projectId, long projecetIdData){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //ensure camera activity to handle intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            //create File where photo should go
            File photoFile = null;

            try{
                photoFile = createImageFile(projectId, projecetIdData);
            }catch (IOException e){
                System.out.println(e.fillInStackTrace());
            }
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, "com.example.jordan.enviroapp.FileProvider", photoFile);
                System.out.println("Photo uri: " + photoUri.toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                testSetPhoto();
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                testSetPhoto();
            }
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            testSetPhoto();

        }
    }


    //save member variable for later use, returns unique file name for a new photo
    private File createImageFile(long projectId, long projectDataId) throws IOException {
        String filename = String.valueOf(projectId) + String.valueOf(projectDataId); //concat the two values to define a unique filename, eg if id = 1 and projectid = 5 filename = 15.
        String imageFileName = "JPEG_" + filename;
        System.out.println("ProjectId: " + projectId + " ProjectDataId: " + projectDataId);
        System.out.println("filename: " + filename);

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        System.out.println(storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    /*
    * Testing if this sets a photo via path,*/
    public void testSetPhoto(){
        File file = new File(mCurrentPhotoPath);
        if (file.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(file.toString());
            System.out.println(file.toString());
            pictureTaken.setImageBitmap(myBitmap);
            pictureTaken.setBackgroundColor(Color.RED);
        }
    }



}


