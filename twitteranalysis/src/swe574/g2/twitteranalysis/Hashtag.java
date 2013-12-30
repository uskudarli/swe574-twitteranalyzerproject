package swe574.g2.twitteranalysis;

import twitter4j.HashtagEntity;

public class Hashtag {
	private int id;
	private int start;
	private int end;
	private String value;
	
	public Hashtag() {
	}
	
	public Hashtag(HashtagEntity hashtagEntity) {
		if (hashtagEntity != null) {
			this.start = hashtagEntity.getStart();
			this.end = hashtagEntity.getEnd();
			this.value = hashtagEntity.getText();
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Hashtag) || this.value == null) {
			return false;
		}
		
		return (this.start == ((Hashtag)obj).getStart() && this.end == ((Hashtag)obj).getEnd() && this.value.equals(((Hashtag)obj).getValue()));
	}
	
}
