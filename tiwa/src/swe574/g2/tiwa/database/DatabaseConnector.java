package swe574.g2.tiwa.database;

import java.sql.Connection;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

public class DatabaseConnector {

    static
    {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
    }

    private static DatabaseConnector instance = new DatabaseConnector();

    private DatabaseConnector() {
    }

    public static DatabaseConnector getInstance() {
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
