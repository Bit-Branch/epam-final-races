package by.malinouski.hrace.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.malinouski.hrace.command.Command;
import by.malinouski.hrace.connection.ConnectionPool;
import by.malinouski.hrace.constant.PathConsts;
import by.malinouski.hrace.constant.RequestConsts;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.User;
import by.malinouski.hrace.parser.EntityParser;
import by.malinouski.hrace.parser.EntityParserFactory;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(urlPatterns = {"/main", "/register", "/login", "/updatePass"})
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		response.sendRedirect(request.getHeader(RequestConsts.ORIGIN) 
										+ PathConsts.ROOT + PathConsts.HOME);
	}
    
	/**
	 * Process request.
	 *
	 * @param request the request
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void processRequest(HttpServletRequest request) 
													throws ServletException, IOException { 	
		
		User user = (User) request.getSession().getAttribute(RequestConsts.USER);
		String commandStr = request.getParameter(RequestConsts.COMMAND_PARAM);
		Command command = Command.valueOf(commandStr.toUpperCase());
		
		EntityParserFactory fact = new EntityParserFactory();
		EntityParser parser = fact.getParser(command);
		Entity reqEntity = parser.parse(request.getParameterMap(), user);
		Entity retEntity = command.execute(reqEntity);
		String entityName = retEntity.ofType().getValue();
		
		request.getSession().setAttribute(entityName, retEntity);
	}
	
	@Override
	public void destroy() {
		ConnectionPool.getInstance().close();
	}
}
