package by.malinouski.horserace.listener;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.RequestConsts;

/**
 * Application Lifecycle Listener implementation class AsyncListener
 *
 */
@WebListener(value="/ajax")
public class AsyncResultsListener implements javax.servlet.AsyncListener {
	private static final Logger logger = LogManager.getLogger(AsyncResultsListener.class);
    /**
     * Default constructor. 
     */
    public AsyncResultsListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see AsyncListener#onComplete(AsyncEvent)
     */
    public void onComplete(AsyncEvent arg0) throws java.io.IOException { 
         // TODO Auto-generated method stub
    	logger.debug("completed " + arg0.getSuppliedRequest());
    	Object race = arg0.getSuppliedRequest().getServletContext().getAttribute(RequestConsts.RACE);
    	logger.debug(race);
    	arg0.getSuppliedResponse().getWriter().write("");
    }

	/**
     * @see AsyncListener#onError(AsyncEvent)
     */
    public void onError(AsyncEvent arg0) throws java.io.IOException { 
         // TODO Auto-generated method stub
    }

	/**
     * @see AsyncListener#onStartAsync(AsyncEvent)
     */
    public void onStartAsync(AsyncEvent arg0) throws java.io.IOException { 
         // TODO Auto-generated method stub
    	logger.info("Async started");
    }

	/**
     * @see AsyncListener#onTimeout(AsyncEvent)
     */
    public void onTimeout(AsyncEvent arg0) throws java.io.IOException { 
         // TODO Auto-generated method stub
    }
	
}
