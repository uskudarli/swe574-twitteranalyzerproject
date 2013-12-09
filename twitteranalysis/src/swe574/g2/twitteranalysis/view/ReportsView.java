package swe574.g2.twitteranalysis.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ReportsView extends VerticalLayout implements View{
	
	public void enter(ViewChangeEvent event){
        VerticalLayout toolbar = new VerticalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        addComponent(toolbar);

        Label header = new Label("Reports");
        header.addStyleName("h1");
        toolbar.addComponent(header);
	}
}
