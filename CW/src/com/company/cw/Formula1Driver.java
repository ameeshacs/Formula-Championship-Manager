package com.company.cw;

import java.io.Serializable;
//import java.util.Comparator;
import java.util.Scanner;

//Formula1Driver class
public class Formula1Driver extends Driver implements Serializable {
    private static final long serialVersionUID=1L;
    public int[] pointsTable = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};
    private int points;
    private final int[] matchPlaces = new int[10];
    private int firstPlaces, secondPlaces, thirdPlaces;
    private int racesNo;


    public Formula1Driver(String driver_Name, String constructor_TeamName, String location, int racesNo, int firstPlaces, int secondPlaces, int thirdPlaces, int points) {
        super(driver_Name, constructor_TeamName, location);
        this.firstPlaces=firstPlaces;
        this.secondPlaces=secondPlaces;
        this.thirdPlaces=thirdPlaces;
        this.points = points;
        this.racesNo=racesNo;
    }

    public Formula1Driver(){

    }


    //method to calculate all the points and  manage the statistics
    public void statistics() {
        points=0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of races the driver has participated this season:");
        racesNo=sc.nextInt();
        for (int i = 0; i < 10; i++) {
            System.out.println("Enter the number of times the driver got" + (i + 1));
            matchPlaces[i] = sc.nextInt();
            points = points + (matchPlaces[i] * pointsTable[i]);
        }

        firstPlaces=matchPlaces[0];
        secondPlaces=matchPlaces[1];
        thirdPlaces=matchPlaces[2];

    }

    //setter for number of races
    public void setRacesNo(int racesNo){
        this.racesNo=racesNo;
    }

    //getter for number of races
    public int getRacesNo(){
        return racesNo;
    }

    //getter for number of points
    public int getPoints(){
        return points;
    }

    //setter for number of points
    public void setPoints(int points){
        this.points=points;
    }

    //getter for number of first places
    public int getFirstPlaces(){
        return firstPlaces;
    }

    //setter for number of first places
    public void setFirstPlaces(int firstPlaces){

        this.firstPlaces=firstPlaces;
    }

    //getter for number of second places
    public int getSecondPlaces(){
        return secondPlaces;
    }

    //setter for number of second places
    public void setSecondPlaces(int secondPlaces){

        this.secondPlaces=secondPlaces;
    }

    //getter for number of third places
    public int getThirdPlaces(){
        return thirdPlaces;
    }

    //setter for number of third places
    public void setThirdPlaces(int thirdPlaces){

        this.thirdPlaces=thirdPlaces;
    }

    //toString to print the details of drivers
    public String toString() {
        return"[ driver name ="+driverName+ " ,constructor team name " +constructorTeamName +" ,points " + points+"]";
    }


}
