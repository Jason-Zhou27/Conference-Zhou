import java.io.*;
import java.util.*;

public class Conference {
    private int numTables;
    private int pplPerTable;
    private int aArraySize;
    private int capacity;
    private Attendee[] conferenceArray;
    private String filenameA = "confGuests.txt"; //name of text file that stores guests
    private String[][] tables;

    //constructors
    public Conference(int numT, int pPT) throws IOException {
        numTables = numT;
        pplPerTable = pPT;
        capacity = numT*pPT;
        aArraySize = (int)(numT*pPT*1.5);
        Attendee[] conferenceArray = new Attendee[aArraySize];
        readFileA();
        tables = new int[numTables][pplPerTable];
    }
    public void readFileA() throws IOException {
        Scanner scan = new Scanner(new File(filenameA));
        int i =0;
        while (i<aArraySize && scan.hasNext()){
            String line = scan.nextLine();
            String[] elements = line.split(",");
            int id = Integer.parseInt(elements[0]);
            String lN = elements[1];
            String fN = elements[2];
            int cN = Integer.parseInt(elements[3]);
            conferenceArray[i]=new Attendee(fN, lN, id, cN);
            i++;
        } 
    }
    //methods
    public void fillArray(){
        for (int r=0; r<numTables; r++){
            for (int c=0; c<pplPerTable; c++){
                tables[r][c]="-1";
            }
        }
    }
    public boolean isValid(int tableNum, int idSearch){
        for (int c=0; c<pplPerTable; c++){
            if (tables[tableNum][c]==idSearch){
                return false
            }
        }
        if ()
        return true;
    }
    public void place(int tableNum, int idPlace){
        for (tables)
    }
    public void organizeTables(){
        for(int a=0; a<capacity; a++){
            for (int t=0; t<numTables; t++){
                if (isValid(t,conferenceArray[a].getID)){
                    place(t, conferenceArray[a].getID);
                }
            }
            
        }

    }


}