package com.company.cw;

import java.util.ArrayList;

public interface ChampionshipManager {
    void addDriver(ArrayList<Formula1Driver> formula1Drivers);
    void deleteDriver(ArrayList<Formula1Driver> formula1Drivers);
    void replaceDriver(ArrayList<Formula1Driver> formula1Drivers);
    void displayStatistics(ArrayList<Formula1Driver> formula1Drivers);
    void addRace(ArrayList<Formula1Driver> formula1Drivers,ArrayList<Race> races);
    void saveDriverData(ArrayList<Formula1Driver> formula1Drivers);
    void sortPoints(ArrayList<Formula1Driver> formula1Drivers);
    void saveRaceData(ArrayList<Race> races);
}
