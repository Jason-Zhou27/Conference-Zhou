/**
 * Attendee.java
 * @author Jason Zhou
 * @since (date) 11/19/2025
 * This class assists in the creation of Attendee objects
*/
public class Attendee {
    //variables
    private String firstName;
    private String lastName;
    private int idNum;
    private String cName;
    private int cNumber;

    //constructor
    public Attendee(String f, String l, int idN, String cNm, int cNum){
        firstName = f;
        lastName = l;
        idNum = idN;
        cName = cNm;
        cNumber = cNum;
    }

    public Attendee(String f, String l, int idN, int cNum){
        firstName = f;
        lastName = l;
        idNum = idN;
        cNumber = cNum;
    }

    //methods
    public String getFirst(){
        return firstName;
    }
    public String getLast(){
        return lastName;
    }
    public int getID(){
        return idNum;
    }
    public String getCompany(){
        return cName;
    }
    public int getCompNum(){
        return cNumber;
    }
}
