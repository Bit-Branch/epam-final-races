package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.command.receiver.factory.CommandReceiverFactory;
import by.malinouski.horserace.command.receiver.initiator.CommandInitiator;
import by.malinouski.horserace.connection.ConnectionPool;
import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.entity.User;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(asyncSupported = true, 
			urlPatterns = {"/main", "/register", "/login"})
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MainServlet.class);

    @Override
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
    	Map<String, Object> requestMap = processRequest(request, response);
		
    	request.setAttribute(RequestMapKeys.RESULT, 
    							requestMap.get(RequestMapKeys.RESULT));
    	request.getRequestDispatcher(
					(String) requestMap.get(RequestMapKeys.REDIRECT_PATH))
												.forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

    	Map<String, Object> requestMap = processRequest(request, response);
		Object isLoggedIn = requestMap.get(RequestMapKeys.IS_LOGGED_IN);
		
		if (isLoggedIn != null && (Boolean) isLoggedIn) {
			request.getSession().setAttribute(RequestMapKeys.USER, 
										requestMap.get(RequestMapKeys.USER));
		}
		response.sendRedirect(PathConsts.ROOT + 
						requestMap.get(RequestMapKeys.REDIRECT_PATH));
	}
    
	protected Map<String, Object> processRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException { 	
		
		request.getServletContext().log(
				"Log level is enabled: " + logger.getLevel());
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.putAll(request.getParameterMap());
		requestMap.put(RequestMapKeys.USER, 
				request.getSession().getAttribute(RequestMapKeys.USER));

		new CommandReceiverFactory(requestMap).getReceiver().act();
		
		return requestMap;
	}
	
	@Override
	public void destroy() {
		logger.debug("destroying servlet");
		ConnectionPool.getConnectionPool().close();
	}
}
