package swe574.g2.twitteranalysis.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;

public class DashBoardView extends CustomComponent implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2298050859684097927L;
	
	public static final String NAME = "";
	
	Label text = new Label();

    Button logout = new Button("Logout", new Button.ClickListener() {

        /**
		 * 
		 */
		private static final long serialVersionUID = -1681553675352537475L;

		@Override
        public void buttonClick(ClickEvent event) {

            // "Logout" the user
            getSession().setAttribute("user", null);

            // Refresh this view, should redirect to login view
            getUI().getNavigator().navigateTo(NAME);
        }
    });

    public DashBoardView() {
        setCompositionRoot(new CssLayout(text, logout));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // Get the user name from the session
        String username = String.valueOf(getSession().getAttribute("user"));

        // And show the username
        text.setValue("Hello " + username);
    }

}
