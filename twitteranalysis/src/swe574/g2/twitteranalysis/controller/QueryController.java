package swe574.g2.twitteranalysis.controller;

import java.sql.SQLException;
import java.util.List;

import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.dao.QueryDAO;
import swe574.g2.twitteranalysis.exception.QueryException;
import swe574.g2.twitteranalysis.exec.QueryExecuter;
import swe574.g2.twitteranalysis.view.QueryView;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
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
	
	public void addQuery(List<String> includingKeywords, List<String> excludingKeywords) throws QueryException {
		Query query = new Query();
		query.setIncludingKeywords(includingKeywords);
		query.setExcludingKeywords(excludingKeywords);
		query.setCampaignId((Integer)getSession().getAttribute("active_campaign_id"));
		
		QueryDAO dao = new QueryDAO();
		try {
			dao.save(query);
		} 
		catch (SQLException e) {
			throw new QueryException(e.getMessage());
		}
	}
	public boolean addQuery(List<String> includingKeywords, List<String> excludingKeywords, int campaignId) {
		Query query = new Query();
		query.setIncludingKeywords(includingKeywords);
		query.setExcludingKeywords(excludingKeywords);
		query.setCampaignId(campaignId);
		
		QueryDAO dao = new QueryDAO();
		boolean ret = false;
		try {
			ret = dao.save(query);
		} 
		catch (SQLException e) {
		}
		
		return ret;
	}
	
	public void removeQuery(ComboBox queriesComboBox, String keyword, String type) throws QueryException {
		Query query = (Query)queriesComboBox.getValue();
		if (query == null)
			throw new QueryException("Select a query to remove.");
		
		QueryDAO dao = new QueryDAO();
		try {
			dao.remove(query);
		} 
		catch (SQLException e) {
			throw new QueryException(e.getMessage());
		}
	}
	
	public void addKeyword(ComboBox queriesComboBox, String keyword, String type) {
		Query q = (Query)queriesComboBox.getValue();
		if (q != null) {
			q.addKeyword(keyword, type);
			try 
			{
				new QueryDAO().save(q);
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void saveQuery(Query q){
		if (q != null) {
			try 
			{
				new QueryDAO().save(q);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeKeyword(ComboBox queriesComboBox, String keyword, String type) {
		Query q = (Query)queriesComboBox.getValue();
		if (q != null) {
			q.removeKeyword(keyword, type);
			try 
			{
				new QueryDAO().save(q);
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void runQuery(ComboBox queriesComboBox) {
		QueryExecuter queryExecuter = new QueryExecuter();
		queryExecuter.execute( this, ((Query)queriesComboBox.getValue()) );
	}
	
	public void loadQueries(ComboBox queriesComboBox, Object campaignId) {
		System.out.println("LoadQueries");
		Query dataObject  = new Query();
		
		if(campaignId instanceof String)
			return;
		
		try 
		{
			int cId = (Integer)campaignId;
				
				dataObject.setCampaignId(cId);
				
				Query[] qs = new QueryDAO().get(dataObject);
				for (Query q : qs) {
					
					queriesComboBox.addItem(q);
					queriesComboBox.setItemCaption(q, String.valueOf(q.getId()));
					
				}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Query[] getQueries(int campaignId) {
		Query dataObject  = new Query();
		
		try 
		{
			int cId = (Integer)campaignId;
			dataObject.setCampaignId(cId);
			
			Query[] qs = new QueryDAO().get(dataObject);
			return qs;
		} 
		catch (SQLException e) {
		}
		
		return null;
	}
	
	public void loadIncludingKeywords(ComboBox queriesComboBox, ListSelect list) {
		System.out.println("loadIncludings");
		Query q = (Query)queriesComboBox.getValue();
		list.removeAllItems();
		if (q != null) {
			for (String i : q.getIncludingKeywords()) {
				list.addItem(i);
			}			
		}
	}
	
	public void loadExcludingKeywords(ComboBox queriesComboBox, ListSelect list) {
		System.out.println("loadExcludings");
		Query q = (Query)queriesComboBox.getValue();
		list.removeAllItems();
		if (q != null) {
			for (String i : q.getExcludingKeywords()) {
				list.addItem(i);
			}
		}
	}
	
	public void notifyQueryCompleted(Query query) {
		Notification.show("Query completed!");
	}
}
