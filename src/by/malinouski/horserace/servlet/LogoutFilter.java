/**
 * 
 */
package by.malinouski.horserace.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author makarymalinouski
 *
 */
@WebFilter(value={"/logout"})
public class LogoutFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(LogoutFilter.class);
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		request.getSession().invalidate();
		logger.debug("session " + request.getSession(false));
		request.getRequestDispatcher("/index.jsp").forward(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("LogoutFilter initiated");
	}


	
}
