package com.company.cw;

import java.io.*;
import java.util.*;


public class Formula1ChampionshipManager implements ChampionshipManager {
    Formula1Driver driver = new Formula1Driver();

    //text menu method
    public void menu(ArrayList<Formula1Driver> formula1Drivers,ArrayList<Race> races) {
        loadDriverData(formula1Drivers);
        loadRaceData(races);
        Scanner sc = new Scanner(System.in);

        try {
            boolean label = true;
            while (label) {
                System.out.println("\n Enter your desired number:" +
                        "\n 1 to add a driver " +
                        "\n 2 to delete a driver " +
                        "\n 3 to change the driver " +
                        "\n 4 to display statistics" +
                        "\n 5 to add a race" +
                        "\n 6 to Display Formula 1 driver table " +
                        "\n 7 to save data " +
                        "\n 8 to display the GUI " +
                        "\n 9 to close the program ");
                int menuInput = sc.nextInt();
                System.out.println();

                //switch case for each menu item
                switch (menuInput) {
                    case 1:
                        addDriver(formula1Drivers);
                        break;
                    case 2:
                        deleteDriver(formula1Drivers);
                        break;
                    case 3:
                        replaceDriver(formula1Drivers);
                        break;
                    case 4:
                        displayStatistics(formula1Drivers);
                        break;
                    case 5:
                        addRace(formula1Drivers,races);
                        break;
                    case 6:
                        sortPoints(formula1Drivers);
                        break;
                    case 7:
                        saveDriverData(formula1Drivers);
                        saveRaceData(races);
                        break;
                    case 8:
                        new SwingTester(formula1Drivers,races);
                        label = false;
                        break;
                    case 9:
                        label = false;
                        break;
                    default:
                        System.out.println("Incorrect input");
                        break;
                }//switch
            }//while

            //exception call
        } catch (Exception e) {
            System.out.println("An error has occurred");
        }

    }//menu

    @Override
    //method to add drivers and their statistics so far in the season
    public void addDriver(ArrayList<Formula1Driver> formula1Drivers) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the driver's name");
        String driver_Name = sc.next();
        System.out.println("The driver is added");

        System.out.println("Enter the name of the constructor team");
        String constructor_TeamName=sc.next();

            for (int i = 0; i < formula1Drivers.size(); i++) {
                //check if the constructor team is already registered
                if ( formula1Drivers.get(i).getConstructorTeam().equals(constructor_TeamName)) {
                    System.out.println("Enter a new constructor team. The previous constructor team is already taken");
                    constructor_TeamName=sc.next();
                }
                else{
                    break;
                }
            }

        System.out.println("Enter the name of the location");
        String location = sc.next();

