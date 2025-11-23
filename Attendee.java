/**
 * Attendee.java
 * @author Jason Zhou
 * @since (date) 11/19/2025
 * This class assists in the creation of Attendee objects
*/
/*
 * Attendee class outlines the properties and methods of the Attendee object. It includes two constructors and object methods (e.g.
 * getFirst(), getLast(), getID(), getCompany(), & get CompNum. Calls to Attendee constructor and methods are in Conference class
*/
public class Attendee {
    //variables
    private String firstName;
    private String lastName;
    private int idNum;
    private String cName;
    private int cNumber;

    //constructors
    
    /*
     * Attendee constructor w/ 5 arguments outlining the Attendee's first and last name, id, company name, and company Number/id;
     * assigns the characteristics of the Attendee object to the values in those arguments
    */
    public Attendee(String f, String l, int idN, String cNm, int cNum){
        firstName = f;
        lastName = l;
        idNum = idN;
        cName = cNm;
        cNumber = cNum;
    }
    /*
     * Attendee constructor w/ 4 arguments outlining the Attendee's first and last name, id, and company Number/id;
     * assigns the characteristics of the Attendee object to the values in those arguments.
     * Lacks company name because in early stages of testing, searching the company name was not employed; as of 11/23/25, this constructor
     * is not in use, but may be used for some other purpose later.
    */
    public Attendee(String f, String l, int idN, int cNum){
        firstName = f;
        lastName = l;
        idNum = idN;
        cNumber = cNum;
    }

    //methods
    /*
     * getFirst is a getter method which returns an attendee object's first name
    */
    public String getFirst(){
        return firstName;
    }
    /*
     * getLast is a getter method which returns an attendee object's last name
    */
    public String getLast(){
        return lastName;
    }
    /*
     * getID is a getter method which returns an attendee object's distinct id
    */
    public int getID(){
        return idNum;
    }
    /*
     * getCompany is a getter method which returns an attendee object's company name
    */
    public String getCompany(){
        return cName;
    }
    /*
     * getCompNum is a getter method which returns an attendee object's company id/number
    */
    public int getCompNum(){
        return cNumber;
    }
}
