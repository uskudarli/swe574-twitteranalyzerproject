package swe574.g2.twitteranalysis.mapi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.controller.CampaignController;
import swe574.g2.twitteranalysis.controller.LoginController;

/**
 * Servlet implementation class CampaignServlet
 */
@WebServlet("/v1.0/api/campaign/add/")
public class CampaignAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CampaignAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO remove this method; must be used only for test purposes
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String campaignName = request.getParameter("campaignName");
		String campaignDescription = request.getParameter("campaignDescription");
		
		LoginController loginController = new LoginController();
		ApplicationUser user = loginController.checkUser(username, password);
		
		String json = "";
		if (user != null && campaignName != null) {
			CampaignController campaignController = new CampaignController();
			boolean result = campaignController.addCampaign(campaignName, campaignDescription, user.getId());
			
			Campaign[] campaigns = campaignController.getCampaigns(user.getId());
			int index=0;
			
			json = "{" + 
					"  \"campaigns\": [";
			if (campaigns != null) {
				for (Campaign c : campaigns) {
					if (index > 0) {
						json += ",";
					}
					json += "{" +
							"    \"campaignId\": \"" + c.getId() + "\", " + 
							"    \"name\": \"" + c.getName() + "\", " +
							"    \"description\": \"" + c.getDescription() + "\" " +
							"} ";
					++index;
					if (campaignName != null && campaignName.equals(c.getName())) {
						break;
					}
				}
			}
			
			json += "] }";
		}
		else {
			if (user == null) {
				json = "{" +
						"  \"error\": true, " + 
						"  \"message\": \"authentication_failure\" " + 
						"}";
			}
			else 
			{
				json = "{" +
						"  \"error\": true, " + 
						"  \"message\": \"define a name for campaign\" " + 
						"}";
			}
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
	}

}
