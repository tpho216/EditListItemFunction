package com.codificador.contactapp;

import java.io.Serializable;

/**
 * Created by Seng on 11/17/2017.
 */

public class ListItem implements Serializable{

    private String name;
    private String number;
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

    public ListItem(String name, String number, long id, int seekbar_max, int seekbar_min) {
        this.name = name;
        this.number = number;
        this.id = id;
        this.seekbar_max = seekbar_max;
        this.seekbar_min = seekbar_min;
    }

    public ListItem(long id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public ListItem(String name, String number) {
        id = 0;
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
