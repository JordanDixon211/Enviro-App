package com.example.jordan.enviroapp.DataBase;

import com.example.jordan.enviroapp.DataBase.LinkedList;
import com.example.jordan.enviroapp.RecycleViews.ProjectDataStruct;

/**
 * Created by Jordan on 15/07/2017.
 */

public class Test {
    public static void main(String[] args){
        LinkedList<ProjectDataStruct> c = new LinkedList();
        ProjectDataStruct project = new ProjectDataStruct();
        project.setId(1);
        c.add(project);


    }
}
