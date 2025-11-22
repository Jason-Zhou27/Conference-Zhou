public class Runner {
    //variables

    //methods

    public static void main(String[] args){
        Conference c1 = new Conference(10,10);
        c1.readFileA();
        c1.organizeTables();
        c1.printIDTablesArray();
        System.out.println();
        c1.printCompNumTablesArray();
        System.out.println();
        c1.printFirstNameTablesArray();
    }
}