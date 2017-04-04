package by.malinouski.horserace.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import by.malinouski.horserace.command.Command;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.exception.NoRacesScheduledException;
import by.malinouski.horserace.listener.AsyncResultsListener;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.entity.User;
import by.malinouski.horserace.logic.racing.RacesResults;
import by.malinouski.horserace.parser.EntityParser;
import by.malinouski.horserace.parser.factory.EntityParserFactory;

/**
 * Servlet implementation class AsyncServlet
 */
//@WebServlet(asyncSupported = true, urlPatterns = { "/placeBet" })
public class AsyncServlet extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(AsyncServlet.class);
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		AsyncContext async = request.startAsync();
		async.addListener(new AsyncResultsListener());
	
		try {
			LocalDateTime datetime = 
					(LocalDateTime) request.getServletContext()
								.getAttribute(RequestConsts.NEXT_RACE_DATETIME);
			
			Future<Race> future = RacesResults.getInstance().getFutureRace(datetime);
			Race race = (Race) future.get();
			logger.debug(race);
			request.getServletContext().setAttribute(RequestConsts.RACE, race);
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e);
		} catch (NoRacesScheduledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		async.dispatch();
		async.complete();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		processRequest(request, response);
		response.sendRedirect(request.getHeader(RequestConsts.REFERER));
	}

	protected void processRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel()));
		
		AsyncContext async = request.startAsync();
		
		User user = (User) request.getSession().getAttribute(RequestMapKeys.USER);
		String commandStr = request.getParameter(RequestConsts.COMMAND_PARAM);
		Command command = Command.valueOf(commandStr.toUpperCase());
		
		EntityParserFactory fact = new EntityParserFactory();
		EntityParser parser = fact.getParser(command);
		Entity reqEntity = parser.parse(request.getParameterMap(), user);
		Entity retEntity = command.execute(reqEntity);
		
//		if (opt.isPresent()) {
//			Bet bet = (Bet) opt.get();
//			logger.debug(bet);
//			request.getServletContext().setAttribute(
//											RequestConsts.BET, bet);
//			Gson gson = new GsonBuilder().create();
//			HttpSession session = request.getSession();
//			session.setAttribute(RequestConsts.BET, bet);
//			session.setAttribute(RequestConsts.USER, bet.getUser());
//			response.setContentType("application/json");
//			response.getWriter().write(gson.toJson(bet));
//		}
		
		async.complete();

	}

}
