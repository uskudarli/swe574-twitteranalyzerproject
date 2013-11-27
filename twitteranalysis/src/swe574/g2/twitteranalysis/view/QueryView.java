package swe574.g2.twitteranalysis.view;

import swe574.g2.twitteranalysis.controller.QueryController;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class QueryView extends CustomComponent implements View {

	public static final String NAME = "query";

	private ComboBox queries = new ComboBox();
	
	private ListSelect includesList = new ListSelect("Include Keyword: ");
	private Button addInclude = new Button("Add Include");
	private Button removeInclude = new Button("Remove Include");
	
	private ListSelect excludesList = new ListSelect("Exclude Keyword: ");
	private Button addExclude = new Button("Add Exclude");
	private Button removeExclude = new Button("Remove Exclude");

	private Button runQuery = new Button("Run Query");

	
	public QueryView() {
		queries.setImmediate(true);
		queries.setWidth("380px");
		queries.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				System.out.println("valueChanged");
				QueryController queryController = new QueryController(getUI());
				queryController.loadIncludingKeywords(queries, includesList);
				queryController.loadExcludingKeywords(queries, excludesList);		
			}
		});
		
		includesList.setNullSelectionAllowed(false);
		includesList.setWidth("230px");
		addInclude.setWidth("115px");
		removeInclude.setWidth("115px");
		
		excludesList.setNullSelectionAllowed(false);
		excludesList.setWidth("230px");
		removeExclude.setWidth("115px");
		addExclude.setWidth("115px");
		
		runQuery.setWidth("80px");
		runQuery.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				QueryController queryController = new QueryController(getUI());
				queryController.runQuery(queries); 
			}
		});

		HorizontalLayout hLayout = new HorizontalLayout(includesList, excludesList);
		HorizontalLayout hLayout2 = new HorizontalLayout(addInclude, removeInclude, addExclude, removeExclude);
		HorizontalLayout hLayout3 = new HorizontalLayout(queries, runQuery);
		
		VerticalLayout vLayout = new VerticalLayout(hLayout3, hLayout, hLayout2);
		vLayout.setComponentAlignment(hLayout, Alignment.MIDDLE_CENTER);
		vLayout.setComponentAlignment(hLayout2, Alignment.MIDDLE_CENTER);
		vLayout.setComponentAlignment(hLayout3, Alignment.MIDDLE_CENTER);
		vLayout.setStyleName(Reindeer.LAYOUT_WHITE);
		setCompositionRoot(vLayout);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		QueryController queryController = new QueryController(getUI());
		queryController.loadQueries(queries);
	}

}
