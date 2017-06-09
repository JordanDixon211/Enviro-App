package com.example.jordan.enviroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jordan.enviroapp.RecycleViews.RecyclerViewsProject;

public class MainActivity extends AppCompatActivity {
    private Button newProjectBtn;
    private Button previousProjectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newProjectBtn = (Button)findViewById(R.id.NewProjectBtnMain);
        previousProjectBtn = (Button)findViewById(R.id.PreviousProjectBtn);

        newProjectBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, NewProjectForm.class));
            }
        });

        previousProjectBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, RecyclerViewsProject.class));
            }
        });
    }
}
