package com.example.jordan.enviroapp.DataBase;

/**
 * Created by Jordan on 15/07/2017.
 */

public class Node extends ListNode {
    /*Implementation of the ListNode, Interals of the node. */
    public Node(Object data) {
        super(data);
    }

    @Override
    public ListNode rightLink() {
        return this.right;
    }

    @Override
    public ListNode leftLink() {
        return this.left;
    }

    @Override
    public void setRight(ListNode right) {
        this.right = right;
    }

    @Override
    public void setLeft(ListNode left) {
        this.left = left;
    }

}
