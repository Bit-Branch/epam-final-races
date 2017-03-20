package by.malinouski.horserace.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admin" })
public class AdminServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractServlet#AbstractServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	
    }

}
