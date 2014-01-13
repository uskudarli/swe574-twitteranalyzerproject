package swe574.g2.twitteranalysis.exec;

import java.sql.Connection;
import java.sql.SQLException;

import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.controller.QueryController;
import swe574.g2.twitteranalysis.dao.QueryDAO;
import swe574.g2.twitteranalysis.database.DatabaseConnector;

public class QueryExecuterService {

	private static QueryExecuterService instance = new QueryExecuterService();

	private static int PAUSE_TIME = 3000;
	
	static {
		System.out.println("QueryExecuterService");
		new Thread(new Runnable() {
			int requestCount = 0;
			int totalRequestCount = 0;
			
			@Override
			public void run(){
				DatabaseConnector dbInstance = DatabaseConnector.getInstance();
				Connection connection = null;
				try {
					Query[] fetchedQueries = null;
					Query query = new Query();
					query.setActive("Y");
					
					connection = dbInstance.getConnection();
					connection.setAutoCommit(false);
					
					// there is an infinite loop here
					while( true ) {
						
						try {
							fetchedQueries = new QueryDAO().get(connection, query);
							
					    	for (Query q : fetchedQueries) {
								new ExecutableQuery(q, new DefaultQueryProcessor()).execute(connection);
								connection.commit();
								
								requestCount += 1;
								totalRequestCount += 1;
					            
								try {
									// wait 1 seconds
									Thread.sleep(PAUSE_TIME);
									System.out.println("Paused.");
								} 
								catch (InterruptedException e) {
								}
							}
					    	
					    	try {
								// wait 1 seconds
								Thread.sleep(15000);
								System.out.println("Paused.");
							} 
							catch (InterruptedException e) {
							}
					    	
							System.out.println("Fetched: " + fetchedQueries.length);
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
				finally {
					if (connection != null) {
						try {
							dbInstance.closeConnection(connection);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}
	
	private QueryExecuterService() {
	}
	
}
