package swe574.g2.twitteranalysis.mapi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swe574.g2.twitteranalysis.ApplicationUser;
import swe574.g2.twitteranalysis.controller.LoginController;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/v1.0/api/signup/")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		String confirmPassword = request.getParameter("confirmPassword");
		
		LoginController loginController = new LoginController();
		ApplicationUser user = loginController.checkUser(username, password);
		
		String json = "";
		if (user != null) {
			json = "{" +
					"  \"error\": true, " + 
					"  \"message\": \"existing_user\" " +
					"}";
		}
		else {
			user = loginController.signup(username, password, confirmPassword);
			if (user != null) {
				json = "{" +
						"  \"user\": {" +
						"    \"userId\": \"" + user.getId() + "\", " + 
						"    \"username\": \"" + user.getEmail() + "\", " +
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
