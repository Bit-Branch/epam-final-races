/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.command.receiver.initiator.CommandInitiator;

/**
 * @author makarymalinouski
 */
public abstract class AbstractServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Map of request parameters to be used in application 
	 */
	protected static ConcurrentHashMap<String, Object> requestMap;

	public AbstractServlet() {
		requestMap = new ConcurrentHashMap<>();
	}
	
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
    
    protected abstract void processRequest(
    		HttpServletRequest request, HttpServletResponse response) 
    										throws ServletException, IOException;
    
	protected void execCommand(HttpServletRequest request) {
		// copy the parameters to a new request map to be used for transfer
		requestMap.putAll(request.getParameterMap());
		System.out.println(requestMap);
		
		// initiate appropriate command and execute it
		CommandInitiator init = new CommandInitiator(requestMap);
		Command command = init.init();
		command.execute();
	}

}
