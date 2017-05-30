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
	private static Logger logger = LogManager.getLogger(AsyncServlet.class);
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		AsyncContext async = request.startAsync();
		async.setTimeout(NumericConsts.ASYNC_TIMEOUT);
		async.start(() -> {
			try {
				RacesResults races = RacesResults.getInstance();
				Optional<Future<Race>> optFuture = races.getUpcomingRace();
				if (optFuture.isPresent()) {
					Race race = (Race) optFuture.get().get();
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
					response.getWriter().write(gson.toJson(race));
				}
			} catch (InterruptedException | ExecutionException e) {
				logger.error(e);
			} catch (IOException e) {
				logger.error(e);
			}
			async.complete();
		});
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(RequestConsts.USER);
		String commandStr = request.getParameter(RequestConsts.COMMAND_PARAM);
		Command command = Command.valueOf(commandStr.toUpperCase());
		EntityParserFactory fact = new EntityParserFactory();
		EntityParser parser = fact.getParser(command);
		
		Entity requestEnt = parser.parse(request.getParameterMap(), user);
		Entity returnEnt = command.execute(requestEnt);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		if (returnEnt.ofType() == EntityType.FUTURE_ENTITY) {
			FutureEntity<? extends Entity> futureEnt = (FutureEntity<?>) returnEnt;
			AsyncContext async = request.startAsync();
			async.setTimeout(NumericConsts.ASYNC_TIMEOUT);
			async.start(() -> {
				try {
					Entity entity = (Entity) futureEnt.get();
					session.setAttribute(entity.getClass().getSimpleName(), entity);
					response.getWriter().write(gson.toJson(entity));
				} catch (InterruptedException | ExecutionException e) {
					logger.error("Thread was interrupted" + e);
				} catch (IOException e) {
					logger.error(e);
				}
				async.complete();
			});
		} else if (returnEnt.ofType() == EntityType.MESSAGE) {
			response.getWriter().write(gson.toJson(returnEnt));
		}
	}

}
