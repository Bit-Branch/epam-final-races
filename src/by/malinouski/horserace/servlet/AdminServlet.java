package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.RequestConsts;

/**
 * Servlet implementation class AdminServlet
 */
//@WebServlet(asyncSupported = true, urlPatterns = { "/admin" })
public class AdminServlet extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(AdminServlet.class);
	private static final long serialVersionUID = 1L;
       
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
												throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
												throws ServletException, IOException {
		processRequest(req, resp);
	}
	
    protected void processRequest(HttpServletRequest request, 
    							  		HttpServletResponse response)
    									  		throws ServletException, IOException {

    	request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel()));
		
		response.sendRedirect(request.getHeader(RequestConsts.REFERER));
    }

}

