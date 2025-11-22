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
    private int[][] tables;

    //constructors
    public Conference(int numT, int pPT) {
        numTables = numT;
        pplPerTable = pPT;
        capacity = numT*pPT;
        aArraySize = (int)(numT*pPT*1.5);
        conferenceArray = new Attendee[aArraySize];
        tables = new int[numTables][pplPerTable];
        fillArray();
    }
    public void readFileA() {
        try {
            Scanner scan = new Scanner(new File(filenameA));
            int i =0;
            int aFileSize = 0;
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
                tables[r][c]=-1;
            }
        }
    }
    public boolean isValid(int tableNum, int idSearch){
        boolean free = false;
        for (int c=0; c<pplPerTable; c++){
            if (tables[tableNum][c]==idSearch){
                return false;
            }
            if (tables[tableNum][c]==-1){
                free = true;
            }
        }
        return free;
    }
    public void place(int tableNum, int idPlace){
        for (int c=0; c<pplPerTable; c++){
            if (tables[tableNum][c]==-1){
                tables[tableNum][c]=idPlace;
                break;
            }
        }
    }
    public void organizeTables(){
        for(int a=0; a<aFileSize; a++){
            for (int t=0; t<numTables; t++){
                if (isValid(t,conferenceArray[a].getID())){
                    place(t, conferenceArray[a].getID());
                }
            }
        }
    }
    public void printIDTablesArray(){
        for (int t=0; t<numTables; t++){
            for (int a=0; a<pplPerTable; a++){
                System.out.print(tables[t][a] + " ");
            }
            System.out.print("\n");
        }
    }
}