package swe574.g2.twitteranalysis.view;

import swe574.g2.twitteranalysis.controller.LoginController;
import swe574.g2.twitteranalysis.controller.RegisterController;
import swe574.g2.twitteranalysis.exception.RegistrationException;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class RegisterView extends CustomComponent implements View {

	public static final String NAME = "register";

	private final TextField username;
	private final PasswordField password;
	private final PasswordField confirmPassword;
	private final Button registerButton;

	public RegisterView() {

		username = new TextField("Username: ");
		password = new PasswordField("Password: ");
		confirmPassword =  new PasswordField("Confirm Password: ");
		registerButton = new Button("Sign up");
		registerButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				RegisterController controller = new RegisterController(getUI());
				try {
					controller.register(username, password, confirmPassword);
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
		
		VerticalLayout vl = new VerticalLayout(username, password, confirmPassword, registerButton);
		vl.setSizeFull();
		vl.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
		vl.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
		vl.setComponentAlignment(confirmPassword, Alignment.MIDDLE_CENTER);
		vl.setComponentAlignment(registerButton, Alignment.MIDDLE_CENTER);
		vl.setStyleName(Reindeer.LAYOUT_WHITE);
		setCompositionRoot(vl);
	}


	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
