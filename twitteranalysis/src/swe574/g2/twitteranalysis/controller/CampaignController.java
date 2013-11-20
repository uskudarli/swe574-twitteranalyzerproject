package swe574.g2.twitteranalysis.controller;

import swe574.g2.twitteranalysis.view.DashBoardView;

import com.vaadin.navigator.Navigator;

public class CampaignController {

	private Navigator navigator = null;
	
	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}
	
    public void addCampaign(String campaignName) {
		
	}
	
    public void removeCampaign(String campaignName) {
		
	}
    
    public void showCampaigns() {
    	navigator.navigateTo(DashBoardView.NAME);
    }
    
}
