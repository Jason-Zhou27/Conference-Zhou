import java.io.*;
import java.util.*

public class Conference [
    private int numTables;
    private int pplPerTable;
    private int aArraySize;
    private Attendee[] conferencearray;
    private String filenameA = "confGuests.txt"; //name of text file that stores guests
    
    //constructors
    public Conference(int numT, int pPT) throws IOException {
        numTables = numT;
        pplPerTable = pPT;
        aArraySize = numT*pPT*1.5;
        Attendee[] conferenceArray = new Attendee[aArraySize];
        readFileA();
    }
    public void readFileA() throws IOException {
        Scanner scan = new Scanner(new File(filenameA));
        int i =0;
        while (i<aArraySize && sca.hasNext()){
            String line = scan.nextLine();
            String[] elements = line.split(",");
            int id = elements[0];
            String lN = elements[1];
            String fN = elements[2];
            String cN = elements[3];
            conferenceArray[i]=new Attendee(fN, lN, id, cN);
            i++
        } 
    }

    //methods

]