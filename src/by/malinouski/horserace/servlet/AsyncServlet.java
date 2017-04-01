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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import by.malinouski.horserace.command.receiver.factory.CommandReceiverFactory;
import by.malinouski.horserace.constant.RequestConsts;
import by.malinouski.horserace.constant.RequestMapKeys;
import by.malinouski.horserace.exception.NoRacesScheduledException;
import by.malinouski.horserace.listener.AsyncResultsListener;
import by.malinouski.horserace.logic.entity.Bet;
import by.malinouski.horserace.logic.entity.Entity;
import by.malinouski.horserace.logic.entity.Race;
import by.malinouski.horserace.logic.racing.RacesResults;

/**
 * Servlet implementation class AsyncServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/placeBet" })
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
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel()));
		
		AsyncContext async = request.startAsync();
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.putAll(request.getParameterMap());
		requestMap.put(RequestMapKeys.USER, 
				request.getSession().getAttribute(RequestMapKeys.USER));

		Optional<? extends Entity> opt = 
				new CommandReceiverFactory(requestMap).getReceiver().act();
		
		if (opt.isPresent()) {
			Bet bet = (Bet) opt.get();
			logger.debug(bet);
			request.getServletContext().setAttribute(
											RequestConsts.BET, bet);
			Gson gson = new GsonBuilder().create();
			request.getServletContext().setAttribute(RequestConsts.BET, gson.toJson(bet));
			response.setContentType("application/json");
			response.getWriter().write(gson.toJson(bet));
			response.sendRedirect(request.getHeader(RequestConsts.REFERER));
		} else {
			response.sendRedirect(request.getHeader(RequestConsts.REFERER));
		}
		
		async.complete();

	}

}
