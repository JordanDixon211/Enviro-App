package com.example.jordan.enviroapp.RecycleViews;


import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class ProjectDataStruct implements Comparable<ProjectDataStruct> {
    private int id;
    private String longatiude;
    private String latiude;
    private String notes;
    private int projectId;
    String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongatiude() {
        return longatiude;
    }

    public void setLongatiude(String longatiude) {
        this.longatiude = longatiude;
    }

    public String getLatiude() {
        return latiude;
    }

    public void setLatiude(String latiude) {
        this.latiude = latiude;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int compareTo(@NonNull ProjectDataStruct o) {
        if (this.getId() < o.getId()){
            return -1;
        }else if(this.getId() > o.getId()){
            return 1;
        }else {
            return 0;
        }
    }

    public void sortList(List unsortedList){
         Collections.sort(unsortedList);
    }

}
