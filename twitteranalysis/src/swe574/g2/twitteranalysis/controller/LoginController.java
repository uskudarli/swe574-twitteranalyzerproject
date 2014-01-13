package swe574.g2.twitteranalysis.controller;

import java.sql.SQLException;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.dao.ApplicationUserDAO;
import swe574.g2.twitteranalysis.view.LoginView;

import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
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
	
	public ApplicationUser signup(String email, String password, String confirmPassword) {
		ApplicationUser queryUser = new ApplicationUser();
		queryUser.setEmail(email);
		queryUser.setHashedPassword(password);
		boolean saveResult = false;
		try
		{
			saveResult = new ApplicationUserDAO().save(queryUser);
		}
		catch (SQLException sqle) {
		}
		
		if (saveResult) {
			queryUser = checkUser(email, password);
		}
		
		return queryUser;
	}
	
	public ApplicationUser checkUser(String username, String password) throws RuntimeException {
		ApplicationUser queryUser = new ApplicationUser();
		queryUser.setEmail(username);
		queryUser.setHashedPassword(password);
		try 
		{
			ApplicationUser[] users = new ApplicationUserDAO().get(queryUser);
			if (users != null && users.length > 0) {
				return users[0];
			}
		} 
		catch (SQLException e) {
		}
		
		return null;
	}
	
	public boolean login(String email, String password) throws RuntimeException {
		ApplicationUser user = checkUser(email, password);
		
		if (user != null) {
			VaadinSession session = getSession();
			if (session == null) {
				session = VaadinSession.getForSession(VaadinService.getCurrent(), VaadinService.getCurrentRequest().getWrappedSession());
			}
			
			if (session != null) {
				getSession().setAttribute("user_email", user.getEmail());
				getSession().setAttribute("user_id", user.getId());
				getSession().setAttribute("user_name", user.getName());
				getSession().setAttribute("user", user);
			}
			
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("user", user);
			
		    return true;
		}
		return false;
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
