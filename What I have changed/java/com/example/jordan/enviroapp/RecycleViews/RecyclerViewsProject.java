package com.example.jordan.enviroapp.RecycleViews;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.jordan.enviroapp.DataBase.DatabaseContract;
import com.example.jordan.enviroapp.DataBase.ProjectInfoReaderDbHelper;
import com.example.jordan.enviroapp.NewProjectForm;
import com.example.jordan.enviroapp.R;
import com.example.jordan.enviroapp.RecordLocations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class RecyclerViewsProject extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SQLiteDatabase db;
    private List<ProjectInfoStruct> adapterDataProjectInfo;
    private List<ProjectDataStruct> adapterDataProjectData;

    String[] projection = {
            DatabaseContract.ProjectInfoTable.COLUMN_NAME_PROJECTNAME
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new ProjectInfoReaderDbHelper(this).getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_views_project);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        adapterDataProjectInfo = getAllInfoData();
        adapterDataProjectData = getAllProjectData();


        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(15));


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAdapter(adapterDataProjectInfo, new MyAdapter.OnItemClickListener() {
              public void onItemClick(ProjectInfoStruct item) {
                  System.out.println("item Selected:  " + item.getProjectName());

                  Intent intent = new Intent(RecyclerViewsProject.this, RecyclerViewsProjectData.class);
                  Bundle bundle = new Bundle();
                  intent.putExtra("itemId" , item.getId()); //location to search for in this arrayList.
                  intent.putExtras(bundle);
                  startActivity(intent);

              }
        }));


    }


    /**
    * Project queries fill up the arrays below, to then be sent to the recycler view.
    * */

    private List<ProjectInfoStruct> getAllInfoData(){
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

    /**
     * Project queries fill up the arrays below, to then be sent to the recycler view.
     * */

    private List<ProjectDataStruct> getAllProjectData(){
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

                projectDataList.add(projectDataStruct);
            }while (cursor.moveToNext());
        }
        return projectDataList;
    }


}

