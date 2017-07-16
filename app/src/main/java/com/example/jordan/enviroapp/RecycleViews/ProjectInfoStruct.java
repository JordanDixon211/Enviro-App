package com.example.jordan.enviroapp.RecycleViews;


public class ProjectInfoStruct {
    private int id;
    private String ProjectName;
    private String locationText;
    private String subject;
    private String groupMembers;
    private String FilePath;
    private int idFilePath;

    public int getIdFilePath() {
        return idFilePath;
    }

    /*
    * Id relative to the path, this is the ID assoisated with this picture.
    * */
    public void setIdFilePath(int idFilePath) {
        this.idFilePath = idFilePath;
    }

    public String getFilePath() {
        return FilePath;
    }

    /*
    * Where current Record is Saved in the  directory
    * */
    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }
}
