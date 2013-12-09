package swe574.g2.twitteranalysis.view;

import swe574.g2.twitteranalysis.controller.CampaignController;
import swe574.g2.twitteranalysis.controller.QueryController;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class QueryView extends VerticalLayout  implements View {

	public static final String NAME = "query";

//	private Button removeInclude = new Button("Remove Include");
	
//	private Button addExclude = new Button("Add Exclude");
//	private Button removeExclude = new Button("Remove Exclude");

	public QueryView() {
		/*queries.setImmediate(true);
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
		includesList.setImmediate(true);
		addInclude.setWidth("115px");
		removeInclude.setWidth("115px");

		includesList.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = (String)includesList.getValue();
				if (value != null) {
					keywordField.setValue(value);
				}
			}
		});
		
		excludesList.setNullSelectionAllowed(false);
		excludesList.setWidth("230px");
		excludesList.setImmediate(true);
		removeExclude.setWidth("115px");
		addExclude.setWidth("115px");

		excludesList.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = (String)excludesList.getValue();
				if (value != null) {
					keywordField.setValue(value);
				}
			}
		});
		runQuery.setWidth("80px");
		runQuery.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				QueryController queryController = new QueryController(getUI());
				queryController.runQuery(queries); 
			}
		});
		
		addInclude.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String keyword = keywordField.getValue();
				if (keyword != null && keyword.length() > 0) {
					QueryController queryController = new QueryController(getUI());
					queryController.addKeyword(queries, keyword, "including");
					queryController.loadIncludingKeywords(queries, includesList);
				}
			}
		});
		
		removeInclude.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
//				String keyword = keywordField.getValue();
				String keyword = (String)includesList.getValue();
				if (keyword != null && keyword.length() > 0) {
					QueryController queryController = new QueryController(getUI());
					queryController.removeKeyword(queries, keyword, "including");
					queryController.loadIncludingKeywords(queries, includesList);
				}
			}
		});
		
		addExclude.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String keyword = keywordField.getValue();
				if (keyword != null && keyword.length() > 0) {
					QueryController queryController = new QueryController(getUI());
					queryController.addKeyword(queries, keyword, "excluding");
					queryController.loadExcludingKeywords(queries, excludesList);
				}
			}
		});
		
		removeExclude.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String keyword = keywordField.getValue();
				if (keyword != null && keyword.length() > 0) {
					QueryController queryController = new QueryController(getUI());
					queryController.removeKeyword(queries, keyword, "excluding");
					queryController.loadExcludingKeywords(queries, excludesList);
				}
			}
		});

		keywordLabel.setWidth("100px");
		keywordField.setWidth("360px");

		HorizontalLayout hLayout = new HorizontalLayout(includesList, excludesList);
		HorizontalLayout hLayout2 = new HorizontalLayout(addInclude, removeInclude, addExclude, removeExclude);
		HorizontalLayout hLayout3 = new HorizontalLayout(queries, runQuery);
		HorizontalLayout hLayout4 = new HorizontalLayout(keywordLabel, keywordField);
		
		VerticalLayout vLayout = new VerticalLayout(hLayout3, hLayout, hLayout4, hLayout2);
		vLayout.setComponentAlignment(hLayout, Alignment.MIDDLE_CENTER);
		vLayout.setComponentAlignment(hLayout2, Alignment.MIDDLE_CENTER);
		vLayout.setComponentAlignment(hLayout3, Alignment.MIDDLE_CENTER);
		vLayout.setComponentAlignment(hLayout4, Alignment.MIDDLE_CENTER);
		vLayout.setStyleName(Reindeer.LAYOUT_WHITE);
		setCompositionRoot(vLayout);*/
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		final QueryController queryController = new QueryController(getUI());
		final CampaignController campaignController = new CampaignController(getUI());
		
        VerticalLayout toolbar = new VerticalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        addComponent(toolbar);

        Label header = new Label("Submit Query");
        header.addStyleName("h1");
        toolbar.addComponent(header);
        
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeUndefined();
        
        final ComboBox campaignCmb = new ComboBox("Campaign Name:");
        campaignCmb.setNewItemsAllowed(true);
        campaignCmb.setTextInputAllowed(false); //TODO: will be set to true
        campaignCmb.setNullSelectionAllowed(false);
        campaignCmb.setRequired(true);
        campaignCmb.setImmediate(true);
        campaignCmb.setScrollToSelectedItem(true);
        campaignController.loadUserCampaigns(campaignCmb);
        
        formLayout.addComponent(campaignCmb);
        
        final ComboBox queryCombobox = new ComboBox("Query Title:");
        queryCombobox.setNewItemsAllowed(true);
        queryCombobox.setTextInputAllowed(false); //TODO: will be set to true
        queryCombobox.setNullSelectionAllowed(false);
        queryCombobox.setRequired(true);
        queryCombobox.setImmediate(true);
        queryCombobox.setScrollToSelectedItem(true);
        //queryController.loadQueries(queryCmb);
        
        formLayout.addComponent(queryCombobox);
        
        campaignCmb.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(campaignCmb.getValue() != null && !"".equals(campaignCmb.getValue())){
					queryCombobox.removeAllItems();
					queryController.loadQueries(queryCombobox, (Integer)campaignCmb.getValue());
					
				}else{
					queryCombobox.removeAllItems();
					queryCombobox.select(queryCombobox.getNullSelectionItemId());
				}
			}
		});
        
        /*TextField queryTitle = new TextField("Query Title:");
        queryTitle.setRequired(true);
        formLayout.addComponent(queryTitle);*/
        
        HorizontalLayout keywordToolbar = new HorizontalLayout();
        keywordToolbar.setWidth("100%");
        keywordToolbar.setSpacing(true);
        keywordToolbar.addStyleName("toolbar");
        
        final TextField includeKeywordField = new TextField("Include Keyword:");
        includeKeywordField.setWidth("230px");
        keywordToolbar.addComponent(includeKeywordField);
        includeKeywordField.setImmediate(true);
        formLayout.addComponent(keywordToolbar);
        
        final TextField excludeKeywordField = new TextField("Exclude Keyword:");
        excludeKeywordField.setWidth("230px");
        excludeKeywordField.setImmediate(true);
        keywordToolbar.addComponent(excludeKeywordField);
        formLayout.addComponent(keywordToolbar);
        
		HorizontalLayout listToolbar = new HorizontalLayout();
		listToolbar.setWidth("100%");
		listToolbar.setSpacing(true);
		listToolbar.addStyleName("toolbar");
        
        final ListSelect includesList = new ListSelect();
        
        includesList.setNullSelectionAllowed(false);
		includesList.setWidth("230px");
		includesList.setImmediate(true);

        includeKeywordField.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				String keyword = includeKeywordField.getValue();
				if(!"".equals(keyword) && keyword != null){
					includesList.addItem(keyword);
					includeKeywordField.setValue("");
				}
			}
		});
		
