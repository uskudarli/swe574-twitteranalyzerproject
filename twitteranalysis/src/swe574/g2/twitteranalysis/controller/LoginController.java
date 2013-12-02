package swe574.g2.twitteranalysis.controller;

import java.sql.SQLException;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.dao.ApplicationUserDAO;
import swe574.g2.twitteranalysis.view.LoginView;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

public class LoginController extends AbstractController {
	
	public LoginController() {
		
	}
	
	public LoginController(UI ui) {
		super(ui);
	}
	
	public void showLoginPage() {
		getNavigator().navigateTo(LoginView.NAME);
	}
	
	public void signup(String email, String password, String confirmPassword) {
		
	}
	
	public boolean login(String email, String password) throws RuntimeException {
		System.out.println("login");
		ApplicationUser queryUser = new ApplicationUser();
		queryUser.setEmail(email);
		queryUser.setHashedPassword(password);
		try 
		{
			ApplicationUser[] users = new ApplicationUserDAO().get(queryUser);
			if (users != null && users.length > 0) {
				getSession().setAttribute("user_email", users[0].getEmail());
				getSession().setAttribute("user_id", users[0].getId());
				getSession().setAttribute("user_name", users[0].getName());
				
				// Navigate to main view
		        // getNavigator().navigateTo(DashBoardView.NAME);
//				new CampaignController(getUI()).showCampaigns();
				
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("user", users[0]);

			}
		} 
		catch (SQLException e) {
			return false;
		}
		
		return true;
	}
	
	public void logout() {
		System.out.println("logout");
        // "Logout" the user
		getSession().setAttribute("user_email", null);
		getSession().setAttribute("user_id", null);
		getSession().setAttribute("user_name", null);

        // Refresh this view, should redirect to login view
        getNavigator().navigateTo(LoginView.NAME);
	}
	
}
