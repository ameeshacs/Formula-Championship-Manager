package com.company.cw;

import java.io.Serializable;

//abstract class of Driver
abstract class Driver implements Serializable {
    private static final long serialVersionUID=1L;
    public String driverName;
    public String location;
    public String constructorTeamName;

    //constructor for class Driver with parameters
    public Driver(String driver_Name, String constructor_TeamName, String location){
        this.driverName=driver_Name;
        this.constructorTeamName=constructor_TeamName;
        this.location=location;
    }

    //constructor for class Driver without parameters
    public Driver(){

    }

    //setter for driver name
    public void setDriverName(String driverName){
        this.driverName=driverName;
    }

    //setter for location
    public void setLocation(String location){
        this.location=location;
    }

    //setter for constructor team
    public void setConstructorTeamName(String constructorTeamName){
        this.constructorTeamName=constructorTeamName;
    }

    //getter for driver name
    public String getDriverName(){
        return driverName;
    }

    //getter for location
    public String getLocation(){
        return location;
    }

    //getter for constructor team
    public String getConstructorTeam(){
        return constructorTeamName;
    }

}
