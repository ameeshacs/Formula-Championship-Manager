package com.company.cw;

import java.io.Serializable;
import java.util.ArrayList;

//class for races
public class Race implements Serializable {
    private static final long serialVersionUID=1L;
    private String date;
    private ArrayList<String> positions;

    //constructor for races
    public Race(String date,ArrayList<String> positions){
        this.date=date;
        this.positions=positions;
    }

    //getter for date
    public String getDate(){
        return date;
    }

    //setter for date
    public void setDate(String date){
        this.date=date;
    }

    //getter for all positions of a race
    public ArrayList<String> getPositions(){
        return positions;
    }

    //setter for all positions of a race
    public void setPositions(ArrayList<String> positions){
        this.positions=positions;
    }
}
