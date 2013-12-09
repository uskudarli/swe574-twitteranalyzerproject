package swe574.g2.twitteranalysis.exec;

import java.sql.Connection;
import java.sql.SQLException;

import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class QueryExecuter {
	
	public void execute(final ExecutableQuery eq) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
			 try {
		        	DatabaseConnector dbInstance = DatabaseConnector.getInstance();
		        	Connection connection = dbInstance.getConnection();
		        	connection.setAutoCommit(false);
		        	
		        	eq.execute(connection);
		            
		            connection.commit();
		            dbInstance.closeConnection(connection);
		        } catch (SQLException e) {
		        	// TODO: log exceptions
				}
			}
		});
		
		thread.start();
	}
	
	public void execute(Query query, Processor processor) {
		this.execute( new ExecutableQuery(query, processor) );
	}
	
	public void execute(Query query) {
		this.execute( new ExecutableQuery(query, new DefaultQueryProcessor()) );
	}
	
	
	
}
