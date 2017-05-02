package by.malinouski.hrace.servlet;

import java.io.IOException;
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

import by.malinouski.hrace.command.Command;
import by.malinouski.hrace.constant.NumericConsts;
import by.malinouski.hrace.constant.RequestConsts;
import by.malinouski.hrace.listener.AsyncResultsListener;
import by.malinouski.hrace.logic.entity.Entity;
import by.malinouski.hrace.logic.entity.EntityType;
import by.malinouski.hrace.logic.entity.FutureEntity;
import by.malinouski.hrace.logic.entity.Race;
import by.malinouski.hrace.logic.entity.User;
import by.malinouski.hrace.logic.racing.RacesResults;
import by.malinouski.hrace.parser.EntityParser;
import by.malinouski.hrace.parser.EntityParserFactory;

/**
 * Servlet implementation class AsyncServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/placeBet", "/results" })
public class AsyncServlet extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(AsyncServlet.class);
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		AsyncContext async = request.startAsync();
		logger.debug("GET " + async);
		async.addListener(new AsyncResultsListener());
		async.setTimeout(NumericConsts.ASYNC_TIMEOUT);
		async.start(() -> {
			try {
				RacesResults races = RacesResults.getInstance();
				Optional<Future<Race>> optFuture = races.getUpcomingRace();
				if (optFuture.isPresent()) {
					Race race = (Race) optFuture.get().get();
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
//					request.getServletContext().setAttribute(RequestConsts.RACE, race);
					response.getWriter().write(gson.toJson(race));
				}
			} catch (InterruptedException | ExecutionException e) {
				logger.error(e);
			} catch (IOException e) {
				e.printStackTrace();
			}
			async.complete();
		});
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("before response");
		processRequest(request, response);
//		response.resetBuffer();
//		request.getRequestDispatcher(PathConsts.HOME).forward(request, response);
//		response.sendRedirect(request.getHeader(RequestConsts.REFERER));
	}

	protected void processRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		request.getServletContext().log(
				String.valueOf("Log level is enabled: " + logger.getLevel()));
		request.getParameterMap().forEach((s, sa) -> System.out.printf("%s - %s\n", s, sa[0]));
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(RequestConsts.USER);
		String commandStr = request.getParameter(RequestConsts.COMMAND_PARAM);
		Command command = Command.valueOf(commandStr.toUpperCase());
		logger.debug(command);
		EntityParserFactory fact = new EntityParserFactory();
		EntityParser parser = fact.getParser(command);
		
		Entity requestEnt = parser.parse(request.getParameterMap(), user);
		Entity returnEnt = command.execute(requestEnt);
		logger.debug(returnEnt.ofType());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		if (returnEnt.ofType() == EntityType.FUTURE_ENTITY) {
			logger.debug("in post");
			FutureEntity<? extends Entity> futureEnt = (FutureEntity<?>) returnEnt;
			AsyncContext async = request.startAsync();
			logger.debug("POST " + async);
			async.setTimeout(NumericConsts.ASYNC_TIMEOUT);
			async.addListener(new AsyncResultsListener());
			async.start(() -> {
				try {
					Entity entity = (Entity) futureEnt.get();
					session.setAttribute(entity.getClass().getSimpleName(), entity);
					logger.debug(entity);
					response.getWriter().write(gson.toJson(entity));
				} catch (InterruptedException | ExecutionException e) {
					logger.error("Thread was interrupted" + e);
				} catch (IOException e) {
					logger.error(e);
				}
				async.complete();
			});
		} else if (returnEnt.ofType() == EntityType.MESSAGE) {
//			request.setAttribute(RequestConsts.MESSAGE, returnEnt);
			response.getWriter().write(gson.toJson(returnEnt));
		}
	}

}
