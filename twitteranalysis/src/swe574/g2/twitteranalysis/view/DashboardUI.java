package swe574.g2.twitteranalysis.view;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.controller.LoginController;
import swe574.g2.twitteranalysis.dao.ApplicationUserDAO;
import swe574.g2.twitteranalysis.dao.CampaignDAO;
import swe574.g2.twitteranalysis.dao.QueryDAO;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("dashboard")
@Title("Twitter Analyzer")
public class DashboardUI extends UI {
    private static final long serialVersionUID = 1L;

    CssLayout root = new CssLayout();

    VerticalLayout loginLayout;

    CssLayout menu = new CssLayout();
    CssLayout content = new CssLayout();
    
    private ApplicationUser user;
    
    HashMap<String, String> menuNames = new HashMap<String, String>();
    
    HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>();
    private String [] views;
    
    
    HashMap<String, Button> viewNameToMenuButton = new HashMap<String, Button>();

    private Navigator nav;
    
	@SuppressWarnings("serial")
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = DashboardUI.class)
	public static class Servlet extends VaadinServlet {
	}

    @Override
    protected void init(VaadinRequest request) {    	
		try 
		{
			(new ApplicationUserDAO()).init();
			(new CampaignDAO()).init();
			(new QueryDAO()).init();
		} 
		catch (SQLException e) {
			System.err.println("database table control failed.");
		}
		 	
        setLocale(Locale.US);

        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();

        Label bg = new Label();
        bg.setSizeUndefined();
        bg.addStyleName("login-bg");
        root.addComponent(bg);

        buildLoginView(false);
        
    }

    @SuppressWarnings("serial")
	private void buildLoginView(boolean exit) {
        if (exit) {
            root.removeAllComponents();
        }

        addStyleName("login");

        loginLayout = new VerticalLayout();
        loginLayout.setSizeFull();
        loginLayout.addStyleName("login-layout");
        root.addComponent(loginLayout);

        final CssLayout loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");

        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("80%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        loginPanel.addComponent(labels);

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName("h4");
        labels.addComponent(welcome);
        labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

        Label title = new Label("Twitter Analyzer");
        title.setSizeUndefined();
        title.addStyleName("h3");
        title.addStyleName("light");
        labels.addComponent(title);
        labels.setComponentAlignment(title, Alignment.MIDDLE_CENTER);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.focus();
        fields.addComponent(username);

        final PasswordField password = new PasswordField("Password");
        fields.addComponent(password);

        final Button signin = new Button("Sign In");
        signin.addStyleName("default");
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

		final ShortcutListener enter = new ShortcutListener("Sign In",
                KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                signin.click();
            }
        };

        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if (new LoginController(getUI()).login(username.getValue(), password.getValue())) {
                    signin.removeShortcutListener(enter);
                    
                    user = (ApplicationUser)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user");
                    
                    buildMainView();
                } else {
                    if (loginPanel.getComponentCount() > 2) {
                        loginPanel.removeComponent(loginPanel.getComponent(2));
                    }
                    Label error = new Label(
                            "Wrong username.<span>Hint: try 'bkara' for admin view, '2012719081' for student view <br> or 'ucaglayan' for instructor view with no password.</span>",
                            ContentMode.HTML);
                    error.addStyleName("error");
                    error.setSizeUndefined();
                    error.addStyleName("light");
                    error.addStyleName("v-animate-reveal");
                    loginPanel.addComponent(error);
                    username.focus();
                }
            }
        });

        signin.addShortcutListener(enter);

        loginPanel.addComponent(fields);

        loginLayout.addComponent(loginPanel);
        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    @SuppressWarnings("serial")
	private void buildMainView() {
    	views = new String[] {"query", "reports", "settings"};  
    	routes.put("/query", QueryView.class);
    	routes.put("/reports", ReportsView.class);
    	routes.put("/settings", SettingsView.class);
    	
        menuNames.put("/query", "Submit Query");
        menuNames.put("/reports", "Reports");
        menuNames.put("/settings", "Settings");
    	
        nav = new Navigator(this, content);

        for (String route : routes.keySet()) {
            nav.addView(route, routes.get(route));
        }

        removeStyleName("login");
        root.removeComponent(loginLayout);

        root.addComponent(new HorizontalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                addComponent(new VerticalLayout() {
                    // Sidebar
                    {
                        addStyleName("sidebar");
                        setWidth(null);
                        setHeight("100%");

                        // Branding element
                        addComponent(new CssLayout() {
                            {
                                addStyleName("branding");
                                Label logo = new Label(
                                        "<span>Twitter Analyzer</span> Dashboard",
                                        ContentMode.HTML);
                                logo.setSizeUndefined();
                                addComponent(logo);
                                // addComponent(new Image(null, new
                                // ThemeResource(
                                // "img/branding.png")));
                            }
                        });

                        // Main menu
                        addComponent(menu);
                        setExpandRatio(menu, 1);

                        // User menu
                        addComponent(new VerticalLayout() {
                            {
                                setSizeUndefined();
                                addStyleName("user");

                                Label userName = new Label(user.getName());
                                userName.setSizeUndefined();
                                addComponent(userName);

                                Button exit = new NativeButton("Exit");
                                exit.addStyleName("icon-cancel");
                                exit.setDescription("Sign Out");
                                addComponent(exit);
                                exit.addClickListener(new ClickListener() {
                                    @Override
                                    public void buttonClick(ClickEvent event) {
                                        buildLoginView(true);
                                    }
                                });
                            }
                        });
                    }
                });
                // Content
                addComponent(content);
                content.setSizeFull();
                content.addStyleName("view-content");
                setExpandRatio(content, 1);
            }

        });

        menu.removeAllComponents();

        for (final String view : views) {
            Button b = new NativeButton(menuNames.get("/"+view));
            b.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    clearMenuSelection();
                    event.getButton().addStyleName("selected");
                    if (!nav.getState().equals("/" + view))
                        nav.navigateTo("/" + view);
                }
            });

            if (view.equals("reports")) {
                // Add drop target to reports button
                DragAndDropWrapper reports = new DragAndDropWrapper(b);
                reports.setDragStartMode(DragStartMode.NONE);
                reports.setDropHandler(new DropHandler() {

                    @Override
                    public void drop(DragAndDropEvent event) {
                        clearMenuSelection();
                        viewNameToMenuButton.get("/reports").addStyleName(
                                "selected");
                        autoCreateReport = true;
                        items = event.getTransferable();
                        nav.navigateTo("/reports");
                    }

                    @Override
                    public AcceptCriterion getAcceptCriterion() {
                        return AcceptItem.ALL;
                    }

                });
                menu.addComponent(reports);
                menu.addStyleName("no-vertical-drag-hints");
                menu.addStyleName("no-horizontal-drag-hints");
            } else {
                menu.addComponent(b);
            }

            viewNameToMenuButton.put("/" + view, b);
        }
        menu.addStyleName("menu");
        menu.setHeight("100%");

        String f = Page.getCurrent().getUriFragment();
        if (f != null && f.startsWith("!")) {
            f = f.substring(1);
        }
        if (f == null || f.equals("") || f.equals("/")) {
            nav.navigateTo(routes.keySet().iterator().next());
            menu.getComponent(0).addStyleName("selected");
        } else {
            nav.navigateTo(f);
            viewNameToMenuButton.get(f).addStyleName("selected");
        }

    }

    private Transferable items;

    private void clearMenuSelection() {
        for (Iterator<Component> it = menu.getComponentIterator(); it.hasNext();) {
            Component next = it.next();
            if (next instanceof NativeButton) {
                next.removeStyleName("selected");
            } else if (next instanceof DragAndDropWrapper) {
                // Wow, this is ugly (even uglier than the rest of the code)
                ((DragAndDropWrapper) next).iterator().next()
                        .removeStyleName("selected");
            }
        }
    }

    void updateReportsButtonBadge(String badgeCount) {
        viewNameToMenuButton.get("/reports").setHtmlContentAllowed(true);
        viewNameToMenuButton.get("/reports").setCaption(
                "Reports<span class=\"badge\">" + badgeCount + "</span>");
    }

    boolean autoCreateReport = false;
    Table transactions;

    public void openReports(Table t) {
        transactions = t;
        autoCreateReport = true;
        nav.navigateTo("/reports");
        clearMenuSelection();
        viewNameToMenuButton.get("/reports").addStyleName("selected");
    }


}
