import twitter4j.*;

import java.util.List;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class QueryServlet extends HttpServlet {
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
      
    // Use "request" to read incoming HTTP headers (e.g. cookies)
    // and HTML form data (e.g. data the user entered and submitted)
    
    // Use "response" to specify the HTTP response line and headers
    // (e.g. specifying the content type, setting cookies).
    
    PrintWriter out = response.getWriter();
    // Use "out" to send content to browser
    out.println("QueryServlet Starts...");

    Twitter twitter = new TwitterFactory().getInstance();
        try {
            Query query = new Query(request.getParameter("q"));
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);
            //System.exit(0);
        } catch (TwitterException te) {
            //te.printStackTrace();
            out.println("Failed to search tweets: " + te.getMessage());
        }

        out.flush();
  }

}

