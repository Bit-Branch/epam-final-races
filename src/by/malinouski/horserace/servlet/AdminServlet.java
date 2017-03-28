package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
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

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.command.receiver.CommandReceiver;
import by.malinouski.horserace.command.receiver.factory.CommandReceiverFactory;
import by.malinouski.horserace.command.receiver.initiator.CommandInitiator;
import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.listener.AsyncResultsListener;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;

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
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
												throws ServletException, IOException {
		processRequest(req, resp);
	}
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

    	request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel()));
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.putAll(request.getParameterMap());

		CommandReceiver receiver = new CommandReceiverFactory(requestMap).getReceiver();
		Optional<Queue<? extends Future<? extends Entity>>> opt = receiver.act();
		
//		request.getRequestDispatcher(PathConsts.HOME).forward(request, response);
//		response.sendRedirect(PathConsts.ROOT + requestMap.get(RequestMapKeys.REDIRECT_PATH));
		if (opt.isPresent()) {
			Queue<? extends Future<? extends Entity>> futures = opt.get();
			AsyncContext async = request.startAsync();
			async.addListener(new AsyncResultsListener());
			while (!futures.isEmpty()) {
				try {
					Future<? extends Entity> future = futures.remove();
					Race race = (Race) future.get();
					logger.debug(race);
					async.getRequest().getServletContext().setAttribute(RequestConsts.RACE, race);
				} catch (InterruptedException | ExecutionException e) {
					logger.error(e);
				}
			}
			async.complete();
//			request.getRequestDispatcher("/async").forward(request, response);
		}
		
    	
    }

}
