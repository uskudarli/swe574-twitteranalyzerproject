package swe574.g2.twitteranalysis.view;

import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.Query;
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

	public QueryView() {
		
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
        campaignCmb.setTextInputAllowed(true);
        campaignCmb.setNullSelectionAllowed(false);
        campaignCmb.setRequired(true);
        campaignCmb.setImmediate(true);
        campaignCmb.setScrollToSelectedItem(true);
        campaignCmb.sanitizeSelection();
        campaignController.loadUserCampaigns(campaignCmb);
        
        formLayout.addComponent(campaignCmb);
        
        final ComboBox queryCombobox = new ComboBox("Query Title:");
        queryCombobox.setNewItemsAllowed(true);
        queryCombobox.setTextInputAllowed(true);
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
					queryController.loadQueries(queryCombobox, campaignCmb.getValue());
					
				}else{
					queryCombobox.removeAllItems();
					queryCombobox.select(queryCombobox.getNullSelectionItemId());
				}
			}
		});
        
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
				if(queryCombobox.getValue() != null && queryCombobox.getValue() instanceof Query){
					
					queryController.loadIncludingKeywords(queryCombobox, includesList);
					queryController.loadExcludingKeywords(queryCombobox, excludesList);
				}else{
					includesList.removeAllItems();
					excludesList.removeAllItems();
				}
			}
		});
		
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
        
        formLayout.addComponent(submitButton);

        toolbar.addComponent(formLayout);
	}

}
