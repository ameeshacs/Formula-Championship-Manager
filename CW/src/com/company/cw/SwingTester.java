package com.company.cw;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class SwingTester{
    Formula1Driver driver = new Formula1Driver();

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel controlPanel1;
    private JLabel statusLabel;
    private JTable table;
    private JTextField textField;

//method to display the main components and the main table
    public SwingTester(ArrayList<Formula1Driver> formula1Drivers,ArrayList<Race> races){
        designGUI(formula1Drivers,races);//GUI design method
        showTableDemo(formula1Drivers);//main table display
    }

    //method to add all the main components of the window
    private void designGUI(ArrayList<Formula1Driver> formula1Drivers,ArrayList<Race> races){
        //creating a new JFrame and setting the layout and the size
        mainFrame = new JFrame("GUI");
        mainFrame.setMinimumSize(new Dimension(500,400));
        mainFrame.setLayout(new GridLayout(5, 1));

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Formula1ChampionshipManager  newFM=new Formula1ChampionshipManager();
                //saving the data before JVM is closed
                newFM.saveDriverData(formula1Drivers);
                newFM.saveRaceData(races);
                System.out.println("Closing window!");
                //closing the application
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350,100);

        //a new panel for the driver table
        controlPanel1 = new JPanel();
        controlPanel1.setLayout(new BorderLayout(10,10));


        //creating a new panel for the text field and search button
        JPanel controlPanel3 = new JPanel();
        controlPanel3.setLayout(new FlowLayout());
        textField=new JTextField(30);
        JButton searchDriver=new JButton("Search");

        //a new panel for buttons
        JPanel controlPanel2 = new JPanel();
        BoxLayout boxlayout = new BoxLayout(controlPanel2, BoxLayout.X_AXIS);//box layout is on horizontal
        controlPanel2.setLayout(boxlayout);

        //buttons
        JButton addRaceButton = new JButton("Add Race");
        JButton addRaceProbability = new JButton("Add race with probability");
        JButton displayRaceButton = new JButton("Display All Races");
        displayRaceButton.setHorizontalTextPosition(SwingConstants.LEFT);

        //action event for addRaceButton
        addRaceButton.addActionListener(e -> {
            generateRace(formula1Drivers,races);
            TableModel model=table.getModel();
            for(int j = 0; j<formula1Drivers.size(); j++) {
                //set values for the table
                model.setValueAt(formula1Drivers.get(j).getDriverName(),j,0);
                model.setValueAt(formula1Drivers.get(j).getRacesNo(),j,1);
                model.setValueAt(formula1Drivers.get(j).getFirstPlaces(),j,2);
                model.setValueAt(formula1Drivers.get(j).getSecondPlaces(),j,3);
                model.setValueAt(formula1Drivers.get(j).getThirdPlaces(),j,4);
                model.setValueAt(formula1Drivers.get(j).getPoints(),j,5);
            }

        });

        //action event for addRaceProbabilityButton
        addRaceProbability.addActionListener(e -> {
            winProbability(formula1Drivers,races);
            TableModel model2=table.getModel();
            for(int j = 0; j<formula1Drivers.size(); j++) {
                //set values for the table
                model2.setValueAt(formula1Drivers.get(j).getDriverName(),j,0);
                model2.setValueAt(formula1Drivers.get(j).getRacesNo(),j,1);
                model2.setValueAt(formula1Drivers.get(j).getFirstPlaces(),j,2);
                model2.setValueAt(formula1Drivers.get(j).getSecondPlaces(),j,3);
                model2.setValueAt(formula1Drivers.get(j).getThirdPlaces(),j,4);
                model2.setValueAt(formula1Drivers.get(j).getPoints(),j,5);
            }
            statusLabel.setText("Add race with probability button clicked.");
        });

        //action event for the button to display all races
        displayRaceButton.addActionListener(e -> displayAllRaces(races));

        //action event for the button to search driver
        searchDriver.addActionListener(e -> searchRaceDriver(formula1Drivers,races));

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel1);
        mainFrame.add(statusLabel);
        mainFrame.add(controlPanel3);
        controlPanel3.add(textField);
        controlPanel3.add(searchDriver);
        mainFrame.add(controlPanel2);
        controlPanel2.add(addRaceButton);
        controlPanel2.add(addRaceProbability);
        controlPanel2.add(displayRaceButton);
        mainFrame.setVisible(true);
    }

    //method to generate a table with data and sort it according to points
    public void showTableDemo(ArrayList<Formula1Driver> formula1Drivers){
        //references: https://stackoverflow.com/questions/28823670/how-to-sort-jtable-in-shortest-way
        headerLabel.setText("Control in action: JTable");
        //hashmap for Formula1Driver and points
        HashMap<Formula1Driver, Integer> table1 = new HashMap<>();

        //to check whether there are any drivers added to object arraylist
        if(formula1Drivers.isEmpty()){
            System.out.println("No data to show");
        }
        else {
            for(int i=0;i<formula1Drivers.size();i++){
                table1.put(formula1Drivers.get(i),formula1Drivers.get(i).getPoints());
            }
        }

        //comparing points to sort it in descending order
        List<Formula1Driver> pointsSort= new ArrayList<>(table1.keySet());
        pointsSort.sort(Comparator.comparing(Formula1Driver::getPoints).reversed());

        //creating the default table model
        DefaultTableModel model = new DefaultTableModel(new String[] {"Name","No. of Races","First Places","Second Places","Third Places", "Points"},0);
        for (Formula1Driver formula1Driver : pointsSort) {
            //adding data into rows
            model.addRow(new Object[]{formula1Driver.getDriverName(), formula1Driver.getRacesNo(), formula1Driver.getFirstPlaces(), formula1Driver.getSecondPlaces(), formula1Driver.getThirdPlaces(), formula1Driver.getPoints()});
        }

        //creating a table according to the table model
        table = new JTable(model);

        table.setFillsViewportHeight(true);
        controlPanel1.add(new JScrollPane(table));
        mainFrame.pack();
        mainFrame.setVisible(true);

        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to display the table according to the points in ascending order? Yes/No");
        String input= sc.next();

        //sorting the table according to points in ascending order if the user says yes
        if(input.equals("Yes") || input.equals("yes")){
            //clearing the original rows
            model.setRowCount(0);

            //comparing points to sort it in ascending order
            pointsSort.sort(Comparator.comparing(Formula1Driver::getPoints));

            //creating the default table model
            for (Formula1Driver formula1Driver : pointsSort) {
                //adding data into rows
                model.addRow(new Object[]{formula1Driver.getDriverName(), formula1Driver.getRacesNo(), formula1Driver.getFirstPlaces(), formula1Driver.getSecondPlaces(), formula1Driver.getThirdPlaces(), formula1Driver.getPoints()});
            }

        }

        System.out.println("Do you want to display the table according to the number of first places in descending order? Yes/No");
        String inputFirstPlace= sc.next();

        //sorting the table according to the number of first places in ascending order if the user says yes
        if(inputFirstPlace.equals("Yes") || inputFirstPlace.equals("yes")){
            TableRowSorter<TableModel> sorter2 = new TableRowSorter<TableModel>(table.getModel());
            table.setRowSorter(sorter2);
            List<RowSorter.SortKey> sortKeys2 = new ArrayList<>(25);
            sortKeys2.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
            sorter2.setSortKeys(sortKeys2);
        }
    }


    //method to generate an random race
    public void generateRace(ArrayList<Formula1Driver> formula1Drivers,ArrayList<Race> races){
        Scanner sc = new Scanner(System.in);
        //creating an arraylist for driver names for each position
        ArrayList<String> generateNewRacePosition = new ArrayList<>();
        //creating an arraylist to for positions
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i< formula1Drivers.size(); i++) {
            list.add(i);
        }
        //shuffling the positions
        Collections.shuffle(list,new Random());

        //checking if the positions are as same as other race positions
        if(races.size()!=0) {
            for(int j=0;j< formula1Drivers.size();j++){
            int num=list.get(j);
                for (Race race : races) {
                    if (formula1Drivers.get(num).getDriverName().equals(race.getPositions().get(j))) {
                        Collections.shuffle(list, new Random());
                    }
                }
            }
        }

        //entering the date of the race
        System.out.println("Enter the date of the race in the format of dd.MM.yyyy");
        String date=sc.next();

        //updating the statistics according to the positions of the list
        for (int i=0; i< formula1Drivers.size(); i++) {
            int num=list.get(i);
            int newPoints=formula1Drivers.get(num).getPoints()+ driver.pointsTable[i];
            formula1Drivers.get(num).setPoints(newPoints);
            generateNewRacePosition.add(formula1Drivers.get(num).getDriverName());


            if(i==0) {
                int newFirstPlace = formula1Drivers.get(num).getFirstPlaces() + 1;
                formula1Drivers.get(num).setFirstPlaces(newFirstPlace);
            }
            else if(i==1) {
                int newSecondPlace = formula1Drivers.get(num).getSecondPlaces() + 1;
                formula1Drivers.get(num).setSecondPlaces(newSecondPlace);
            }

            else if(i==2){
                int newThirdPlace=formula1Drivers.get(num).getThirdPlaces()+1;
                formula1Drivers.get(num).setThirdPlaces(newThirdPlace);
            }
            else if(i>10){
                break;
            }
        }

        //updating the number of races
        for (Formula1Driver formula1Driver : formula1Drivers) {
            int newNoRaces = formula1Driver.getRacesNo() + 1;
            formula1Driver.setRacesNo(newNoRaces);
        }

        //adding the generated race to the object race arraylist
        races.add(new Race(date,generateNewRacePosition));
        JFrame jFrameNew=new JFrame();
        jFrameNew.setMinimumSize(new Dimension(500,400));
        DefaultTableModel model = new DefaultTableModel(new String[] {"Name","Position"},0);

        for(int j=0;j< formula1Drivers.size();j++) {
            model.addRow(new Object[]{ races.get(races.size()-1).getPositions().get(j), (j+1)});
        }

        //displaying the new generated positions in a table
        JTable tableNew = new JTable(model);
        TableRowSorter<TableModel> sorter4 = new TableRowSorter<TableModel>(tableNew.getModel());
        tableNew.setRowSorter(sorter4);

        //sorting the table in ascending order of the positions
        List<RowSorter.SortKey> sortKeys4 = new ArrayList<>(25);
        sortKeys4.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter4.setSortKeys(sortKeys4);

        tableNew.setFillsViewportHeight(true);
        jFrameNew.add(new JScrollPane(tableNew));
        jFrameNew.setVisible(true);

    }


    //method to display all the races
    public void displayAllRaces(ArrayList<Race> races){
        JFrame jFrame2=new JFrame();
        jFrame2.setMinimumSize(new Dimension(500,400));
        JLabel headerLabel2=new JLabel("All Races", JLabel.CENTER);
        DefaultTableModel model = new DefaultTableModel(new String[] {"Date of the Race","Name","Position"},0);

        //add all the races that were generated in to the table

        for (int i=0;i<races.size();i++) {
            for(int j=0;j<races.get(i).getPositions().size();j++) {

                model.addRow(new Object[]{races.get(i).getDate(), races.get(i).getPositions().get(j), (j + 1)});

            }
        }

        //creating the table according to the default table model
        JTable table1 = new JTable(model);
        TableRowSorter<TableModel> sorter4 = new TableRowSorter<TableModel>(table1.getModel());
        table1.setRowSorter(sorter4);

        //sort the all the races according to the ascending order of the date of races
        List<RowSorter.SortKey> sortKeys4 = new ArrayList<>(25);
        sortKeys4.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter4.setSortKeys(sortKeys4);

        table1.setFillsViewportHeight(true);
        jFrame2.add(headerLabel2);
        jFrame2.add(new JScrollPane(table1));
        jFrame2.setVisible(true);


    }

    //method to search for a driver to display the positions of the driver in each race
    public void searchRaceDriver(ArrayList<Formula1Driver> formula1Drivers,ArrayList<Race> races){
        Frame jFrame3=new JFrame();
        jFrame3.setMinimumSize(new Dimension(500,400));
        JLabel headerLabel2=new JLabel("All Races", JLabel.CENTER);

        //creating a table model and adding all the races to it
        DefaultTableModel model = new DefaultTableModel(new String[] {"Date of the Race","Name","Position"},0);
        for (int i=0;i< races.size();i++) {
            for(int j=0;j< races.get(i).getPositions().size();j++) {
                model.addRow(new Object[]{races.get(i).getDate(), races.get(i).getPositions().get(j), (j+1)});
            }
        }

        //creating a table according to the table model
        JTable table3 = new JTable(model);
        TableRowSorter<TableModel> sorter4 = new TableRowSorter<TableModel>(table3.getModel());
        table3.setRowSorter(sorter4);

        List<RowSorter.SortKey> sortKeys4 = new ArrayList<>(25);
        sortKeys4.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter4.setSortKeys(sortKeys4);

        //getting the name from the text field
        String text = textField.getText();

        //filtering the table for the name
        //source link:https://stackoverflow.com/questions/20325722/how-can-i-perform-a-case-insensitive-filter-on-a-jtable
        if (text.trim().length() == 0) {
            sorter4.setRowFilter(null);
        } else {
            sorter4.setRowFilter(RowFilter.regexFilter(text));
        }

        table3.setFillsViewportHeight(true);
        jFrame3.add(headerLabel2);
        jFrame3.add(new JScrollPane(table3));
        jFrame3.setVisible(true);
    }


    //method to generate a random race and determine the final positions according to the starting positions
    public void winProbability(ArrayList<Formula1Driver> formula1Drivers,ArrayList<Race> races) {
        int firstPlace;
        Scanner sc = new Scanner(System.in);

        //arraylist to store starting positions
        ArrayList<Integer> startingPositions = new ArrayList<>();
        //adding all the positions to the arraylist
        for (int i = 0; i < formula1Drivers.size(); i++) {
            startingPositions.add(i);
        }
        //shuffle the list to generate random starting positions
        Collections.shuffle(startingPositions,new Random());

        //randomly picking the first place from the starting position of 1-9
        if(formula1Drivers.size()>=10) {
            firstPlace = (int) (Math.random() * 9);
        }
        else{
            firstPlace = (int) (Math.random() * formula1Drivers.size());
        }

        //creating an arraylist for the finish positions
        ArrayList<Integer> list = new ArrayList<>();
        //creating an arraylist for the names of the finish positions
        ArrayList<String> generateNewRacePosition = new ArrayList<>();
        list.add(startingPositions.get(firstPlace));
        for (int i = 0; i < formula1Drivers.size(); i++) {
            if (i == firstPlace) {
                //skipping the number of the first place
                continue;
            }
            //adding the rest of the starting positions
            list.add(startingPositions.get(i));
        }
        //shuffling the list of ending positions
        Collections.shuffle(list);
        //swapping back the first place to the original first place
        Collections.swap(list, 0, list.indexOf(firstPlace));

        //enter the date of the race
        System.out.println("Enter the date of the race in the format of dd.MM.yyyy");
        String date = sc.next();

        //updating the statistics of the first table
        for (int i = 0; i < list.size(); i++) {
            int num = list.get(i);
            int newPoints = formula1Drivers.get(num).getPoints() + driver.pointsTable[i];
            formula1Drivers.get(num).setPoints(newPoints);
            generateNewRacePosition.add(formula1Drivers.get(num).getDriverName());

            //updating the number of first second and third places if obtained
            if (i ==0) {
                int newFirstPlace = formula1Drivers.get(num).getFirstPlaces() + 1;
                formula1Drivers.get(num).setFirstPlaces(newFirstPlace);
            }

            else if(i==1) {
                int newSecondPlace = formula1Drivers.get(num).getSecondPlaces() + 1;
                formula1Drivers.get(num).setSecondPlaces(newSecondPlace);
            }

            else if(i==2){
                int newThirdPlace = formula1Drivers.get(num).getThirdPlaces() + 1;
                formula1Drivers.get(num).setThirdPlaces(newThirdPlace);
            }

        }

        //updating the total number of races
        for (Formula1Driver formula1Driver : formula1Drivers) {
            int newNoRaces = formula1Driver.getRacesNo() + 1;
            formula1Driver.setRacesNo(newNoRaces);
        }

        //adding the generated race to the race object arraylist
        races.add(new Race(date,generateNewRacePosition));

        //creating a new frame and adding the details of the new generated race to a table
        JFrame jFrameNew=new JFrame();
        jFrameNew.setMinimumSize(new Dimension(500,400));
        DefaultTableModel model = new DefaultTableModel(new String[] {"Name","Position"},0);

        //adding the data of the new race to the rows
        for(int j=0;j< formula1Drivers.size();j++) {
            model.addRow(new Object[]{ races.get(races.size()-1).getPositions().get(j), (j+1)});
        }

        //creating a new table according to the model
        JTable tableNew = new JTable(model);
        TableRowSorter<TableModel> sorter4 = new TableRowSorter<TableModel>(tableNew.getModel());
        tableNew.setRowSorter(sorter4);

        //sorting the table according to the position
        List<RowSorter.SortKey> sortKeys4 = new ArrayList<>(25);
        sortKeys4.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter4.setSortKeys(sortKeys4);

        tableNew.setFillsViewportHeight(true);
        jFrameNew.add(new JScrollPane(tableNew));
        jFrameNew.setVisible(true);

    }





}
