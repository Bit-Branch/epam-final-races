package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.DeclareRoles;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.command.receiver.initiator.CommandInitiator;
import by.malinouski.horserace.connection.ConnectionPool;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.entity.User;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/main")
public class MainServlet extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(MainServlet.class);
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
												throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
												throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
												throws ServletException, IOException { 	
		
		request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel().toString()));
		logger.debug(request.getParameterNames());
		
		// copy the parameters to a new request map to be used for transfer
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.putAll(request.getParameterMap());
		
		// initiate appropriate command and execute it
		CommandInitiator init = new CommandInitiator(requestMap);
		Command command = init.init();
		command.execute();
		
		// authenticate the user
		if ((Boolean) requestMap.get(RequestMapKeys.IS_LOGGED_IN)) {
			User user = (User)requestMap.get(RequestMapKeys.RESULT);
			logger.debug(user.getLogin() + user.getPassword());
			request.getSession().setAttribute("role", "user");
			Cookie cookie = new Cookie("login", user.getLogin());
			cookie.setMaxAge(60*60*24*30);
			response.addCookie(cookie);
//			request.login(user.getLogin(), user.getPassword());
//			request.authenticate(response);
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
	
//	@Override
//	public void init() throws ServletException {
//		try (ConnectionPool pool = ConnectionPool.getConnectionPool()) {
//		};
//	}
}
