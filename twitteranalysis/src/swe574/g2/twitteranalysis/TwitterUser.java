package swe574.g2.twitteranalysis;

import twitter4j.User;

public class TwitterUser {
	private String username;
	private String miniProfileImageURL;
	private String description;
	private int favouritesCount;
	private int followersCount;
	private int friendsCount;
	private int listedCount;
	private String location;
	private String name;
	private int tweetCount;
	private String url;
	
	
	public TwitterUser() {
		// TODO Auto-generated constructor stub
	}
	
	public TwitterUser(User tweetUser) {
		this.miniProfileImageURL = tweetUser.getMiniProfileImageURL();
		this.description = tweetUser.getDescription();
		this.favouritesCount = tweetUser.getFavouritesCount();
		this.followersCount = tweetUser.getFollowersCount();
		this.friendsCount = tweetUser.getFriendsCount();
		this.listedCount = tweetUser.getListedCount();
		this.location = tweetUser.getLocation();
		this.name = tweetUser.getName();
		this.username = tweetUser.getScreenName();
		this.tweetCount = tweetUser.getStatusesCount();
		this.url = tweetUser.getURL();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getMiniProfileImageURL() {
		return miniProfileImageURL;
	}

	public void setMiniProfileImageURL(String miniProfileImageURL) {
		this.miniProfileImageURL = miniProfileImageURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(int favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}

	public int getListedCount() {
		return listedCount;
	}

	public void setListedCount(int listedCount) {
		this.listedCount = listedCount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTweetCount() {
		return tweetCount;
	}

	public void setTweetCount(int tweetCount) {
		this.tweetCount = tweetCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
