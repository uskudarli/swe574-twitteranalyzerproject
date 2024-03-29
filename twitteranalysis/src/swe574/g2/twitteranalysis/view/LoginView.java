package swe574.g2.twitteranalysis.view;

import swe574.g2.twitteranalysis.controller.LoginController;
import swe574.g2.twitteranalysis.controller.RegisterController;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;


@SuppressWarnings("serial")
public class LoginView extends CustomComponent implements View, ClickListener {

	public static final String NAME = "login";

	private final TextField username;
	private final PasswordField password;
	private final Button loginButton;
	private final Image loginLogo;
	private final Button signupButton;
	
	public LoginView() {
		setSizeFull();

		loginLogo = new Image(null, new ThemeResource("images/login_logo.jpg"));
		loginLogo.setWidth("300px");
		// loginLogo.setHeight("300px");
		
		// Create the user input field
		username = new TextField("Username:");
		username.setWidth("300px");
		username.setRequired(true);
		username.setInputPrompt("Your username (eg. su@boun.edu.tr)");
		// username.addValidator(new EmailValidator("Username must be an email address"));
		username.setInvalidAllowed(false);

		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		// password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		signupButton = new Button("Register");
		signupButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				RegisterController controller = new RegisterController(getUI());
				controller.showRegisterView();
			}
		});
		
		// Create login button
		loginButton = new Button("Login", this);
		
		HorizontalLayout hl = new HorizontalLayout(loginButton, signupButton);

		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(loginLogo, username, password, hl);
		fields.setCaption("Please login to access the application. (test@test.com / 123)");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout(fields);
		// viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_WHITE);
		setCompositionRoot(viewLayout);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		username.focus();		
	}

	@SuppressWarnings("unused")
	private static final class PasswordValidator extends AbstractValidator<String> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3439412027138892788L;

		public PasswordValidator() {
			super("The password provided is not valid");
		}

		@Override
		protected boolean isValidValue(String value) {
			//
			// Password must be at least 4 characters long and contain at least
			// one number
			//
			if (value != null
					&& (value.length() < 1 || !value.matches(".*\\d.*"))) {
				return false;
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		//
        // Validate the fields using the navigator. By using validators for the
        // fields we reduce the amount of queries we have to use to the database
        // for wrongly entered passwords
        //
       if (!this.username.isValid() || !this.password.isValid()) {
           return;
       }

       String username = this.username.getValue();
       String password = this.password.getValue();

       boolean loggedIn = new LoginController(getUI()).login(username, password);
       if (!loggedIn) {
    	   // Wrong password clear the password field and refocuses it
           this.password.setValue(null);
           this.password.focus();
       }
	}


}
