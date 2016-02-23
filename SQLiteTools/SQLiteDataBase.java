package sqlitetools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteDataBase {

    //where connection object will be stored
    private Connection c;
    //where statement object will be stored
    private Statement stm;
    //location where DB will be stored
    private final String dbLocation;

    //constructor for database object
    public SQLiteDataBase(String dbLocation) {
        //initialize location of database
        this.dbLocation = dbLocation;
        //register driver
        registerDrivers();
        //connect and initialize statement
        connectAndStatement();
    }

    //create table
    public void createTable(String tableName, String keys) {
        //where command to create table will be held
        String SqlStatement
                = "CREATE TABLE " + tableName + " (" + keys + ")";
        //execute query
        excecuteUpdate(SqlStatement);
    }

    //delete table
    public void deleteTable(String tableName) {
        //sql command to delete table held here
        String SqlStatement = "DROP TABLE " + tableName;
        //execute query
        excecuteUpdate(SqlStatement);
    }

    //insert record into table
    public void insertRecord(String tableName, String values) {
        //sql command to insert record
        String SqlStatement
                = "INSERT INTO " + tableName + " VALUES (" + values + ")";
        //execute query
        excecuteUpdate(SqlStatement);
    }

    //delete record from table
    public void deleteRecord(String tableName, String conditions) {
        //sql command to insert record
        String SqlStatement
                = "DELETE FROM " + tableName + " WHERE " + conditions;
        //execute query
        excecuteUpdate(SqlStatement);
    }

    //update record from table
    public void updateRecord(
            String tableName, String columValuePairs, String conditions) {
        //sql command to insert record
        String SqlStatement
                = "UPDATE " + tableName + " SET " + columValuePairs
                + " WHERE " + conditions;
        //execute query
        excecuteUpdate(SqlStatement);
    }

    //select record from table
    public ArrayList<String> selectRecords(String tableName, String colums) {
        //sql command to select record held here
        String SqlStatement
                = "SELECT " + colums + " FROM " + tableName;
        //excecute and return query
        return returnQueryAsArray(SqlStatement);
    }

    //select records from table with conditions
    public ArrayList<String> selectRecordsConditional(
            String tableName, String colums, String conditions) {
        //sql command to select record held here
        String SqlStatement
                = "SELECT " + colums + " FROM " + tableName
                + " WHERE " + conditions;
        //excecute and return query
        return returnQueryAsArray(SqlStatement);
    }

    //excecute sql query and return arraylist of rows
    public ArrayList<String> returnQueryAsArray(String sqlCommand) {
        //where result set will be stored
        ArrayList<String> resultset = new ArrayList<>();
        //excecute query
        try (ResultSet results = stm.executeQuery(sqlCommand)) {
            //extract meta data
            ResultSetMetaData rsmd = results.getMetaData();
            //number of collums in result set
            int numberOfColums = rsmd.getColumnCount();
            while (results.next()) {
                String row = "";
                //for each colum add colum value to string to form row
                for (int i = 1; i <= numberOfColums; i++) {
                    row += results.getString(i) + " ";
                }//add entire row to array
                resultset.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }//return record as array
        return resultset;
    }

    //excute update
    public void excecuteUpdate(String sqlCommand) {
        //attempt to excecute query, log any errors
        try {
            //excecute query
            stm.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //close connections
    public void closeConnections() {
        //attempt to close connections, log any errors
        try {
            stm.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //connect and initialize statement
    private void connectAndStatement() {
        //try to connect to database, log any errors
        try {
            //open connection to database
            c = DriverManager.getConnection("jdbc:sqlite:" + dbLocation);
            //intialize statement
            stm = c.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Register Drivers
    private void registerDrivers() {
        //try to register drivers, log any errors
        try {
            //register drivers
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLiteDataBase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("make sure SQLite library is added");
        }
    }
}
