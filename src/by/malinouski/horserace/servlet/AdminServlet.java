package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.command.receiver.initiator.CommandInitiator;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.constant.RequestMapKeys;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admin" })
public class AdminServlet extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(AdminServlet.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractServlet#AbstractServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
												throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
												throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

    	request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel()));
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.putAll(request.getParameterMap());
		// initiate appropriate command and execute it
		CommandInitiator init = new CommandInitiator(requestMap);
		Command command = init.init();
		command.execute();
		
		if (RequestConsts.START_RACES.equals(
							requestMap.get(RequestMapKeys.REQUEST_TYPE))) {
			
			AsyncContext async = request.startAsync();
			
			async.complete();
		}
    	
    }

}
