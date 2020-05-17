package com.codificador.contactapp;

import java.io.Serializable;

/**
 * Created by Seng on 11/17/2017.
 */

public class ListItem implements Serializable {

    private String name;
    private int value;
    private long id;
    private int seekbar_max;
    private int seekbar_min;


    public void setSeekbar_max(int seekbar_max) {
        this.seekbar_max = seekbar_max;
    }


    public void setSeekbar_min(int seekbar_min) {
        this.seekbar_min = seekbar_min;
    }

    public int getSeekbar_max() {
        return seekbar_max;
    }

    public int getSeekbar_min() {
        return seekbar_min;
    }

    public ListItem(String name, int value, int seekbar_max, int seekbar_min) {
        this.name = name;
        this.value = value;
        this.seekbar_max = seekbar_max;
        this.seekbar_min = seekbar_min;
    }

    public ListItem(long id, String name, int value, int seekbar_max, int seekbar_min) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.seekbar_max = seekbar_max;
        this.seekbar_min = seekbar_min;
    }

    public ListItem(long id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.seekbar_min = 0;
        this.seekbar_max = 100;
    }

    public ListItem(String name, int value) {
        id = 0;
        this.name = name;
        this.value = value;
    }

    public ListItem(String name) {
        id = 0;
        this.name = name;
        this.value = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void autoAdjust() {
        this.value++;
        if (this.value == this.seekbar_max) {
            this.value = this.seekbar_min;
        }


    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
