package swe574.g2.twitteranalysis.controller;

import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;

import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.dao.CampaignDAO;
import swe574.g2.twitteranalysis.exception.CampaignException;
import swe574.g2.twitteranalysis.view.DashBoardView;

public class CampaignController extends AbstractController {
	
	
	public CampaignController() {
	
	}
	
	public CampaignController(UI ui) {
		super(ui);
	}
	
	public void addCampaign(String campaignName) throws CampaignException {
    	Campaign campaign = new Campaign();
		campaign.setName(campaignName);
		campaign.setOwnerUserId((Integer)getSession().getAttribute("user_id"));
		CampaignDAO dao = new CampaignDAO();
		try {
			dao.save(campaign);
		} 
		catch (MySQLIntegrityConstraintViolationException e) {
			throw new CampaignException("Campaign Already exists!");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean addCampaign(String campaignName, String campaignDescription, int userId) {
    	Campaign campaign = new Campaign();
		campaign.setName(campaignName);
		campaign.setDescription(campaignDescription);
		campaign.setOwnerUserId(userId);
		CampaignDAO dao = new CampaignDAO();
		
		boolean ret = false;
		try {
			ret = dao.save(campaign);
		} 
		catch (MySQLIntegrityConstraintViolationException e) {
		}
		catch (SQLException e) {
		}
		
		return ret;
	}
	
    public void removeCampaign(String campaignName) throws CampaignException {
    	Campaign campaign = new Campaign();
		campaign.setName(campaignName);
		campaign.setOwnerUserId((Integer)getSession().getAttribute("user_id"));
		CampaignDAO dao = new CampaignDAO();
		try {
			dao.remove(campaign);
		}
		catch (SQLException e) {
			throw new CampaignException(e.getMessage());
		}
	}
    
    public Campaign[] getCampaigns(int userId) {
    	try 
    	{
    		Campaign queryCampaign = new Campaign();
    		queryCampaign.setOwnerUserId(userId);
    		Campaign[] campaigns = new CampaignDAO().get(queryCampaign);
    		return campaigns;	
    	} 
    	catch (SQLException e) {
    
    	}
    	
    	return null;
    }
    
    public void loadUserCampaigns(ComboBox campaignsComboBox) {
//    	String username = String.valueOf(getSession().getAttribute("user_email"));
    	
    	int userId = (Integer) getSession().getAttribute("user_id");
    	
//   	ApplicationUser queryUser = new ApplicationUser();
//   	queryUser.setEmail(username);
    	try {
//			ApplicationUser[] appUsers = new ApplicationUserDAO().get(queryUser);
//			if (appUsers != null && appUsers.length > 0) {
				Campaign queryCampaign = new Campaign();
				queryCampaign.setOwnerUserId(userId);
				Campaign[] campaigns = new CampaignDAO().get(queryCampaign);
				
				for (Campaign c : campaigns) {
					campaignsComboBox.addItem(c.getId());
					campaignsComboBox.setItemCaption(c.getId(), c.getName());
				}
//			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void showCampaigns() {
    	getNavigator().navigateTo(DashBoardView.NAME);
    }
    
}
