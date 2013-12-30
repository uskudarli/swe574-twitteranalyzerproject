package swe574.g2.twitteranalysis.mapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.controller.LoginController;
import swe574.g2.twitteranalysis.dao.ApplicationUserDAO;

/**
 * Servlet implementation class ApplicationUserServlet
 */
@WebServlet("/v1.0/api/user/update/")
public class ApplicationUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationUserServlet() {
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
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String changedPassword = request.getParameter("changedPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		
		LoginController loginController = new LoginController();
		
		ApplicationUser user = loginController.checkUser(username, password);
		
		String json = "";
		if (user == null) {
			json = "{" +
					"  \"error\": true, " + 
					"  \"message\": \"user_not_found\" " +
					"}";
		}
		else {
			boolean result = false;
			ApplicationUserDAO dao = new ApplicationUserDAO();
			if (name != null) {
				user.setName(name);
			}
			
			if (changedPassword != null && confirmPassword != null && changedPassword.equals(confirmPassword)) {
				user.setHashedPassword(changedPassword);
			}
			
			try 
			{
				result = dao.save(user);
			} 
			catch (SQLException e) {
			}
			
			if (result) {
				json = "{" +
						"  \"user\": {" +
						"    \"userId\": \"" + user.getId() + "\", " + 
						"    \"username\": \"" + user.getEmail() + "\" " +
						"    \"name\": \"" + user.getName() + "\" " +
						"  } " + 
						"}";
			}
			else {
				json = "{" +
						"  \"error\": true, " + 
						"  \"message\": \"unknown_error\" " +
						"}";
			}
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
	}

}
