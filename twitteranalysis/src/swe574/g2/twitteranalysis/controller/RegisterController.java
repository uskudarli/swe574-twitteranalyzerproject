package swe574.g2.twitteranalysis.controller;

import java.sql.SQLException;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.dao.ApplicationUserDAO;
import swe574.g2.twitteranalysis.exception.RegistrationException;
import swe574.g2.twitteranalysis.view.RegisterView;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class RegisterController extends AbstractController {
	public RegisterController() {
	}
	
	public RegisterController(UI ui) {
		super(ui);
	}
	
	public void register(TextField usernameField, PasswordField passwordField, PasswordField confirmField) throws RegistrationException {
		String username = usernameField.getValue();
		String password = passwordField.getValue();
		String confirm = confirmField.getValue();
		
		if (username == null || !isValidEmailAddress(username)) {
			throw new RegistrationException(RegistrationException.NOT_VALID_USER_NAME);
		} else if (password == null || confirm == null || password.equals(confirm) == false ) {
			throw new RegistrationException(RegistrationException.DIFFERENT_PASSWORDS);
		}
		
		ApplicationUser user = new ApplicationUser();
		user.setName(username);
		user.setEmail(username);
		user.setHashedPassword(password);
		
		ApplicationUserDAO dao = new ApplicationUserDAO();
		try 
		{
			dao.save(user);
		} 
		catch (MySQLIntegrityConstraintViolationException e) {
			throw new RegistrationException(RegistrationException.EXISTING_USER);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isValidEmailAddress(String email) {
		String emailRegEx = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return email.matches(emailRegEx);
	}
	
	public void showRegisterView() {
		getNavigator().navigateTo(RegisterView.NAME);
	}
}
