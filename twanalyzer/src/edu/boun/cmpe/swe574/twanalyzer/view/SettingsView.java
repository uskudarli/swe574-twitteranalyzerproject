package edu.boun.cmpe.swe574.twanalyzer.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SettingsView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
        setSizeFull();

		VerticalLayout content = new VerticalLayout();
		content.setWidth("100%");
		content.setSpacing(true);
		content.setMargin(true);
		content.addStyleName("toolbar");

		Label title = new Label("Settings");
		title.addStyleName("h1");
		title.setSizeUndefined();
		content.addComponent(title);
		content.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		
		addComponent(content);
	}


}
