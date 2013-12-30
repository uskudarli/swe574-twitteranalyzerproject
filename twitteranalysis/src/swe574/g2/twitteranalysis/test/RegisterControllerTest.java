package swe574.g2.twitteranalysis.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import swe574.g2.twitteranalysis.controller.LoginController;
import swe574.g2.twitteranalysis.controller.RegisterController;
import swe574.g2.twitteranalysis.exception.RegistrationException;

public class RegisterControllerTest extends BaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test (expected = RegistrationException.class)
	public void testRegister() {
		RegisterController controller = new RegisterController();
			
		TextField usernameField;
		PasswordField passwordField;
		PasswordField confirmField;

		// Get a RegisterationException where username is not a valid username (null or invalid-email)
		usernameField = new TextField();
		usernameField.setValue("username");
		passwordField = new PasswordField();
		passwordField.setValue("password");
		confirmField = new PasswordField();
		confirmField.setValue("password");
		
		try {
			controller.register(usernameField, passwordField, confirmField);
			fail("register did not throw a RegistrationException where it was expected..");
		} catch (RegistrationException re) {
			// Success Case
		}
		
		// Get a RegisterationException where passwords does not match
		usernameField = new TextField();
		usernameField.setValue("username@example.com");
		passwordField = new PasswordField();
		passwordField.setValue("password");
		confirmField = new PasswordField();
		confirmField.setValue("passwordX");
		
		try {
			controller.register(usernameField, passwordField, confirmField);
			fail("register did not throw a RegistrationException where it was expected..");
		} catch (RegistrationException re) {
			// Success Case
		}
		
		// Registration Success Case
		usernameField = new TextField();
		usernameField.setValue("username@example.com");
		passwordField = new PasswordField();
		passwordField.setValue("password");
		confirmField = new PasswordField();
		confirmField.setValue("password");
		
		try {
			controller.register(usernameField, passwordField, confirmField);
		} catch (RegistrationException re) {
			fail("register threw a RegistrationException where it was not expected..");
		}
		
		// Get a RegisterationException where user exists
		// First: Register a user
		// Second: Try to register the same user again and get exception
		usernameField = new TextField();
		usernameField.setValue("username@example.com");
		passwordField = new PasswordField();
		passwordField.setValue("password");
		confirmField = new PasswordField();
		confirmField.setValue("password");
		
		try {
			controller.register(usernameField, passwordField, confirmField);
		} catch (RegistrationException re) {
			fail("register threw a RegistrationException where it was not expected..");
		}
		
		try {
			controller.register(usernameField, passwordField, confirmField);
			fail("register did not throw a RegistrationException where it was expected..");
		} catch (RegistrationException re) {
			// Success Case
		}
		
	}

}
