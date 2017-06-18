package com.logixity.apps.newpopular.db;



import java.sql.*;
import java.util.List;

public class DatabaseHandler {

    public static final int DUPLICATE_PK = 1;
    static Connection con;
    private static final DatabaseHandler DATABASE_HANDLER = new DatabaseHandler();

    public static DatabaseHandler getDatabaseHandler() {
        return DATABASE_HANDLER;
    }
    private DatabaseHandler() {
        
    }

    static void connect() throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/npms_db?useSSL=false","root","admin");
        } catch(SQLException ex){
            ex.printStackTrace();
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        
    }
    public boolean isValidUser(String username, String password) throws SQLException {
        return Users.isValidUser(username, password);
    }


}
