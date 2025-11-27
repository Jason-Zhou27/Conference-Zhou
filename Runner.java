/**
 * Runner.java
 * @author Jason Zhou
 * @since (date) 11/19/2025
 * This class is the Runner/Main/Tester class which runs the program
*/
import java.util.*;
/*
 * Runner class runs the program by calling Conference constructor and using its methods
*/
public class Runner {
    //variables

    //methods
    /*
     * Main method creates instantiation of Conference with the Conference constructor. With Conference methods, it reads Company Text File, reads Attendee Text File
     * presents user with option to manually add attendees, organizes the tables, prints the Tables w/ IDs, prints the Tables w/ Company #s, and prints Tables w/ First Names
    */
    public static void main(String[] args){
        Conference c1 = new Conference(10,10,10);
        c1.readFileC();
        c1.readFileA();
        c1.manualAdd();
        System.out.println(c1.checkConditions());
        c1.organizeTables();
        c1.printIDTablesArray();
        System.out.println();
        //c1.printCompNumTablesArray();
        System.out.println();
        c1.printFirstNameTablesArray();
        System.out.println(c1.searchPerson());
        System.out.println(c1.grabTableInfo());
    }
}
