package com.example.jordan.enviroapp.RecycleViews;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jordan.enviroapp.Activities.RecordLocations;
import com.example.jordan.enviroapp.DataBase.ProjectInfoReaderDbHelper;
import com.example.jordan.enviroapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 12/05/2017.
 */
public class RecyclerViewsProjectData extends Activity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<ProjectDataStruct> projecetDataStruct;
    List<ProjectInfoStruct> projectinfoStruct;
    private int id;
    private TextView projectName;
    private TextView location;
    private Button button;
    private ProjectInfoReaderDbHelper projectInfoReaderDbHelper;
    private SQLiteDatabase db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_project_data);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        projectName =  (TextView)findViewById(R.id.projectName);
        location =  (TextView)findViewById(R.id.LocationText);
        button = (Button)findViewById(R.id.addMore);
        db = new ProjectInfoReaderDbHelper(this).getWritableDatabase();

        //Capture Data.
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("itemId");

        projectinfoStruct = getAllInfoData();
        projecetDataStruct = getAllProjectData();

        if(id > 0) {
            id = id - 1;
           projectName.setText("Project Name: " + projectinfoStruct.get(id).getProjectName());
            location.setText("Project Location: " + projectinfoStruct.get(id).getLocationText());



            /**List Object gets passed into the
             * adapter as the
             * dataset for this view*/

            List<ProjectDataStruct> results = searchForItemInfo(id + 1);
            mRecyclerView.setHasFixedSize(true);

            mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(15));


            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView.setAdapter(new MyAdapterData(results, new MyAdapterData.OnItemClickListener() {
                public void onItemClick(ProjectDataStruct item) {
                    System.out.println("item Selected:  " + item.getId());

                }
            }));


        }else{
            System.out.println("Could not find resource");
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(RecyclerViewsProjectData.this, RecordLocations.class);
                Bundle bundle = new Bundle();
                System.out.println(id);
                bundle.putInt("ProjectId",  id + 1);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }


    /**
     * Query List's
     * */

    public List<ProjectDataStruct> searchForItemInfo(int ProjectId){
        List<ProjectDataStruct> searchItem = new ArrayList<ProjectDataStruct>();
        int count = 0;
        for (int i = 0; i < projecetDataStruct.size(); i++){
            if (projecetDataStruct.get(i).getProjectId() == ProjectId){
                searchItem.add(projecetDataStruct.get(i));
            }
        }
        return searchItem;
    }

    public List<ProjectInfoStruct> getAllInfoData(){
        List<ProjectInfoStruct> projecetInfoList = new ArrayList<ProjectInfoStruct>();
        //Generating query.
        Cursor cursor = db.rawQuery(ProjectInfoReaderDbHelper.SQL_PROJECT_INFO_QUERY, null);

        if (cursor.moveToFirst()){
            do{
                ProjectInfoStruct projectInfoStruct = new ProjectInfoStruct();
                projectInfoStruct.setId(cursor.getInt(0));
                projectInfoStruct.setProjectName(cursor.getString(1));
                projectInfoStruct.setLocationText(cursor.getString(2));
                projectInfoStruct.setSubject(cursor.getString(3));
                projectInfoStruct.setGroupMembers(cursor.getString(4));

                projecetInfoList.add(projectInfoStruct);
            }while (cursor.moveToNext());
        }
        return projecetInfoList;
    }

    public List<ProjectDataStruct> getAllProjectData(){
        List<ProjectDataStruct> projectDataList = new ArrayList<ProjectDataStruct>();
        //Generating query.
        Cursor cursor = db.rawQuery(ProjectInfoReaderDbHelper.SQL_PROJECT_DATA_QUERY, null);

        if (cursor.moveToFirst()){
            do{
                ProjectDataStruct projectDataStruct = new ProjectDataStruct();
                projectDataStruct.setId(cursor.getInt(0));
                projectDataStruct.setLongatiude(cursor.getString(1));
                projectDataStruct.setLatiude(cursor.getString(2));
                projectDataStruct.setNotes(cursor.getString(3));
                projectDataStruct.setProjectId(cursor.getInt(4));
                projectDataStruct.setFilePath(cursor.getString(5));

                projectDataList.add(projectDataStruct);
            }while (cursor.moveToNext());
        }
        return projectDataList;
    }
}
