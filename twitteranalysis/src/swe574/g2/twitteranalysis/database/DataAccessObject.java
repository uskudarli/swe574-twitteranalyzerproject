package swe574.g2.twitteranalysis.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataAccessObject<T> {

	public boolean init() throws SQLException;
	public boolean init(Connection connection) throws SQLException;
	public boolean save(T dataObject) throws SQLException;
	public boolean save(Connection connection, T dataObject) throws SQLException;
	public boolean remove(T dataObject) throws SQLException;
	public boolean remove(Connection connection, T dataObject) throws SQLException;
	public T[] get(T dataObject) throws SQLException;
	public T[] get(Connection connection, T dataObject) throws SQLException;
	public T[] find(String query) throws SQLException;
	public T[] find(Connection connection, String query) throws SQLException;

}
