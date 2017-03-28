package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Future;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.command.receiver.CommandReceiver;
import by.malinouski.horserace.command.receiver.factory.CommandReceiverFactory;
import by.malinouski.horserace.listener.AsyncResultsListener;
import by.malinouski.horserace.listener.RaceResultsAttributeListener;
import by.malinouski.horserace.logic.entity.Entity;

/**
 * Servlet implementation class AsyncServlet
 */
//@WebServlet(asyncSupported = true, urlPatterns = { "/placeBet" })
public class AsyncServlet extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(AsyncServlet.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see AbstractServlet#AbstractServlet()
     */
    public AsyncServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel()));
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.putAll(request.getParameterMap());

		CommandReceiver receiver = new CommandReceiverFactory(requestMap).getReceiver();
		Optional<Queue<? extends Future<? extends Entity>>> opt = receiver.act();	
		
	}

}
