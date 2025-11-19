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
        Attendee[] conferencearray = new Attendee[aArraySize];
        readFileA();
    }
    public void readFileA() throws IOException {
        Scanner scan = new Scanner(new File(filenameA));
        int i =0;
        while (i<)
    }

    //methods

]