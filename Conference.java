/**
 * Conference.java
 * @author Jason Zhou
 * @since (date) 11/19/2025
 * This class organizes the Conference by registering attendees and organizing attendees at tables. Furthermore, it provides info to the user about the conference.
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
    private int aFileSize; //preregistration #
    private int cFileSize; //preregistration #
    private int aArraySize; //net
    private int cArraySize; //net
    private int capacity;
    private Attendee[] conferenceArray;
    private String[][] companyArray;
    private String filenameA = "confGuests.txt"; //name of text file that stores attendees/guests
    private String filenameC = "companies.txt"; //name of text file that stores companies
    private int[][] tablesCompNum;
    private int[][] tablesID;
    private Attendee[][] tablesAttendee;
    private int manualID; //gives id of manual registration
    private int manualCID; //gives company id/num of manual registration
    private int lenA; //determines how many tens digits to allot to Attendee ID print; helps with alignment
    private int lenC; //determines how many tens digits to allot to Attendee ID print; helps with alignment

    //constructors
    
    public Conference() {
        System.out.println("\n\nWelcome to the Conference program. Please fill out the following information to get started.\n");
        Scanner scanC = new Scanner(System.in);
        System.out.println("# of tables for seating: ");
        numTables = Integer.parseInt(scanC.nextLine());
        System.out.println("# of people per table: ");
        pplPerTable = Integer.parseInt(scanC.nextLine());
        System.out.println("Max # of people from each company: ");
        maxPplPerCompany = Integer.parseInt(scanC.nextLine());
        capacity = numT*pPT;
        aArraySize = (int)(numT*pPT*1.5);
        cArraySize = (int)(numT*pPT*1.5); //accomodates the extreme case that each attendee is from his/her distinct company
        lenA=findPlaceTens(aArraySize);
        lenC=findPlaceTens(cArraySize);
        conferenceArray = new Attendee[aArraySize];
        companyArray = new String[2][cArraySize];
        tablesCompNum = new int[numTables][pplPerTable];
        tablesID = new int[numTables][pplPerTable];
        tablesAttendee = new Attendee[numTables][pplPerTable];
        fillArray();
    }
    /*
     * Conference constructor gives welcome and takes in arguments such as number of tables, people per tables, and max people per table;
     * uses those arguments to calculate and define circumstances of the conference, and fills tables arrays. This constructor was used 
     * for testing and creation; probably retired as of 11/29/2025
    */ 
    public Conference(int numT, int pPT, int mPPC) {
		System.out.println("\n\nWelcome to the Conference program.\n");
        numTables = numT;
        pplPerTable = pPT;
        maxPplPerCompany = mPPC;
        capacity = numT*pPT;
        aArraySize = (int)(numT*pPT*1.5);
        cArraySize = (int)(numT*pPT*1.5); //accomodates the extreme case that each attendee is from his/her distinct company
        lenA=findPlaceTens(aArraySize);
        lenC=findPlaceTens(cArraySize);
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
        try { //implementing try catch was not initially clear; used throws IOException at first according to Runestone, but errors surfaced. Switched to try catch structure w/ w3schools
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
            manualID=aFileSize; //not readFileA functionality; assists in finding initial id for manual add section
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
        try { //implementing try catch was not initially clear; used throws IOException at first according to Runestone, but errors surfaced. Switched to try catch structure w/ w3schools
            Scanner scan1 = new Scanner(new File(filenameC));
            cFileSize = 0;
            int s=0;
            while (scan1.hasNext()){
                String lineComp = scan1.nextLine();
                if (!lineComp.equals("")){
					String[] elementsComp = lineComp.split(",");
					String fileCompId = elementsComp[0];
					String fileCompName = elementsComp[1];
					companyArray[0][s]=fileCompId;
					companyArray[1][s]=fileCompName;
					cFileSize++;
					s++;
				}
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
		for (int i=0; i<companyArray[0].length;i++){
			if(companyArray[0][i]!=null){
				if (Integer.parseInt(companyArray[0][i])==searchCompanyID){
					return companyArray[1][i];
				}
			}	
		}
		return "NA";
	}
	/*
	 * getCompany ID takes in a company name as a parameter to find the corresponding company id. It looks through the 2d company id and searches for a certain company name.
	 * If it finds the company, it returns the company num/id.
	 * If it went through all the filled columns (signified by finding the null), it will create a new company with the searchCompanyName and return the id of the new company.
	*/
	public int getCompanyID(String searchCompanyName){
		for (int i=0; i<companyArray[0].length;i++){
			if(companyArray[0][i]!=null){
				if (companyArray[1][i].equals(searchCompanyName)){
					return Integer.parseInt(companyArray[0][i]);
				}
			}
			if (companyArray[0][i]==null){
				companyArray[0][i]= String.valueOf(i+1); //credit to w3schools for String.valueOf() method
				System.out.println("\nNew company successfully created!");
				companyArray[1][i]=searchCompanyName;
				return i+1;
			}	
		}
		return -1;
	}
	/*
	 * manualAdd allows the user to interact with the program to manually register attendees that were not loaded through pre-registration. It presents the option to either add a definite
	 * or indefinite number, which helps with convenience. To manually add attendees, the program prompts the user to enter the required info using a specific format (similar to the one in the txt file)
	 * and uses a similar process (splitting up individual info with delimiter) to extract information to create an instantiation of the attendee object; since the id is not explicitly known by the
	 * person registering, the program will start those who register with a manual id relative to the last id of manual registration and increment it every time someone manually registers. The program
	 * will present the user with a notification if the attendee was successfully added or not and a warning if the max # of attendees is being approached
	*/ 	
    public void manualAdd(){ //manual registration method
		Scanner scan2 = new Scanner(System.in);
		System.out.print("How many attendees do you wish to add? If the amount is indefinite, type NA:");
		//definite quantity case
		String manualAttendeeResponseNum = scan2.nextLine();
		if (!(manualAttendeeResponseNum.equals("NA"))){
			int amtManualAttendees = Integer.parseInt(manualAttendeeResponseNum);
			for(int i=0; i<amtManualAttendees; i++){
				System.out.print("To add attendee, fill out the following information\n\nFirst Name: ");
				String fNManual = scan2.nextLine();
				System.out.print("Last Name: ");
				String lNManual = scan2.nextLine();
				System.out.print("Company name: ");
				String cnManual = scan2.nextLine();
				int cNumManual = getCompanyID(cnManual);
				conferenceArray[manualID]=new Attendee(fNManual, lNManual, manualID, cnManual, cNumManual); //creates new instance of Attendee on gathered info
				manualID++;
				System.out.println("\nSuccessfully added!\n\n");
			}
		}
		//indefinite quantity case
		else {
			for(int i=aFileSize; i<aArraySize; i++){
				System.out.print("Continue? If yes, press any key except for q. If not, press q. \n");
				String lineManual = scan2.nextLine();
				if (lineManual.equals("q")){ 
					break; //credit to w3schools for break
				}	
				System.out.print("To add attendee, fill out the following information\n\nFirst Name: ");
				String fNManual = scan2.nextLine();
				System.out.print("Last Name: ");
				String lNManual = scan2.nextLine();
				System.out.print("Company name: ");
				String cnManual = scan2.nextLine();
				int cNumManual = getCompanyID(cnManual);
				conferenceArray[manualID]=new Attendee(fNManual, lNManual, manualID, cnManual, cNumManual); //creates new instance of Attendee on gathered info
				manualID++;
				System.out.println("\nSuccessfully added!\n\n");
				if (i==(aArraySize-2)){
					System.out.println("WARNING: ONLY ONE MORE SPOT AVAILABLE!");
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
                tablesAttendee[r][c]=null;
            }
        }
    }
    /*
     * isValid is a method which checks if an attendee can be placed at the table by looking for 2 conditions; a person from the same company cannot be also sitting at the table (uses 2d array tablesCompNum
     * for that purpose) & there is a vacancy present (uses tablesID for that purpose--looking for a -1); the boolean free satisfies the purpose of checking the latter condition
    */
    public boolean isValid(int tableNum, int compNumSearch){
        boolean free = false; //defaults to false; modified later to be true if space found
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
                break; //credit to w3schools for break
            }
        }
    }
    /*
     * organizeTables calls the isValid and place methods to run the task of assigning attendees to tables. It first resets all the arrays of the tables
     * so it does not place the attendees already placed twiced. It goes through each attendee with a for loop, checks each table with a for loop nested
     * in the attendee for loop, and if a table can accomodate him/her, the attendee is placed with the place method and break is used to exit out of the nested loop (so an attendee is not placed
     * at multiple tables)
    */
    public void organizeTables(){
		fillArray();
		boolean attendeePlaced;
		int numNotPlaced = 0;
		String oConc = "";
        for(int a=0; a<conferenceArray.length; a++){
            attendeePlaced = false;
            for (int t=0; t<numTables; t++){
				if (conferenceArray[a]!=null){
					if (isValid(t,conferenceArray[a].getCompNum())){
						place(t, conferenceArray[a].getCompNum(), conferenceArray[a].getID(), conferenceArray[a]);
						attendeePlaced = true;
						break; //credit to w3schools for break
					}
				}
            }
            if(attendeePlaced==false && conferenceArray[a]!=null){
				numNotPlaced++;
				oConc = oConc + "\n\n- " + conferenceArray[a].getFirst() + " " + conferenceArray[a].getLast() + " from " + conferenceArray[a].getCompany() + " was not seated due to lack of availability";		
			}	
        }
        oConc = "\n\nOrganized!\n" + oConc + "\n\n";
        System.out.println("\n\n" + oConc);
    }
    /*
     * checkConditions checks if there are more attendees from each company than there are supposed to be. It employs nested for loops. It goes through each company and for each company goes through
     * each Attendee. With getCompNum method, it counts how many members each company has and if that number exceeds the threshold, that company is added to a list of companies which have too many
     * attendees. This list along with instructions on how to handle the issue by user is then returned to be printed.
    */
    public String checkConditions(){
		int pplPerCompany;
		String cMaxConditionResponse = ""; 
		for(int c=0;c<companyArray[0].length;c++){
			pplPerCompany=0;
			if (companyArray[0][c]!=null){
				for(int a=0;a<conferenceArray.length;a++){
					if(conferenceArray[a]!=null){
						if(Integer.parseInt(companyArray[0][c])==conferenceArray[a].getCompNum()){
							pplPerCompany++; //counter determines # for a specific company	
						}	
					}	
				}	
			}
			if(pplPerCompany>maxPplPerCompany){ //compares counter to max allowed
				cMaxConditionResponse = cMaxConditionResponse + "- too many attendees from company " + companyArray[1][c] + "\n";
			}		
		}
		if (cMaxConditionResponse.equals("")){
			return "";
		} else {	
			return "\n\nPlease fix the following issues and then rerun program: \n\n" + cMaxConditionResponse + "\nThanks!\n\n";
		}
	}
	/*
	 * findPlaceTens tracker finds the limit to which a number exist (e.g. hundreds, tens, thousands). It is used to find how many places
	 * the aArraySize and cArraySize are to (and thus individual id and company numb/id) to therefore assist when printing it out w/ correct alignment 
	 * in the printTables methods
	*/
	public int findPlaceTens(int numFindTens){
		int n=1;
		int tensTracker=1;
		while (n<numFindTens){
			n*=10;
			tensTracker++;
		}
		return tensTracker;	
	}		
    /*
     * printIDTablesArray uses two for loops (1 nested) to circulate through the table of IDs and print them out. 
     * It serves a useful tool in debugging/testing but also provides useful information to the attendees to find where they are based
     * on id.
    */	
    public void printIDTablesArray(){
        System.out.print("Seating by Individual ID ('s' signifies seat and 't' signifies table; -1 signifies empty spot) \n\n"); //provides interpretation for stranger values
        for (int u=0; u<(1+findPlaceTens(numTables)+1);u++){
			System.out.print(" ");
		}	
        for (int o=0; o<pplPerTable; o++){
			System.out.printf("%" + lenA + "s", "s" + (o+1)); //credit to w3schools for text alignment
		}	
        System.out.println();
        for (int t=0; t<numTables; t++){
			System.out.printf("t" + "%" + findPlaceTens(numTables) + "s ", t+1); //credit to w3schools for text alignment
            for (int a=0; a<pplPerTable; a++){
                System.out.printf("%" + lenA + "s", tablesID[t][a]); //credit to w3schools for text alignment
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
                System.out.printf("%" + lenC + "s", tablesCompNum[t][a]); //credit to w3schools for text alignment
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
        System.out.print("Seating by First Name ('s' signifies seat and 't' signifies table) \n\n");
        for (int u=0; u<(1+findPlaceTens(numTables)+1);u++){ 
			System.out.print(" ");
		}	
        for (int o=0; o<pplPerTable; o++){
			System.out.printf("%-10s", "s" + (o+1)); //credit to w3schools for text alignment
		}	
        System.out.println();
        for (int t=0; t<numTables; t++){
            System.out.printf("t" + "%" + findPlaceTens(numTables) + "s ", t+1); //credit to w3schools for text alignment
            for (int a=0; a<pplPerTable; a++){
                if (tablesAttendee[t][a]!=null){
                    System.out.printf("%-10s", (tablesAttendee[t][a].getFirst())); //credit to w3schools for text alignment
                }
                else {
                    System.out.printf("%-10s", "NA"); //credit to w3schools for text alignment
                }
            }
            System.out.print("\n");
        }
    }
    /*
     * add searchPerson method uses the first and last name of a person (through scanner) to pinpoint the person's position in the tables of attendees array (w/ other details aboout the person)
     * and if not, return the person's info w/o seat position. If not found anywhere, return error message.
    */
    public String searchPerson(){
		System.out.println("\n\n\nSearch for a person's position and details by entering his/her first and last names\n\n"); //directions
		Scanner scanSearch = new Scanner(System.in);
		System.out.print("Enter first name: ");
		String fName = scanSearch.nextLine();
		System.out.print("Enter last name: ");
		String lName = scanSearch.nextLine();
		String searchPersonConc = "\nDetails for " + fName + " " + lName + "\n";
		for (int a=0; a<numTables;a++){ //handles seated case
			for (int b=0; b<pplPerTable;b++){
				if(tablesAttendee[a][b]!=null){
					if (tablesAttendee[a][b].getFirst().equals(fName) && tablesAttendee[a][b].getLast().equals(lName)){
						searchPersonConc = searchPersonConc + "Table: " + String.valueOf(a+1) + "\nSeat: " + String.valueOf(b+1) + "\n";
						searchPersonConc = searchPersonConc + "Individual ID: " + tablesAttendee[a][b].getID() + "\n";
						searchPersonConc = searchPersonConc + "Company: " + tablesAttendee[a][b].getCompany() + "\nCompany ID/Number: " + tablesAttendee[a][b].getCompNum() + "\n\n";
					}
				}	
			}	
		}
		if (searchPersonConc.equals("\nDetails for " + fName + " " + lName + "\n")){ //handles not seated case
			for (int e=0; e<conferenceArray.length;e++){
				if(conferenceArray[e]!=null){
					if (conferenceArray[e].getFirst().equals(fName) && conferenceArray[e].getLast().equals(lName)){
						searchPersonConc = "Not Seated\n";
						searchPersonConc = searchPersonConc + "Individual ID: " + conferenceArray[e].getID() + "\n";
						searchPersonConc = searchPersonConc + "Company: " + conferenceArray[e].getCompany() + "\nCompany ID/Number: " + conferenceArray[e].getCompNum() + "\n\n";
					}
				}	
			}
		}	
		if (searchPersonConc.equals("Details for " + fName + " " + lName + "\n")){ //if searchPersonConc is unedited/no person found --> return error message
			return "\nError: Attendee Not Found\n\n";
		}
		return searchPersonConc;			
	}	
    /*
     * grabTableInfo method uses the table number (through Scanner) to access information of the table and print the info out through a for loop.
    */
    public String grabTableInfo(){
		//needs error handling
		System.out.println("\n\nEnter the table # to get info for that table");
		Scanner scanTable = new Scanner(System.in);
		System.out.print("Enter the table #: ");
		int tableNumScan = Integer.parseInt(scanTable.nextLine());
		//error handling; provides directions if error is found
		if(tableNumScan<1 || tableNumScan>numTables){
			return "error: table does not exist\nplease enter a number from 1 to " + numTables + "\nThanks\n\n";
		}	
		int occupants = 0;
		String grabTableInfoConc = "Details for Table #"+ tableNumScan + "\n\n";
		for (int c=0; c<pplPerTable; c++){
			if (tablesAttendee[tableNumScan-1][c]!=null){
				grabTableInfoConc = grabTableInfoConc + "Seat " + String.valueOf(c+1) + "\n";
				grabTableInfoConc = grabTableInfoConc + "Name: " + tablesAttendee[tableNumScan-1][c].getFirst()+ " " +tablesAttendee[tableNumScan-1][c].getLast() + "\n";
				grabTableInfoConc = grabTableInfoConc + "Individual ID: " + tablesAttendee[tableNumScan-1][c].getID() + "\n";
				grabTableInfoConc = grabTableInfoConc + "Company Name: " + tablesAttendee[tableNumScan-1][c].getCompany() + "\n";
				grabTableInfoConc = grabTableInfoConc + "Company ID: " + tablesAttendee[tableNumScan-1][c].getCompNum() + "\n";
				grabTableInfoConc = grabTableInfoConc + "\n\n\n";
				occupants++; //counter keeps track of # SEATED
			}
			if (tablesAttendee[tableNumScan-1][c]==null){
				grabTableInfoConc = grabTableInfoConc + "Seat " + String.valueOf(c+1) + "\n";
				grabTableInfoConc = grabTableInfoConc + "EMPTY SEAT";
				grabTableInfoConc = grabTableInfoConc + "\n\n\n";
			}
			
		}
		grabTableInfoConc = grabTableInfoConc + "# of Occupants: " + occupants + "\n\n\n";
		return grabTableInfoConc;
	}
	/*
	 * getSeatPlacement method uses the attendee object (as an argument) along with a for loop to search through the tablesAttendee array to see if it is there. 
	 * The method will return a 1d array of length 1 with index 0 communicating table and index 1 ocmmunicating seat.
	*/
	public int[] getSeatPlacement(Attendee aSeated){
		int[] seat =new int[2];
		for(int a=0;a<numTables;a++){
			for(int b=0;b<pplPerTable;b++){
				if(aSeated==tablesAttendee[a][b]){
					seat[0]=a+1; //array starts at 0; +1 accounts for that fact
					seat[1]=b+1; //array starts at 0; +1 accounts for that fact
					return seat;
				}	
			}	
		}
		return seat;	
	}	
	
	/*
	 * getCompanyRoster uses the name of the company being searched to compare to the Attendee's companies. If those two strings are equal, add the person's details
	 * to the concactenation of the Roster. Return the concactenation in the form of a String.
	*/
	public String getCompanyRoster(){
		System.out.println("\n\nEnter the Company name to get info for that company"); //directions
		Scanner scanCompany = new Scanner(System.in);
		System.out.print("Enter Company Name: ");
		String compNameScan = scanCompany.nextLine();
		String rosterConc = "Roster for Company " + compNameScan + "\nNote: 0 for table and seat indicates that person was not seated\n\n";
		int numInComp = 0; //counter (added to concactenation) which helps user track # in company
		for (int l=0; l<conferenceArray.length; l++){
			if(conferenceArray[l]!=null){
				if(compNameScan.equals(conferenceArray[l].getCompany())){
					numInComp++;
					rosterConc = rosterConc + numInComp + ".\n";
					rosterConc = rosterConc + "Name: " + conferenceArray[l].getFirst() + " " + conferenceArray[l].getLast() + "\n";
					rosterConc = rosterConc + "ID: " + conferenceArray[l].getID() + "\n";
					rosterConc = rosterConc + "Table: " + getSeatPlacement(conferenceArray[l])[0] + "\nSeat: " + getSeatPlacement(conferenceArray[l])[1];
					rosterConc = rosterConc + "\n\n\n";
				}
			}		
		}
		//no edit to rosterConc/no roster found -->return error mesage
		if(rosterConc.equals("Roster for Company " + compNameScan + "\nNote: 0 for table and seat indicates that person was not seated\n\n")){
			return "Error: No company found";
		}	
		return rosterConc;	
		
	}
	/*
	 * menu method is meant to continually prompt the user for a certain action. It provides the user with directions, and asks the user
	 * if he/she wants to proceed. If not, the program ends, If yes, the program will execute a while do with a prompt each time, 
	 * matching the user's response with a certain method. However, if the user's prompt is QUIT, the loop ends and the program ends.
	*/
	public void menu(){
		System.out.print("\n\n\nNavigate this program with the following commands.\n\n");
		System.out.print("To manually add, press m\n");
		System.out.print("To organize the tables, press o\n");
		System.out.print("To print the table of IDs, press i\n");
		System.out.print("To print the table of First Names, press f\n");
		System.out.print("To search for a person's info, press s\n");
		System.out.print("To grab a table's info, press t\n");
		System.out.print("To get a Company's roster, press c\n");
		System.out.print("To quit, type QUIT in all caps\n\n");
		Scanner scanMenu = new Scanner(System.in);
		String lineMenu = "";
		while(!lineMenu.equals("QUIT")){
			if(lineMenu.equals("m")){
				manualAdd();
				System.out.println(checkConditions());
			}
			if(lineMenu.equals("o")){
				organizeTables();
			}
			if(lineMenu.equals("i")){
				printIDTablesArray();
				System.out.println();
			}
			if(lineMenu.equals("f")){
				printFirstNameTablesArray();
				System.out.println();
			}
			if(lineMenu.equals("s")){
				System.out.println(searchPerson());
			}
			if(lineMenu.equals("t")){
				System.out.println(grabTableInfo());
			}
			if(lineMenu.equals("c")){
				System.out.println(getCompanyRoster());
			}
			System.out.print("Enter a command: ");
			lineMenu = scanMenu.nextLine(); //prompt and answer at end so while do loop can check for response QUIT before action
		}
	}				
}
