package swe574.g2.twitteranalysis;

import twitter4j.Place;

public class TweetPlace {

	private String country;
	private String fullName;
	private String name;
	private String type;
	private String streetAddress;
	private String url;
	private long extTweetId;
	
	public TweetPlace() {
	}
	
	public TweetPlace( Place tweetPlace ) {
		this.country = tweetPlace.getCountry();
		this.fullName = tweetPlace.getFullName();
		this.name = tweetPlace.getName();
		this.type = tweetPlace.getPlaceType();
		this.streetAddress = tweetPlace.getStreetAddress();
		this.url = tweetPlace.getURL();
	}
	
	public TweetPlace( long extTweetId, Place tweetPlace ) {
		this(tweetPlace);
		this.extTweetId = extTweetId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getExtTweetId() {
		return extTweetId;
	}

	public void setExtTweetId(long extTweetId) {
		this.extTweetId = extTweetId;
	}

	
	
	
	
	
}
