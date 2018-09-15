package ui.helper;

import java.sql.*;


public class Jdbc {

    // JDBC driver name and database URL
    static final String DB_URL = "jdbc:oracle:thin:@ldap://oranames.testserver.com:3389/TS1_TESTAPP_1,cn=OracleContext,dc=testserver,dc=com";

    //  Database credentials
    static final String USER = "DB_LOGIN";
    static final String PASS = "PASSWORD";
    Connection conn = null;
    Statement stmt = null;
    public static Jdbc instance;

    public static synchronized Jdbc getInstance() {
        if (instance == null)
            instance = new Jdbc();

        return instance;
    }

    private Jdbc() {
        try {
            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }//end main

    public void executeStatement(String sql) {
        //STEP 4: Execute a query
        try {
            System.out.println("Creating statement...");
            conn = null;
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            rs.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {

            }
        }
    }

    public void closeConnection() {
        System.out.println("Closing a connection...");
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }//end try
    }

    public void cleanupDB() {
        Jdbc.getInstance().executeStatement("DELETE FROM TASKS WHERE USER_ID = 'TestUser')");
        Jdbc.getInstance().executeStatement("DELETE FROM INST WHERE USER_ID = 'TestUser'");
        this.closeConnection();
    }
}
