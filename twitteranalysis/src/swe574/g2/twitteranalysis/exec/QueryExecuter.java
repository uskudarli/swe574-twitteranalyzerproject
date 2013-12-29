package swe574.g2.twitteranalysis.exec;

import java.sql.Connection;
import java.sql.SQLException;

import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.controller.QueryController;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class QueryExecuter {
	
	public void execute(final QueryController queryController, final ExecutableQuery eq) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
			 try {
				 	System.out.println("Thread start running...");
		        	DatabaseConnector dbInstance = DatabaseConnector.getInstance();
		        	Connection connection = dbInstance.getConnection();
		        	connection.setAutoCommit(false);
		        	
		        	eq.execute(connection);
		            
		            connection.commit();
		            dbInstance.closeConnection(connection);
		        } catch (SQLException e) {
		        	e.printStackTrace();
		        	// TODO: log exceptions
				}
			 	finally {
			 		if (queryController != null) {
			 			queryController.notifyQueryCompleted((Query)eq);
			 		}
			 	}
			}
		});
		
		thread.start();
	}
	
	public void execute(QueryController queryController, Query query, Processor processor) {
		this.execute( queryController, new ExecutableQuery(query, processor) );
	}
	
	public void execute(QueryController queryController, Query query) {
		this.execute( queryController, new ExecutableQuery(query, new DefaultQueryProcessor()) );
	}
	
	
	
}
