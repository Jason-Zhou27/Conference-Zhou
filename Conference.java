import java.io.*;
import java.util.*;

public class Conference {
    private int numTables;
    private int pplPerTable;
    private int aFileSize;
    private int cFileSize;
    private int aArraySize;
    private int capacity;
    private Attendee[] conferenceArray;
    private String filenameA = "confGuests.txt"; //name of text file that stores guests
    private String filenameC = "companies.txt";
    private int[][] tablesCompNum;
    private int[][] tablesID;
    private Attendee[][] tablesAttendee;

    //constructors
    public Conference(int numT, int pPT) {
        numTables = numT;
        pplPerTable = pPT;
        capacity = numT*pPT;
        aArraySize = (int)(numT*pPT*1.5);
        conferenceArray = new Attendee[aArraySize];
        tablesCompNum = new int[numTables][pplPerTable];
        tablesID = new int[numTables][pplPerTable];
        tablesAttendee = new Attendee[numTables][pplPerTable];
        fillArray();
    }
    public void readFileA() {
        try {
            Scanner scan = new Scanner(new File(filenameA));
            aFileSize = 0;
            while (scan.hasNext()){
                String line = scan.nextLine();
                String[] elements = line.split(",");
                int id = Integer.parseInt(elements[0]);
                String lN = elements[1];
                String fN = elements[2];
                int cN = Integer.parseInt(elements[3]);
                conferenceArray[i]=new Attendee(fN, lN, id, cN);
                aFileSize++;
            }
        } catch (FileNotFoundException e){
            System.out.println("File not Found!");
        }
    }
    /*public void readFileC() {
        try {
            Scanner scan1 = new Scanner(new File(filenameC));
            cFileSize = 0;
            while (scan1.hasNext()){
                String line = scan1.nextLine();
                String[] elements = line.split(",");
                int id = Integer.parseInt(elements[0]);
                String lN = elements[1];
                String fN = elements[2];
                int cN = Integer.parseInt(elements[3]);
                conferenceArray[i]=new Attendee(fN, lN, id, cN);
                cFileSize++;
            }
        } catch (FileNotFoundException e){
            System.out.println("File not Found!");
        }
    }
    */
    //methods
    public void fillArray(){
        for (int r=0; r<numTables; r++){
            for (int c=0; c<pplPerTable; c++){
                tablesCompNum[r][c]=-1;
                tablesID[r][c]=-1;
            }
        }
    }
    public boolean isValid(int tableNum, int compNumSearch){
        boolean free = false;
        for (int c=0; c<pplPerTable; c++){
            if (tablesCompNum[tableNum][c]==compNumSearch){
                return false;
            }
            if (tablesID[tableNum][c]==-1){
                free = true;
            }
        }
        return free;
    }
    public void place(int tableNum, int compNumPlace, int iDplace, Attendee attendeePlace){
        for (int c=0; c<pplPerTable; c++){
            if (tablesID[tableNum][c]==-1){
                tablesCompNum[tableNum][c]=compNumPlace;
                tablesID[tableNum][c]=iDplace;
                tablesAttendee[tableNum][c]=attendeePlace;
                break;
            }
        }
    }
    public void organizeTables(){
        for(int a=0; a<aFileSize; a++){
            for (int t=0; t<numTables; t++){
                if (isValid(t,conferenceArray[a].getCompNum())){
                    place(t, conferenceArray[a].getCompNum(), conferenceArray[a].getID(), conferenceArray[a]);
                    break;
                }
            }
        }
    }
    public void printIDTablesArray(){
        for (int t=0; t<numTables; t++){
            for (int a=0; a<pplPerTable; a++){
                System.out.print(tablesID[t][a] + " ");
            }
            System.out.print("\n");
        }
    }
    public void printCompNumTablesArray(){
        for (int t=0; t<numTables; t++){
            for (int a=0; a<pplPerTable; a++){
                System.out.print(tablesCompNum[t][a] + " ");
            }
            System.out.print("\n");
        }
    }
    public void printFirstNameTablesArray(){
        for (int t=0; t<numTables; t++){
            for (int a=0; a<pplPerTable; a++){
                if (tablesAttendee[t][a]!=null){
                    System.out.print(tablesAttendee[t][a].getFirst() + " ");
                }
                else {
                    System.out.print("NA ");
                }
            }
            System.out.print("\n");
        }
    }
    //add searchPerson method
    //add grabTableInfo by # method
    
}