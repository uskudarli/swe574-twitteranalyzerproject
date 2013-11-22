package edu.boun.cmpe.swe574.twanalyzer.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class QueryView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		setSizeFull();

		VerticalLayout content = new VerticalLayout();
		content.setWidth("100%");
		content.setSpacing(true);
		content.setMargin(true);
		content.addStyleName("toolbar");

		Label title = new Label("Submit Query");
		title.addStyleName("h1");
		title.setSizeUndefined();
		content.addComponent(title);
		content.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

		HorizontalLayout horizontal = new HorizontalLayout();

		TextField queryField = new TextField("Query String:");
		queryField.setWidth("370px");
		horizontal.addComponent(queryField);
		horizontal.setWidth("30%");
		horizontal.setComponentAlignment(queryField, Alignment.BOTTOM_LEFT);
		horizontal.setExpandRatio(queryField, 0.9f);

		Button btnAdd = new Button("Add");
		horizontal.addComponent(btnAdd);
		horizontal.setComponentAlignment(btnAdd, Alignment.BOTTOM_LEFT);

		/*Table sample = new Table();
        sample.setSizeFull();
        sample.setSelectable(true);
        sample.setMultiSelect(true);
        sample.setImmediate(true);
        
        sample.setVisibleColumns(new String[] { "Query Strings"});
        sample.setColumnHeaders(new String[] { "Query Strings"});

        sample.setRowHeaderMode(RowHeaderMode.ICON_ONLY);

		content.addComponent(sample);*/
        
		content.addComponent(horizontal);
		
		PopupDateField startDate = new PopupDateField("From:");

		startDate.setValue(new java.util.Date());
		startDate.setResolution(PopupDateField.RESOLUTION_DAY);
		startDate.setImmediate(true);
		content.addComponent(startDate);

		PopupDateField endDate = new PopupDateField("Till:");
		endDate.setValue(new java.util.Date());
		endDate.setResolution(PopupDateField.RESOLUTION_DAY);
		endDate.setImmediate(true);
		content.addComponent(endDate);

		Button btnSubmit = new Button("Submit");
		content.addComponent(btnSubmit);

		addComponent(content);
	}
}