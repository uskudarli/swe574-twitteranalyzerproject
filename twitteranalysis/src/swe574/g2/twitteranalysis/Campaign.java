package swe574.g2.twitteranalysis;

public class Campaign implements Comparable<Campaign> {
	private int id;
	private String name;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		return this.name.equals(((Campaign)obj).getName());
	}
	
	@Override
	public int compareTo(Campaign o) {
		if (o == null)
			return -1;
		
		return this.name.compareTo(o.name);
	}
}
