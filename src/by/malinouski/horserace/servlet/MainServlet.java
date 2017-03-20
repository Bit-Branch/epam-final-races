package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.connection.ConnectionPool;
import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.logic.entity.User;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(urlPatterns={"/main", "/register", "/login"})
public class MainServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MainServlet.class);

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
												throws ServletException, IOException { 	
		
		request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel().toString()));
		
		// parent's method
		execCommand(request);
		
		// authenticate the user
		Object res = requestMap.get(RequestMapKeys.IS_LOGGED_IN);
		if (res != null && (Boolean) res) {
			User user = (User)requestMap.get(RequestMapKeys.RESULT);
			request.getSession().setAttribute(
						RequestConsts.ROLE_ATTR_KEY, user.getRole().toString());
			request.getSession().setAttribute(
						RequestConsts.LOGIN_ATTR_KEY, user.getLogin());
		}
		
		response.sendRedirect(
				PathConsts.ROOT + requestMap.get(RequestMapKeys.REDIRECT_PATH));
	}
	
	@Override
	public void destroy() {
		ConnectionPool.getConnectionPool().close();
	}
}
