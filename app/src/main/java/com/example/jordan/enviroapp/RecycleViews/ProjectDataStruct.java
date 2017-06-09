package com.example.jordan.enviroapp.RecycleViews;


public class ProjectDataStruct {
    private int id;
    private String longatiude;
    private String latiude;
    private String notes;
    private int projectId;

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
}
