package swe574.g2.twitteranalysis.controller;

import swe574.g2.twitteranalysis.view.LoginView;

import com.vaadin.navigator.Navigator;

public class LoginController {

	private Navigator navigator = null;

	public LoginController() {
	}
	
	public LoginController(Navigator navigator) {
		this.navigator = navigator;
	}
	
	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}
	
	public void showLoginPage() {
		navigator.navigateTo(LoginView.NAME);
	}
	
	public void signup(String email, String password, String confirmPassword) {
		
	}
	
	public void login(String email, String password) {
		
	}
	
	public void logout() {
		
	}
	
}
