package com.company.cw;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        //creating an object arraylist of Formula1Driver
        ArrayList<Formula1Driver> formula1Drivers= new ArrayList<>();
        //creating an object for the Formula1ChampionshipManager class
        Formula1ChampionshipManager formula1ChampionshipManager=new Formula1ChampionshipManager();
        //creating an object arraylist of Races
        ArrayList<Race> races=new ArrayList<>();
        formula1ChampionshipManager.menu(formula1Drivers,races);

    }
}
