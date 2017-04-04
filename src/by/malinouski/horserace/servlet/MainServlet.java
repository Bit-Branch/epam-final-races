package by.malinouski.horserace.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.connection.ConnectionPool;
import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.User;
import by.malinouski.horserace.parser.EntityParser;
import by.malinouski.horserace.parser.factory.EntityParserFactory;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/main", "/register", "/login"})
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(MainServlet.class);

    @Override
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
    	processRequest(request);
    	request.getRequestDispatcher(PathConsts.HOME).forward(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

    	processRequest(request);
		response.sendRedirect(PathConsts.ROOT + PathConsts.HOME);
	}
    
	protected void processRequest(HttpServletRequest request) 
													throws ServletException, IOException { 	
		
		request.getServletContext().log("Log level is enabled: " + logger.getLevel());
		
		User user = (User) request.getSession().getAttribute(RequestConsts.USER);
		logger.debug(user);
		String commandStr = request.getParameter(RequestConsts.COMMAND_PARAM);
		Command command = Command.valueOf(commandStr.toUpperCase());
		
		EntityParserFactory fact = new EntityParserFactory();
		EntityParser parser = fact.getParser(command);
		Entity reqEntity = parser.parse(request.getParameterMap(), user);
		Entity retEntity = command.execute(reqEntity);
		
		String name = retEntity.getClass().getSimpleName();
		if (RequestConsts.MESSAGE.equals(name)) {
			request.setAttribute(name, retEntity);
		} else {
			request.getSession().setAttribute(name, retEntity);
		}
	}
	
	@Override
	public void destroy() {
		logger.debug("destroying servlet");
		ConnectionPool.getConnectionPool().close();
	}
}
