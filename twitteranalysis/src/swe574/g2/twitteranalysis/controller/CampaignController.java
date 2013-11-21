package swe574.g2.twitteranalysis.controller;

import java.sql.SQLException;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.dao.ApplicationUserDAO;
import swe574.g2.twitteranalysis.dao.CampaignDAO;
import swe574.g2.twitteranalysis.view.DashBoardView;

public class CampaignController extends AbstractController {
	
	
	public CampaignController() {
	
	}
	
	public CampaignController(UI ui) {
		super(ui);
	}
	
    public void addCampaign(String campaignName) {
		
	}
	
    public void removeCampaign(String campaignName) {
		
	}
    
    public void loadUserCampaigns(ComboBox campaignsComboBox) {
    	String username = String.valueOf(getSession().getAttribute("user_email"));
    	
    	ApplicationUser queryUser = new ApplicationUser();
    	queryUser.setEmail(username);
    	try {
			ApplicationUser[] appUsers = new ApplicationUserDAO().get(queryUser);
			if (appUsers != null && appUsers.length > 0) {
				Campaign queryCampaign = new Campaign();
				queryCampaign.setOwnerUserId(appUsers[0].getId());
				Campaign[] campaigns = new CampaignDAO().get(queryCampaign);
				
				for (Campaign c : campaigns) {
					campaignsComboBox.addItem(c.getName());
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void showCampaigns() {
    	getNavigator().navigateTo(DashBoardView.NAME);
    }
    
}
