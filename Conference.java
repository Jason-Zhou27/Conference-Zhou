import java.io.*;
import java.util.*;

public class Conference {
    private int numTables;
    private int pplPerTable;
    private int aFileSize;
    private int cFileSize;
    private int aArraySize;
    private int cArraySize;
    private int capacity;
    private Attendee[] conferenceArray;
    private String[][] companyArray;
    private String filenameA = "confGuests.txt"; //name of text file that stores guests
    private String filenameC = "companies.txt";
    private int[][] tablesCompNum;
    private int[][] tablesID;
    private Attendee[][] tablesAttendee;
    private int manualID; //gives id of manual registration

    //constructors
    public Conference(int numT, int pPT) {
        numTables = numT;
        pplPerTable = pPT;
        capacity = numT*pPT;
        aArraySize = (int)(numT*pPT*1.5);
        cArraySize = (int)(numT*pPT*1.5); //accomodates the extreme case that each attendee is from his/her distinct company
        conferenceArray = new Attendee[aArraySize];
        companyArray = new String[2][cArraySize];
        tablesCompNum = new int[numTables][pplPerTable];
        tablesID = new int[numTables][pplPerTable];
        tablesAttendee = new Attendee[numTables][pplPerTable];
        fillArray();
    }
    //methods
    public void readFileA() { //loads text file of attendees into 1D array conferenceArray
        try {
            Scanner scan = new Scanner(new File(filenameA));
            aFileSize = 0;
            int i = 0;
            //go through all lines of text file, extracting info, creating attendee objects
            while (scan.hasNext()){
                String line = scan.nextLine();
                String[] elements = line.split(","); //split w/ , delimiter 
                int id = Integer.parseInt(elements[0]);
                String lN = elements[1];
                String fN = elements[2];
                int cN = Integer.parseInt(elements[3]);
                String cName = searchCompanyName(cN);
                conferenceArray[i]=new Attendee(fN, lN, id, cName, cN); //use constructor to build attendee objects
                aFileSize++;
                i++;
            }
            manualID=aFileSize+1; //not readFileA functionality; assists in finding initial id for manual add section
        } catch (FileNotFoundException e){
            System.out.println("File not Found!");
        }
    }
    public void readFileC() {
        try {
            Scanner scan1 = new Scanner(new File(filenameC));
            cFileSize = 0;
            int s=0;
            while (scan1.hasNext()){
                String lineComp = scan1.nextLine();
                String[] elementsComp = lineComp.split(",");
                String fileCompId = elementsComp[0];
                String fileCompName = elementsComp[1];
                companyArray[0][s]=fileCompId;
                companyArray[1][s]=fileCompName;
                cFileSize++;
                s++;
            }
        } catch (FileNotFoundException e){
            System.out.println("File not Found!");
        }
    }
    public String searchCompanyName(int searchCompanyID){
		for (int i=0; i<companyArray.length;i++){
			if(companyArray[0][i]!=null){
				if (Integer.parseInt(companyArray[0][i])==searchCompanyID){
					return companyArray[1][i];
				}
			}	
		}
		return "NA";
	}	
    public void manualAdd(){ //manual registration method
		System.out.print("If you wish to manually add attendees, press any key except for q. If you wish to quit, press q. \n");
		Scanner scan2 = new Scanner(System.in);
		if (!(scan2.nextLine()).equals("q")){
			System.out.print("How many attendees do you wish to add? If the amount is indefinite, type NA:");
			//definite quantity
			String manualAttendeeResponseNum = scan2.nextLine();
			if (!(manualAttendeeResponseNum.equals("NA"))){
				int amtManualAttendees = Integer.parseInt(manualAttendeeResponseNum);
				for(int i=0; i<amtManualAttendees; i++){
					System.out.print("To add attendee, use the following format to enter info: [first name],[last name],[company name],[company number] \n");
					String lineManual = scan2.nextLine();
					String[] elementsM = lineManual.split(",");
					String fNManual = elementsM[0];
					String lNManual = elementsM[1];
					String cnManual = elementsM[2];
					int cNumManual = Integer.parseInt(elementsM[3]);
					conferenceArray[manualID]=new Attendee(fNManual, lNManual, manualID, cnManual, cNumManual);
					manualID++;
					System.out.println("Successfully added!");
				}
			}
			//indefinite quantity
			else {
				for(int i=0; i<capacity; i++){
					System.out.print("To add attendee, use the following format to enter info: [first name],[last name],[company name],[company number] \n");
					String lineManual = scan2.nextLine();
					if (lineManual.equals("q")){
						break;
					}	
					String[] elementsM = lineManual.split(",");
					String fNManual = elementsM[0];
					String lNManual = elementsM[1];
					String cnManual = elementsM[2];
					int cNumManual = Integer.parseInt(elementsM[3]);
					conferenceArray[manualID]=new Attendee(fNManual, lNManual, manualID, cnManual, cNumManual);
					manualID++;
					System.out.println("Successfully added!");
					if (i==(capacity-2)){
						System.out.println("WARNING: ONLY ONE MORE SPOT AVAILABLE!");
					}	
				}
			}	
		}	
	}
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
        for(int a=0; a<conferenceArray.length; a++){
            for (int t=0; t<numTables; t++){
				if (conferenceArray[a]!=null){
					if (isValid(t,conferenceArray[a].getCompNum())){
						place(t, conferenceArray[a].getCompNum(), conferenceArray[a].getID(), conferenceArray[a]);
						break;
					}
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
