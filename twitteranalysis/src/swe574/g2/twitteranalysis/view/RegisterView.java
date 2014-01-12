package swe574.g2.twitteranalysis.view;

import swe574.g2.twitteranalysis.controller.LoginController;
import swe574.g2.twitteranalysis.controller.RegisterController;
import swe574.g2.twitteranalysis.exception.RegistrationException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class RegisterView extends VerticalLayout implements View {

	public static final String NAME = "register";

	private TextField username;
	private TextField email;
	private PasswordField password;
	private PasswordField confirmPassword;
	private Button registerButton;

	/*@WebServlet(value = "/signup", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = RegisterView.class)
	public static class Servlet extends VaadinServlet {
		
	}*/
	
	public RegisterView() {

	}
	
	private void buildRegisterView(){
        setSizeFull();

		VerticalLayout verticalLayout = new VerticalLayout();
		
        final Panel signupPanel = new Panel("Sign Up!");
        verticalLayout.addComponent(signupPanel);
		
/*		Label header = new Label("Sign Up!");
		header.addStyleName("h1");
		verticalLayout.addComponent(header);*/
        
        verticalLayout.setSizeFull();
        verticalLayout.setComponentAlignment(signupPanel, Alignment.TOP_CENTER);
        signupPanel.setWidth(null);
		
		FormLayout formLayout = new FormLayout();
		
		username = new TextField("Username: ");
		formLayout.addComponent(username);
		
		email = new TextField("Email Address:");
		formLayout.addComponent(email);
		
		password = new PasswordField("Password: ");
		formLayout.addComponent(password);
		
		confirmPassword =  new PasswordField("Confirm Password: ");
		formLayout.addComponent(confirmPassword);
		
		registerButton = new Button("Sign up");
		
		registerButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				RegisterController controller = new RegisterController(getUI());
				try {
					controller.register(username, email, password, confirmPassword);
					Notification n = new Notification("Registered Successfully", "Redirecting to login page...", Notification.Type.HUMANIZED_MESSAGE);
					n.setDelayMsec(2000);
					n.show(Page.getCurrent());
					
					new LoginController(getUI()).showLoginPage();
				} 
				catch (RegistrationException e) {

					switch (e.getType()) {
					case RegistrationException.DIFFERENT_PASSWORDS:
						System.err.println(e.getMessage());
						break;

					case RegistrationException.EXISTING_USER:
						System.err.println(e.getMessage());
						break;

					default:
					}

				}
			}
		});
		formLayout.addComponent(registerButton);
		
		signupPanel.setContent(formLayout);
		
		addComponent(verticalLayout);
	}


	@Override
	public void enter(ViewChangeEvent request) {
		buildRegisterView();
	}
}