//		final TextField keywordField = new TextField("Keyword:");
//		keywordField.setWidth("360px");

		
		/*includesList.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = (String)includesList.getValue();
				if (value != null) {
					keywordField.setValue(value);
				}
			}
		});*/
		
		listToolbar.addComponent(includesList);
        
		final ListSelect excludesList = new ListSelect();
		
		excludesList.setNullSelectionAllowed(false);
		excludesList.setWidth("230px");
		excludesList.setImmediate(true);
		
		excludeKeywordField.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				String keyword = excludeKeywordField.getValue();
				if(!"".equals(keyword) && keyword != null){
					excludesList.addItem(keyword);
					excludeKeywordField.setValue("");
				}
			}
		});
		
		
		queryCombobox.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(queryCombobox.getValue() != null && !"".equals(queryCombobox.getValue())){
					
					queryController.loadIncludingKeywords(queryCombobox, includesList);
					queryController.loadExcludingKeywords(queryCombobox, excludesList);
				}else{
					includesList.removeAllItems();
					excludesList.removeAllItems();
				}
			}
		});
		/*excludesList.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = (String)excludesList.getValue();
				if (value != null) {
					keywordField.setValue(value);
				}
			}
		});*/
		
		listToolbar.addComponent(excludesList);
		
        formLayout.addComponent(listToolbar);
		
        Button submitButton = new NativeButton("Submit");
        submitButton.setDescription("Submit");
        submitButton.addStyleName("default");
		
        submitButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				QueryController queryController = new QueryController(getUI());
				queryController.runQuery(queryCombobox);
			}
		});
        
    	/*Button addInclude = new Button("Add Include");
    	
		addInclude.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String keyword = keywordField.getValue();
				if (keyword != null && keyword.length() > 0) {
					includesList.addItem(keyword);
				}
			}
		});*/
    	
    	/*Button addExclude = new Button("Add Exclude");
        
		addExclude.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String keyword = keywordField.getValue();
				if (keyword != null && keyword.length() > 0) {
					excludesList.addItem(keyword);
				}
			}
		});*/
    	
//    	formLayout.addComponent(addInclude);
//    	formLayout.addComponent(addExclude);
//        formLayout.addComponent(keywordField);
        formLayout.addComponent(submitButton);

        toolbar.addComponent(formLayout);
	}

}
