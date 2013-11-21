package swe574.g2.twitteranalysis.controller;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public abstract class AbstractController {

	private UI ui = null;
	
	public AbstractController() {
		// TODO Auto-generated constructor stub
	}
	
	public AbstractController(UI ui) {
		this.ui = ui;
	}
	
	public void setUI(UI ui) {
		this.ui = ui;
	}
	
	public UI getUI() {
		return this.ui;
	}
	
	public Navigator getNavigator() {
		return getUI().getNavigator();
	}
	
	public VaadinSession getSession() {
		return getUI().getSession();
	}
	
}
