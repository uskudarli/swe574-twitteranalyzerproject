package swe574.g2.twitteranalysis;

public class Campaign implements Comparable<Campaign> {
	private int id;
	private String name;
	private String description;
	private int ownerUserId;
	
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
	public int getOwnerUserId() {
		return ownerUserId;
	}
	public void setOwnerUserId(int ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.name == null || !(obj instanceof Campaign)) {
			return false;
		}
		
		return this.name.equals(((Campaign)obj).getName());
	}
	
	@Override
	public int compareTo(Campaign o) {
		if (o == null || this.name == null)
			return -1;
		
		return this.name.compareTo(o.name);
	}
}
