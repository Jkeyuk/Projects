package sqlitetools;

import java.util.ArrayList;
//This shows how to use the SQLiteDataBase class in an application
public class SQLiteDatabaseExample {

    public static void main(String[] args) {

        //set up database object and point it to database
        SQLiteDataBase x = new SQLiteDataBase("test.db");

        //create table
        x.createTable("NewTable1", "ID INTERGER PRIMARY KEY, NAME TEXT");

        //show newly created table
        System.out.println("List of Table After Creation");
        ArrayList<String> tables
                = x.selectRecordsConditional(
                        "sqlite_master", "name", "type='table'");

        for (String table : tables) {
            System.out.println(
                    "Table #" + (tables.indexOf(table) + 1) + ": " + table);
        }

        //Insert record into table
        x.insertRecord("NewTable1", "1 , 'Jon'");
        x.insertRecord("NewTable1", "2 , 'bon'");

        //show record just inserted
        System.out.println("records just added to NewTable1:");
        ArrayList<String> test = x.selectRecords("NewTable1", "*");
        for (String y : test) {
            System.out.println(y);
        }

        //Delete 2nd record
        x.deleteRecord("NewTable1", "ID = 2");

        //update first record
        x.updateRecord("NewTable1", "NAME = 'Jonathan'", "ID = 1");

        //show records after deletion and update
        System.out.println("The following record are left after deletion/updating:");
        test = x.selectRecords("NewTable1", "*");
        for (String y : test) {
            System.out.println(y);
        }

        //DELETE TABLE CREATED ABOVE
        x.deleteTable("NewTable1");

        //Show table has been deleted
        System.out.println("List of Table After Deletion");
        tables = x.selectRecordsConditional(
                "sqlite_master", "name", "type='table'");

        for (String table : tables) {
            System.out.println(
                    "Table #" + (tables.indexOf(table) + 1) + ": " + table);
        }

        //close connection to database
        x.closeConnections();
    }
}
