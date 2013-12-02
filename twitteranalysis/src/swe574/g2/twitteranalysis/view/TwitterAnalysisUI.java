package swe574.g2.twitteranalysis.view;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import swe574.g2.twitteranalysis.controller.LoginController;
import swe574.g2.twitteranalysis.dao.ApplicationUserDAO;
import swe574.g2.twitteranalysis.dao.CampaignDAO;
import swe574.g2.twitteranalysis.dao.QueryDAO;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
//@Theme("twitteranalysis")
public class TwitterAnalysisUI{ /*extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DashboardUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		try 
		{
			(new ApplicationUserDAO()).init();
			(new CampaignDAO()).init();
			(new QueryDAO()).init();
		} 
		catch (SQLException e) {
			System.err.println("database table control failed.");
		}
		
		//
        // Create a new instance of the navigator. The navigator will attach
        // itself automatically to this view.
        //
        new Navigator(this, this);
        
        getNavigator().addView(LoginView.NAME, LoginView.class);
        getNavigator().addView(DashBoardView.NAME, DashBoardView.class);
        getNavigator().addView(QueryView.NAME, QueryView.class);
        getNavigator().addView(RegisterView.NAME, RegisterView.class);
        
        
        //
        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        //
        getNavigator().addViewChangeListener(new ViewChangeListener() {
            
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                
                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user_email") != null;
                boolean isLoginView = event.getNewView() instanceof LoginView;
                boolean isRegisterView = event.getNewView() instanceof RegisterView;

                if (!isLoggedIn && !isLoginView && !isRegisterView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    // getNavigator().navigateTo(LoginView.NAME);
                	new LoginController(getUI()).showLoginPage();
                    return false;

                } else if (isLoggedIn && (isLoginView || isRegisterView)) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;
                }

                return true;
            }
            
            @Override
            public void afterViewChange(ViewChangeEvent event) {
                
            }
        });
	}*/

}
