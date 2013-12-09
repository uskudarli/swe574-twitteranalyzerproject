package swe574.g2.twitteranalysis;

import twitter4j.GeoLocation;

public class TweetLocation {
	private double latitude;
	private double longitude;
	
	public TweetLocation() {
	}
	
	public TweetLocation(double lat, double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}
	
	public TweetLocation(GeoLocation geoLocation) {
		if (geoLocation != null) {
			this.latitude = geoLocation.getLatitude();
			this.longitude = geoLocation.getLongitude();
		}
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
