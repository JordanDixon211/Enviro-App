package com.example.jordan.enviroapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jordan.enviroapp.R;


public class NewProjectForm extends AppCompatActivity implements TextWatcher {
    private Button createProjectBtn;
    private Button cancelProjectBtn;
    private EditText projectNameText;
    private EditText projectSubjectText;
    private EditText locationText;
    private EditText groupMembersText;

    // How you want the results sorted in the resulting Cursor


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_project_form);

        createProjectBtn = (Button) findViewById(R.id.SubmitBtn);
        cancelProjectBtn = (Button) findViewById(R.id.CancelBtn);

        projectNameText = (EditText) findViewById(R.id.ProjectNameText);
        projectSubjectText = (EditText) findViewById(R.id.ProjectSubjectText);
        locationText = (EditText) findViewById(R.id.LocationText);
        groupMembersText = (EditText) findViewById(R.id.GroupMembersText);

        createProjectBtn.setEnabled(false);
        //////////////////// adding listeners for the text detection ////////////////////////////
        projectNameText.addTextChangedListener(this);
        projectSubjectText.addTextChangedListener(this);
        locationText.addTextChangedListener(this);

        createProjectBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                /*process these values
                into another Class, mainipulate the objecet via this class.
                 */
                Intent intent = new Intent(NewProjectForm.this, RecordLocations.class);
                Bundle bundle = new Bundle();
                bundle.putString("projectNameText",  projectNameText.getText().toString());
                bundle.putString("locationText", locationText.getText().toString());
                bundle.putString("projectSubjectText", projectSubjectText.getText().toString());
                bundle.putString("groupMembersText", groupMembersText.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        cancelProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewProjectForm.this, MainActivity.class));

            }
        });
    }

    private boolean emptyTextCheck() {
        if (!projectNameText.getText().toString().trim().isEmpty() && !projectSubjectText.getText().toString().trim().isEmpty() && !locationText.getText().toString().trim().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        boolean setEnabledCheck = emptyTextCheck();
        createProjectBtn.setEnabled(setEnabledCheck);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean setEnabledCheck = emptyTextCheck();
        createProjectBtn.setEnabled(setEnabledCheck);
    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean setEnabledCheck = emptyTextCheck();
        createProjectBtn.setEnabled(setEnabledCheck);
    }


}



