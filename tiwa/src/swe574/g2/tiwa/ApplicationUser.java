package swe574.g2.tiwa;

import java.util.List;

public class ApplicationUser {
	private int id;
	private String email;
	private String name;

	private List<Campaign> campaigns;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Campaign> getCampaigns() {
		return this.campaigns;
	}
	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}
	public void addCampaign(Campaign campaign) {
		this.campaigns.add(campaign);
	}
	public void removeCampaign(String name) {
		this.campaigns.remove(this.getCampaign(name));
	}
	public Campaign getCampaign(String name) {
		if (name == null) {
			return null;
		}
		
		Campaign campaign = null;
		for (Campaign c : campaigns) {
			if (name.equals( c.getName() )) {
				campaign = c;
				break;
			}
		}
		
		return campaign;
	}
	
}
