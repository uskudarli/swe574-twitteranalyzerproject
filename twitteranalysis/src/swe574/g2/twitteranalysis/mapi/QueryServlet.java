package swe574.g2.twitteranalysis.mapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.Campaign;
import swe574.g2.twitteranalysis.Query;
import swe574.g2.twitteranalysis.controller.CampaignController;
import swe574.g2.twitteranalysis.controller.LoginController;
import swe574.g2.twitteranalysis.controller.QueryController;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/v1.0/api/query/")
public class QueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet() {
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
		String campaignId = request.getParameter("campaignId");
		
		LoginController loginController = new LoginController();
		ApplicationUser user = loginController.checkUser(username, password);
		
		String json = "";
		if (user != null) {
			QueryController queryController = new QueryController();
			Query[] queries = queryController.getQueries(Integer.valueOf(campaignId));
			int index=0;
			
			json = "{" + 
					"  \"queries\": [";
			if (queries != null) {
				for (Query q : queries) {
					int index2=0;
					int index3=0;
					List<String> ikeys = q.getIncludingKeywords();
					List<String> ekeys = q.getExcludingKeywords();
					
					if (index > 0) {
						json += ",";
					}
					json += "{" +
							"    \"queryId\": \"" + q.getId() + "\", " + 
							"    \"including\": [";
					
					if (ikeys != null) {
						for (String k : ikeys) {
							if (index2 > 0) {
								json += ",";
							}
							
							json += "\"" + k + "\" ";
							
							++index2;
						}
					}
					json += "], " + 
							"    \"excluding\": [";
					if (ekeys != null) {
						for (String k : ekeys) {
							if (index3 > 0) {
								json += ",";
							}
							
							json += "\"" + k + "\" ";
							
							++index3;
						}
					}
					json += "] " + 
							"}";
					
					++index;
				}
			}
			
			json += "] }";
		}
		else {
			json = "{" +
					"  \"error\": true, " + 
					"  \"message\": \"authentication_failure\" " + 
					"}";
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
	}

}
