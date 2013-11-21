package swe574.g2.twitteranalysis.controller;

import java.sql.SQLException;
import java.util.List;

import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.dao.CampaignDAO;
import swe574.g2.twitteranalysis.dao.QueryDAO;
import swe574.g2.twitteranalysis.view.QueryView;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.UI;

public class QueryController extends AbstractController {
	
	public QueryController() {
	
	}
	
	public QueryController(UI ui) {
		super(ui);
	}
	
	public void showQueryPage(String campaignName) {
		getNavigator().navigateTo(QueryView.NAME);
	}
	
	public void addQuery(List<String> includingKeywords, List<String> excludingKeywords) {
		
	}
	
	public void removeQuery(int queryId) {
		
	}
	
	public void addKeyword(int queryId, String keyword, String type) {
		
	}
	
	public void removeKeyword(int queryId, String keyword, String type) {
		
	}
	
	public void runQuery(int queryId) {
		
	}
	
	public void loadQueries(ComboBox queriesComboBox) {
		System.out.println("LoadQueries");
		Query dataObject  = new Query();
		Campaign campaign = new Campaign();
		campaign.setOwnerUserId((Integer)(getSession().getAttribute("user_id")));
		campaign.setName(String.valueOf( getSession().getAttribute("campaign_name") ));
		
		try 
		{
			Campaign[] campaigns = new CampaignDAO().get(campaign);
			if (campaigns != null && campaigns.length > 0) {
				dataObject.setCampaignId(campaigns[0].getId());
				
				Query[] qs = new QueryDAO().get(dataObject);
				for (Query q : qs) {
					
					queriesComboBox.addItem(q);
					
				}
			}
			
			
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadIncludingKeywords(String queryId, ListSelect list) {
		System.out.println("loadIncludings");
	}
	
	public void loadExcludingKeywords(String queryId, ListSelect list) {
		System.out.println("loadExcludings");
	}
}