        //statistic method from the driver class is called
        driver.statistics();
        //all the details are added to a new object arraylist
        formula1Drivers.add(new Formula1Driver(driver_Name, constructor_TeamName, location, driver.getRacesNo(), driver.getFirstPlaces(), driver.getSecondPlaces(), driver.getThirdPlaces(), driver.getPoints()));

    }

    @Override
    //delete a driver from the added drivers
    public void deleteDriver(ArrayList<Formula1Driver> formula1Drivers) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the driver you want to delete:");
        String deleteName = sc.next();

        for (int i = 0; i < formula1Drivers.size(); i++) {
            //check whether the name to be deleted contains in the already added drivers list
            if (formula1Drivers.get(i) != null && formula1Drivers.get(i).getDriverName().equals(deleteName)) {
                formula1Drivers.remove(i);
                System.out.println("The driver and the relevant constructor team is deleted");//confirmation text
                break;
            }
            //if the name to be deleted is not in the added driver list
            if (i == formula1Drivers.size() - 1) {
                System.out.println("That requested person is not registered to the competition");
            }
        }


    }

    //method to replace the driver with a new driver
    @Override
    public void replaceDriver(ArrayList<Formula1Driver> formula1Drivers) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the constructor team of the driver you want to replace:");
        String replacedTeamName = sc.next();
        System.out.println("Enter the name of the new driver:");
        String newName = sc.next();

        //the name of the new driver replace the old driver of the user given constructor name is in the arraylist
        for (int i = 0; i < formula1Drivers.size(); i++) {
            if (formula1Drivers.get(i).getConstructorTeam().equals(replacedTeamName)) {
                formula1Drivers.get(i).setDriverName(newName);
                System.out.println(newName + " is added to the team " + replacedTeamName);//confirmation text
                break;
            }
            //if the team name to be deleted is incorrect
            if(i==formula1Drivers.size()-1){
                System.out.println("The constructor team is not registered");
            }
        }


    }

    //display all the statistics of a driver of choice
    @Override
    public void displayStatistics(ArrayList<Formula1Driver> formula1Drivers) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the driver you want to find the statistics of:");
        String driverStatName = sc.next();

        //display all the statistics if the driver name is equal to the user given name
        for (int i = 0; i < formula1Drivers.size(); i++) {
            if (formula1Drivers.get(i).getDriverName().equals(driverStatName)) {
                System.out.println("Points of the driver:" + formula1Drivers.get(i).getPoints());
                System.out.println("\n Top three positions: "+
                        "\n first place " + formula1Drivers.get(i).getFirstPlaces()+
                        "\n second place "+ formula1Drivers.get(i).getSecondPlaces()+
                        "\n third place "+ formula1Drivers.get(i).getThirdPlaces());
                break;
            }
            //if the driver name entered is incorrect
            if(i==formula1Drivers.size()-1){
                System.out.println("The driver name entered is incorrect.");
            }

        }
    }

    //a race of users choice is added with positions
    @Override
    public void addRace(ArrayList<Formula1Driver> formula1Drivers,ArrayList<Race> races){
        ArrayList<String> newRacePositions = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the date of the race in the format of dd.MM.yyyy");
        String date = sc.next();

        for (int i = 0; i < formula1Drivers.size(); i++) {
            //name of the user given driver is added to the arraylist
            System.out.println("Enter the name of the driver got position " + (i+1));
            newRacePositions.add(sc.next());
        }


        for (int i = 0; i < formula1Drivers.size(); i++) {
            for(int j=0;j < newRacePositions.size();j++) {
                if (formula1Drivers.get(i).getDriverName().equals(newRacePositions.get(j))) {
                    int newPoints = formula1Drivers.get(i).getPoints() + driver.pointsTable[j];
                    formula1Drivers.get(i).setPoints(newPoints);//all the points are updated
                    int newRaceNo=formula1Drivers.get(i).getRacesNo()+1;
                    formula1Drivers.get(i).setRacesNo(newRaceNo);
                    if (i == 0) {
                        //number of first places are updated if the user has won in first place
                        int newFirstPlace = formula1Drivers.get(i).getFirstPlaces() + 1;
                        formula1Drivers.get(i).setFirstPlaces(newFirstPlace);
                    } else if (i == 1) {
                        //number of second places are updated if the user has won in second place
                        int newSecondPlace = formula1Drivers.get(i).getSecondPlaces() + 1;
                        formula1Drivers.get(i).setSecondPlaces(newSecondPlace);
                    }

                    //number of third places are updated if the user has won in third place
                    else if (i == 2) {
                        int newThirdPlace = formula1Drivers.get(i).getThirdPlaces() + 1;
                        formula1Drivers.get(i).setThirdPlaces(newThirdPlace);
                    }
                }
            }
        }

        //race position array list is added to arraylist which contains all the races
        races.add(new Race(date,newRacePositions));
        System.out.println("New race is added");
    }


    //method to save all the driver data that is entered
    @Override
    public void saveDriverData(ArrayList<Formula1Driver> formula1Drivers){
        try{
            File file=new File("driver.txt");
            FileOutputStream fileOut=new FileOutputStream(file);
            ObjectOutputStream objectOut=new ObjectOutputStream(fileOut);// Connect the FileWriter to the BufferedWriter

            //save each driver details
            for (int i=0;i<formula1Drivers.size();i++) {
                objectOut.writeObject(formula1Drivers.get(i));
            }

            System.out.println("Driver data is saved");
            objectOut.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method to load the driver data that is saved
    public void loadDriverData(ArrayList<Formula1Driver> formula1Drivers){
        File fileNew=new File("driver.txt");
        //checking whether data is available to load
        if (fileNew.length() == 0) {
            System.out.println("Driver data is not available to load\n");
        }
        else {
            try {
                FileInputStream fileIn = new FileInputStream("driver.txt");
                ObjectInputStream objIn = new ObjectInputStream(fileIn);

                //read each driver details and add it to the object arraylist
                while (true) {
                    try {
                        Formula1Driver formula1Driver1 = (Formula1Driver) objIn.readObject();
                        formula1Drivers.add(new Formula1Driver(formula1Driver1.getDriverName(),formula1Driver1.getConstructorTeam(),formula1Driver1.getLocation(),formula1Driver1.getRacesNo(),formula1Driver1.getFirstPlaces(),formula1Driver1.getSecondPlaces(),formula1Driver1.getThirdPlaces(),formula1Driver1.getPoints()));

                    } catch (IOException | ClassNotFoundException e) {
                        break;
                    }
                }

                System.out.println("Driver data is loaded from the file");
                objIn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //method to save all the race data that is entered
    @Override
    public void saveRaceData(ArrayList<Race> races){
        try{
            File file1=new File("Race.txt");
            FileOutputStream fileOut1=new FileOutputStream(file1);
            ObjectOutputStream objectOut1=new ObjectOutputStream(fileOut1);// Connect the FileWriter to the BufferedWriter

            //save each driver details
            for (int i=0;i<races.size();i++) {
                objectOut1.writeObject(races.get(i));
            }

            System.out.println("Race data is saved");
            objectOut1.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    //method to load the race data that is saved
    public void loadRaceData(ArrayList<Race> races){
        File fileNew1=new File("Race.txt");
        //checking whether data is available to load
        if (fileNew1.length() == 0) {
            System.out.println("Race data is not available to load\n");
        }
        else {
            try {
                FileInputStream fileIn1 = new FileInputStream("Race.txt");
                ObjectInputStream objIn1 = new ObjectInputStream(fileIn1);

                //read each driver details and add it to the object arraylist
                while (true) {
                    try {
                        Race newFormula1Race1 = (Race) objIn1.readObject();
                        races.add(new Race(newFormula1Race1.getDate(),newFormula1Race1.getPositions()));

                     } catch (IOException | ClassNotFoundException e) {
                        break;
                    }
                }
                System.out.println("Race Data is loaded from the file");
                objIn1.close();
            } catch (Exception e) {
                e.printStackTrace();
             }
        }

    }


    //method to sort all the drivers according to points in descending order
    @Override
    public void sortPoints(ArrayList<Formula1Driver> formula1Drivers){

        //references: https://stackoverflow.com/questions/780541/how-to-sort-a-hashmap-in-java
        //hashmap for Formula1Driver and points
        HashMap<Formula1Driver, Integer> table = new HashMap<>();

        //to check whether there are any drivers added to object arraylist
        if(formula1Drivers.isEmpty()){
            System.out.println("No data to show");
        }
        else {
            for(int i=0;i<formula1Drivers.size();i++){
                table.put(formula1Drivers.get(i),formula1Drivers.get(i).getPoints());
            }
        }

        //comparing points to sort it in descending order
        List<Formula1Driver> pointsSort= new ArrayList<>(table.keySet());
        pointsSort.sort(Comparator.comparing(Formula1Driver::getPoints).reversed());

        //display the formula1 driver table
        System.out.println("Driver details according to their points:"+
                            "\nDriver ||\tTeam ||\tPoints |"+
                            "\n------------------------------------");
        for(Formula1Driver driver:pointsSort){
            System.out.println(driver.getDriverName()+" ||\t"+driver.getConstructorTeam()+" ||\t"+driver.getPoints()+" ||");
        }
        System.out.println("------------------------------------");


    }
}
