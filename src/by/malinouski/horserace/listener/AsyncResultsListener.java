package by.malinouski.horserace.listener;

import java.util.Enumeration;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

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
    	HttpServletRequest request = (HttpServletRequest)arg0.getSuppliedRequest();
    	logger.debug(request.getSession().getId());
    	Enumeration<String> attrs = request.getAttributeNames();
    	while (attrs.hasMoreElements()) {
    		logger.debug(request.getAttribute(attrs.nextElement()));
    	}
    }

	/**
     * @see AsyncListener#onError(AsyncEvent)
     */
    public void onError(AsyncEvent arg0) throws java.io.IOException { 
         // TODO Auto-generated method stub
    	logger.debug("error");
    	arg0.getAsyncContext().complete();
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
