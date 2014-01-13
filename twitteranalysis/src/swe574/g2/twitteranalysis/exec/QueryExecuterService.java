package swe574.g2.twitteranalysis.exec;

import java.sql.Connection;
import java.sql.SQLException;

import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.controller.QueryController;
import swe574.g2.twitteranalysis.dao.QueryDAO;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class QueryExecuterService {

	private static QueryExecuterService instance = new QueryExecuterService();

	private static int PAUSE_TIME = 30000;
	
	static {
		System.out.println("QueryExecuterService");
		new Thread(new Runnable() {
			int requestCount = 0;
			int totalRequestCount = 0;
			
			@Override
			public void run(){
				DatabaseConnector dbInstance = DatabaseConnector.getInstance();
				try {
					Query[] fetchedQueries = null;
					Query query = new Query();
					query.setActive("Y");
					
					// there is an infinite loop here
					while( true ) {
						try {
							Connection connection = null;
							
							connection = dbInstance.getConnection();
							fetchedQueries = new QueryDAO().get(connection, query);
							System.out.println("Fetched: " + fetchedQueries.length);
							
					    	for (Query q : fetchedQueries) {
								new ExecutableQuery(q, new DefaultQueryProcessor()).execute(connection);
								
								requestCount += 1;
								totalRequestCount += 1;
					            
								try {
									// wait n seconds
									Thread.sleep(PAUSE_TIME);
									System.out.println("Paused inner.");
								} 
								catch (InterruptedException e) {
								}
							}
					    	
					    	dbInstance.closeConnection(connection);
					    	
					    	try {
					    		System.gc();
					    		
								// wait 1 seconds
								Thread.sleep(15000);
								System.out.println("Paused.");
							} 
							catch (InterruptedException e) {
							}
					    	
							
						} 
						catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					
					
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private QueryExecuterService() {
	}
	
}
