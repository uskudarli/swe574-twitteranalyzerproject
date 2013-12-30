package swe574.g2.twitteranalysis.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import swe574.g2.twitteranalysis.controller.LoginController;

public class LoginControllerTest extends BaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSignup() {
		fail("Not yet implemented");
	}

	@Test
	public void testLogin() {
		LoginController controller = new LoginController();
		
		boolean expectedValue;
		boolean returnValue;
		String email;
		String password;

		// Login Fail Test 1
		email = "testEmail@example.com";
		password =  "testPass";
		expectedValue = false;
		returnValue = controller.login(email, password);
		assertEquals(expectedValue, returnValue);		

		// Login Fail Test 2
		email = "testEmail";
		password =  "testPass";
		expectedValue = false;
		returnValue = controller.login(email, password);
		assertEquals(expectedValue, returnValue);		

		// Login Success Test
		email = "test@example.com";
		password =  "test";
		expectedValue = true;
		returnValue = controller.login(email, password);
		assertEquals(expectedValue, returnValue);

		// Login Wrong Password Test
		email = "test@example.com";
		password =  "test1234";
		expectedValue = false;
		returnValue = controller.login(email, password);
		assertEquals(expectedValue, returnValue);	

		// Login Null Email Test
		email = null;
		password =  "test1234";
		expectedValue = false;
		returnValue = controller.login(email, password);
		assertEquals(expectedValue, returnValue);		

		// Login Null Email Test
		email = "test@example.com";
		password =  null;
		expectedValue = false;
		returnValue = controller.login(email, password);
		assertEquals(expectedValue, returnValue);		
		
	}

}
