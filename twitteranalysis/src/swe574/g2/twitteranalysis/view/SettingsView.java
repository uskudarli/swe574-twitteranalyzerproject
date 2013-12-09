package swe574.g2.twitteranalysis.view;


import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class SettingsView extends VerticalLayout implements View{

	@Override
	public void enter(ViewChangeEvent event) {
        VerticalLayout toolbar = new VerticalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        addComponent(toolbar);

        Label header = new Label("Settings");
        header.addStyleName("h1");
        toolbar.addComponent(header);
        
        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        formLayout.setSizeUndefined();
        
        TextField nameField = new TextField("Name:");
        formLayout.addComponent(nameField);
        
        TextField lastnameField = new TextField("Lastname:");
        formLayout.addComponent(lastnameField);
        
        final TextField emailField = new TextField("E-mail Address:");
        emailField.addValidator(new EmailValidator(
				"Email must contain '@' and have full domain."));
        formLayout.addComponent(emailField);
        
        CheckBox emailNtfSwitch = new CheckBox("Send Email Notifications");
        formLayout.addComponent(emailNtfSwitch);
        
        CheckBox phoneNtfSwitch = new CheckBox("Show Phone Notifications");
        formLayout.addComponent(phoneNtfSwitch);
        
        Button updateButton = new Button("Update");
        updateButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(!emailField.isValid())
					return;
			}
		});
        
        formLayout.addComponent(updateButton);
        
        toolbar.addComponent(formLayout);
        
        
	}
	
}
