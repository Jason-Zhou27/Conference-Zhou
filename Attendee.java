public class Attendee {
    //variables
    private String firstName;
    private String lastName
    private String cName;
    private String cNumber;

    //constructor
    public Attendee(String f, String l, String cNm, String cNum){
        firstName = f;
        lastName = l;
        cName = cNm;
        cNumber = cNum;
    }

    //methods
    public String getFirst(){
        return firstName;
    }
    public String getLast(){
        return lastName;
    }
    public String getCompany(){
        return cName;
    }
    public int getCompNum(){
        return cNum;
    }
}