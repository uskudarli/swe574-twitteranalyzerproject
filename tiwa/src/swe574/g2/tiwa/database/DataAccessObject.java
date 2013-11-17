package swe574.g2.tiwa.database;

public interface DataAccessObject<T> {

	public boolean save(T dataObject);
	public boolean remove(T dataObject);
	public T get(T dataObject);
	public T[] find(String query);

}
