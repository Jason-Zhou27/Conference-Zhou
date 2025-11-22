import java.io.*;
import java.util.*;

public class Conference {
    private int numTables;
    private int pplPerTable;
    private int aFileSize;
    private int aArraySize;
    private int capacity;
    private Attendee[] conferenceArray;
    private String filenameA = "confGuests.txt"; //name of text file that stores guests
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
            int i =0;
            aFileSize = 0;
            while (i<capacity && scan.hasNext()){
                String line = scan.nextLine();
                String[] elements = line.split(",");
                int id = Integer.parseInt(elements[0]);
                String lN = elements[1];
                String fN = elements[2];
                int cN = Integer.parseInt(elements[3]);
                conferenceArray[i]=new Attendee(fN, lN, id, cN);
                i++;
                aFileSize++;
            }
        } catch (FileNotFoundException e){
            System.out.println("File not Found!");
        }
    }
    //methods
    public void fillArray(){
        for (int r=0; r<numTables; r++){
            for (int c=0; c<pplPerTable; c++){
                tablesID[r][c]=-1;
            }
        }
    }
    public boolean isValid(int tableNum, int compIdSearch){
        boolean free = false;
        for (int c=0; c<pplPerTable; c++){
            if (tablesID[tableNum][c]==compIdSearch){
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
                tablesID[tableNum][c]=compNumPlace;
                break;
            }
        }
    }
    public void organizeTables(){
        for(int a=0; a<aFileSize; a++){
            for (int t=0; t<numTables; t++){
                if (isValid(t,conferenceArray[a].getCompNum())){
                    place(t, conferenceArray[a].getCompNum(), conferenceArray[a].getID());
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
                if (tablesAttendee[t][a]!=null){
                    System.out.print(tablesAttendee[t][a].getCompNum() + " ");
                }
            }
            System.out.print("\n");
        }
    }
    
}