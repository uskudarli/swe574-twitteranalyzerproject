package swe574.g2.twitteranalysis.view;

import swe574.g2.twitteranalysis.controller.CampaignController;
import swe574.g2.twitteranalysis.controller.LoginController;
import swe574.g2.twitteranalysis.controller.QueryController;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class DashBoardView extends CustomComponent implements View {

	public static final String NAME = "";
    		
	Label text = new Label();
	ComboBox campaignsComboBox = new ComboBox();
	Button addCampaign = new Button("Add Campaign");
	Button removeCampaign = new Button("Remove Campaign");
	
    Button logout = new Button("Logout", new Button.ClickListener() {
		@Override
        public void buttonClick(ClickEvent event) {
			new LoginController(getUI()).logout();
        }
    });
    
    Button showQueryViewButton = new Button("Show Query", new Button.ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
			getSession().setAttribute("campaign_name", campaignsComboBox.getValue());
			new QueryController(getUI()).showQueryPage(String.valueOf( campaignsComboBox.getValue() ));
		}
	});

    public DashBoardView() {

    	HorizontalLayout hLayout = new HorizontalLayout(campaignsComboBox, showQueryViewButton);
    	HorizontalLayout hLayout2 = new HorizontalLayout(addCampaign, removeCampaign);

    	VerticalLayout vLayout = new VerticalLayout(text, hLayout, hLayout2, new Label(""), logout);
    	//vLayout.setComponentAlignment(text, Alignment.MIDDLE_LEFT);
    	vLayout.setStyleName(Reindeer.LAYOUT_WHITE);
        setCompositionRoot(vLayout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // Get the user name from the session
        String message = "Hello " + String.valueOf(getSession().getAttribute("user_name")) + ", please select your campaign to continue.";

        // And show the username
        text.setValue(message);
        
        new CampaignController(getUI()).loadUserCampaigns(campaignsComboBox);
    }

}
