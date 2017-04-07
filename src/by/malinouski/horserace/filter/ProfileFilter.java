package by.malinouski.horserace.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.PathConsts;
import by.malinouski.horserace.constant.RequestConsts;

/**
 * Servlet Filter implementation class ProfileFilter
 */
@WebFilter("/jsp/profile.jsp")
public class ProfileFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(ProfileFilter.class);

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, 
			ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		logger.debug(req.getSession().getAttribute(RequestConsts.USER));
		if (req.getSession().getAttribute(RequestConsts.USER) != null) {
			logger.info("chaining");
			chain.doFilter(request, response);
		} else {
			logger.info("redirecting");
			request.getRequestDispatcher(PathConsts.INDEX).forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("initiated profile filter");
	}

}
