package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.command.receiver.factory.CommandReceiverFactory;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.listener.AsyncResultsListener;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesResults;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admin" })
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
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.putAll(request.getParameterMap());

		new CommandReceiverFactory(requestMap).getReceiver().act();
		response.sendRedirect(request.getHeader(RequestConsts.REFERER));
    }

}

//		request.getRequestDispatcher(PathConsts.HOME).forward(request, response);
//		response.sendRedirect(PathConsts.ROOT + requestMap.get(RequestMapKeys.REDIRECT_PATH));
//			request.getRequestDispatcher("/async").forward(request, response);