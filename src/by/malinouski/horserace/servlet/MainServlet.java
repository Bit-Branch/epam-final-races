package by.malinouski.horserace.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.connection.ConnectionPool;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.logic.entity.User;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/main")
public class MainServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MainServlet.class);

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
												throws ServletException, IOException {
		processRequest(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
												throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
												throws ServletException, IOException { 	
		
		request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel().toString()));
		logger.debug(request.getQueryString());
		
		// parent's method
		execCommand(request);
		
		// authenticate the user
		if ((Boolean) requestMap.get(RequestMapKeys.IS_LOGGED_IN)) {
			User user = (User)requestMap.get(RequestMapKeys.RESULT);
			logger.debug(user.getLogin());
			request.getSession().setAttribute(RequestConsts.ROLE_ATTR_KEY, user.getRole().toString());
			request.getSession().setAttribute(RequestConsts.LOGIN_ATTR_KEY, user.getLogin());
		}
		
		request.setAttribute(
				RequestMapKeys.RESULT, requestMap.get(RequestMapKeys.RESULT));
		
		logger.debug(requestMap.get(RequestMapKeys.REDIRECT_PATH));
		request.getRequestDispatcher((String) requestMap.get(RequestMapKeys.REDIRECT_PATH))
				.forward(request, response);

	}
	
	@Override
	public void destroy() {
		ConnectionPool.getConnectionPool().close();
	}
}
