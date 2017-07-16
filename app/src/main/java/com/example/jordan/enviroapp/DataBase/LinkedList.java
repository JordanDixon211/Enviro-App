package com.example.jordan.enviroapp.DataBase;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Jordan on 15/07/2017.
 Created for educational purposes, also for use in this Project

 */
public class LinkedList <T> {
    private ListNode<T> root = null;
    private int size = 0;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (root != null) {
            return false;
        } else {
            return true;
        }
    }


    public boolean contains(T o) {
        if (root == null){
            return false;
        }
        if (o != null) {
            if (root == o) {
                return true;
            }

            ListNode node = (Node) root.rightLink();
            while (node != null) {
                if (node == o) {
                    return true;
                } else {
                    node = node.rightLink();
                }
            }
        }
        return false;
    }


    public boolean add(T o) {
        if (o != null && contains(o) == false) {
            if (root == null) {
                root = new Node(o);
                size++;
                return true;
            }

            ListNode node = root;
            while (node.rightLink() != null) {
                node = node.rightLink();
            }

            ListNode<T> newNode = new Node(o);
            node.setRight(newNode);
            newNode.setLeft(node);
            size++;

            return true;
        }
        return false;
    }

    public boolean remove(T o) {
        if (!isEmpty() && o != null) {
            ListNode removeNode = new Node(o);
                if (root.getData() == removeNode.getData()) {
                    root = root.rightLink();
                    root.setLeft(null);
                    size--;
                    return true;

                } else {
                    ListNode node = root.rightLink();
                    while (node != null) {
                        if (node.getData() == removeNode.getData()) ;
                        node.leftLink().setRight(node.rightLink());
                        if (node.rightLink() != null) {
                            node.rightLink().setLeft(node.leftLink());
                        }
                        size--;
                        return true;
                    }
                }
            }
        return false;
    }

    public T get(int index) {
        if (size() > index){
            if (index == 0){
                return root.getData();
            }else {
                ListNode<T> node = root.rightLink();
                int count = 1;
                while (count <= index){
                    if (count != index){
                        node = node.rightLink();
                        count++;
                    }else {
                        return node.getData();
                    }
                }
            }
        }
        return null;
    }

}
