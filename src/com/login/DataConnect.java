package com.login;

import java.sql.Connection;
import java.sql.DriverManager;

public final class DataConnect {

/*    public static String hostName = "localhost";
    public static String portNumber = "3306";
    public static String databaseName = "stockapi";
    public static String userName = "root";
    public static String password = "";*/
    
    public static String hostName = "gator3191.hostgator.com";
    public static String portNumber = "3306";
    public static String databaseName = "findways_seproject";
    public static String userName = "findways_seproj";
    public static String password = "Rushi*1929";
    
    
    
    public static Connection getConnection() {
        try {
        	
        	/*com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
        	hostName = System.getenv("ICSI518_SERVER");
        	portNumber =System.getenv("ICSI518_PORT");
        	databaseName = System.getenv("ICSI518_DB");
        	userName = System.getenv("ICSI518_USER");
        	password = "ICSI518_PASSWORD";*/
        	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + hostName + ":" + portNumber + "/" + databaseName, userName, password);
            return con;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}
