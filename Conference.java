/**
 * Conference.java
 * @author Jason Zhou
 * @since (date) 11/19/2025
 * This class organizes the Conference by registering attendees and organizing attendees at tables
*/
import java.io.*;
import java.util.*;
/*
 * Conference class organizes the Conference w/ a constructor and the following methods: readFileA(), readFileC(), searchCompanyName(), manualAdd(), fillArray(),
 * isValid(), place(), organizeTables(), printIDTablesArray(), printCompNumTablesArray(), & printFirstNamesTablesArray()
*/
public class Conference {
	//variables
    private int numTables;
    private int pplPerTable;
    private int maxPplPerCompany;
    private int aFileSize;
    private int cFileSize;
    private int aArraySize;
    private int cArraySize;
    private int capacity;
    private Attendee[] conferenceArray;
    private String[][] companyArray;
    private String filenameA = "confGuests.txt"; //name of text file that stores attendees/guests
    private String filenameC = "companies.txt"; //name of text file that stores companies
    private int[][] tablesCompNum;
    private int[][] tablesID;
    private Attendee[][] tablesAttendee;
    private int manualID; //gives id of manual registration

    //constructors
    /*
     * Conference constructor takes in arguments such as number of tables and people per tables, uses those arguments to calculate and define circumstances of the conference,
     * and fills tables arrays
    */ 
    public Conference(int numT, int pPT, int mPPC) {
        numTables = numT;
        pplPerTable = pPT;
        maxPplPerCompany = mPPC;
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
    /*
     * readFileA reads the attendee file with a Scanner; it takes in each line of info, extracts the individual elements using the comma delimiter, and constructs Attendee objects using
     * such info. It also counts the number of attendees loaded through the text file.
     * Employs try and catch structure.
    */
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
    /*
     * similar to readFileA, readFileC reads the company file with a Scanner; it takes in each line of info, extracts the individual elements using the comma delimeter, and populates
     * a 2d array of companies with each company receiving a respective column (row 0 is company id and row 1 is name). The 2d array of companies will be used in creating Attendee objects
     * by finding the company name that corresponds to a company id.
     * Employs try and catch structure.
    */
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
    /*
     * searchCompanyName takes in a company ID as a parameter to find the corresponding company name. It looks through a 2d array which allots a company per column which has row 0 
     * used for company id and row 1 for name. With a for loop, it goes through the length of the companyArray, comparing the id of each column to the id being searched. If it finds a 
     * match, it returns the company name; if not, it returns NA signaling a company name was not found
    */
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
	/*
	 * manualAdd allows the user to interact with the program to manually register attendees that were not loaded through pre-registration. It presents the option to either add a definite
	 * or indefinite number, which helps with convenience. To manually add attendees, the program prompts the user to enter the required info using a specific format (similar to the one in the txt file)
	 * and uses a similar process (splitting up individual info with delimiter) to extract information to create an instantiation of the attendee object; since the id is not explicitly known by the
	 * person registering, the program will start those who register with a manual id relative to the last id of manual registration and increment it every time someone manually registers. The program
	 * will present the user with a notification if the attendee was successfully added or not and a warning if the max # of attendees is being approached
	*/ 	
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
	/*
	 * fillArray is a method which fills the tablesCompNum 2d array and the tablesID 2d array w/ -1s, which is a placeholder to signify--when the arrays are being searched--that there is a vacancy
	 * that can be filled; a nested for loop in another for loop is used to circulate.
	*/
    public void fillArray(){
        for (int r=0; r<numTables; r++){
            for (int c=0; c<pplPerTable; c++){
                tablesCompNum[r][c]=-1;
                tablesID[r][c]=-1;
            }
        }
    }
    /*
     * isValid is a method which checks if an attendee can be placed at the table by looking for 2 conditions; a person from the same company cannot be also sitting at the table (uses 2d array tablesCompNum
     * for that purpose) & there is a vacancy present (uses tablesID for that purpose--looking for a -1); the boolean free satisfies the purpose of checking the latter condition
    */
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
    /*
     * place is a method which places the attendee at a certain table in a vacant spot; it updates the corresponding tables arrays as well using the arguments; it exits out of the for loop
     * once a spot is filled with break so the attendee does not populate multiple tables
    */
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
    /*
     * organizeTables calls the isValid and place methods to run the task of assigning attendees to tables. It goes through each attendee with a for loop, checks each table with a for loop nested
     * in the attendee for loop, and if a table can accomodate him/her, the attendee is placed with the place method and break is used to exit out of the nested loop (so an attendee is not placed
     * at multiple tables)
    */
    public void organizeTables(){
        for(int a=0; a<capacity; a++){
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
    public String checkConditions(){
		int pplPerCompany;
		for(int c=0;c<companyArray[0].length;c++){
			pplPerCompany=0;
			if (companyArray[0][c]!=null){
				for(int a=0;a<conferenceArray.length;a++){
					if(conferenceArray[a]!=null){
						if(companyArray[0][c]==conferenceArray[a].getCompNum){
							pplPerCompany++;	
						}	
					}	
				}	
			}
			if(pplPerCompany>maxpplPerCompany){
				return "too many attendees from company " + companyArray[1][c];
			}		
		}
		return "";
	}	
    /*
     * printIDTablesArray uses two for loops (1 nested) to circulate through the table of IDs and print them out. 
     * It serves a useful tool in debugging/testing but also provides useful information to the attendees to find where they are based
     * on id.
    */	
    public void printIDTablesArray(){
        for (int t=0; t<numTables; t++){
            for (int a=0; a<pplPerTable; a++){
                System.out.print(tablesID[t][a] + " ");
            }
            System.out.print("\n");
        }
    }
    /*
     * printIDTablesArray uses two for loops (1 nested) to circulate through the table of compIDs and print them out. 
     * It serves a useful tool in debugging/testing as it ensures that no company has more than 1 person at a certain table.
    */
    public void printCompNumTablesArray(){
        for (int t=0; t<numTables; t++){
            for (int a=0; a<pplPerTable; a++){
                System.out.print(tablesCompNum[t][a] + " ");
            }
            System.out.print("\n");
        }
    }
    /*
     * printFirstNameTablesArray uses two for loops (1 nested) to circulate through the table of Attendees and print their respective first names out with the getFirst method found in the Attendee class.
     * It checks if the attendee object exists in the array before using the getFirst method to prevent it from trying to use getFirst on an object that does not exist (null). If no attendee exists
     * at a spot, it will print out NA.
     * This method--in addition to the printIDTablesArray--helps attendees find where they are based on first name
    */
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
