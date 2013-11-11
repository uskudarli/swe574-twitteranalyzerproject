package edu.boun.cmpe.swe574.twanalyzer.db;

import java.sql.Connection;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

public class DBConnector {

    static
    {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) 
        {
            //mysql driver da yoksa birakalim gidelim
            e.printStackTrace();
        }
    }

    private static DBConnector instance = new DBConnector();

    private DBConnector() {
    }

    public static DBConnector getInstance() {
        return instance;
    }

    public synchronized Connection getConnection(){
        Connection connection = null;
        Context initCtx = null;
        DataSource ods = null;
        
        try
        {
            initCtx = new InitialContext();
            
            //getting map of resources from tomcat/context.xml
            ods = (DataSource) initCtx.lookup("java:comp/env/jdbc/twanalyzer");

            //getting an available connection from pool
            connection = ods.getConnection();
        }
        catch (Exception e) {
        	e.printStackTrace(System.err);
        	connection = null;
        }
        
        return connection;
    }

    
}
