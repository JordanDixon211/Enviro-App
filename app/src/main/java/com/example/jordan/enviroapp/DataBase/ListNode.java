package com.example.jordan.enviroapp.DataBase;

import com.example.jordan.enviroapp.RecycleViews.ProjectDataStruct;
import com.example.jordan.enviroapp.RecycleViews.ProjectStructAbs;

/*Own Implementation of a Linked List for faster insertion and faster delection, also for pratical purposes*/

/*Extends the abstract class ProjectStruct to enable usable of subclasses ProjectDataStruct and ProjectInfoStruct*/

abstract class ListNode<T> {
    protected ListNode left = null;
    protected ListNode right = null;
    private T data;

    public ListNode(T data){
        this.data = data;
    }

    public abstract ListNode rightLink();
    public abstract ListNode leftLink();
    public abstract void setRight(ListNode right);
    public abstract void setLeft(ListNode left);

    public T getData(){
        return data;
    }
}
