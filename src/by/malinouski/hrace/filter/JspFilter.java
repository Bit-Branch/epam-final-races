package by.malinouski.hrace.filter;

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

import by.malinouski.hrace.constant.PathConsts;
import by.malinouski.hrace.constant.RequestConsts;

/**
 * Servlet Filter implementation class ProfileFilter
 */
@WebFilter(filterName = "jsp")
public class JspFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(JspFilter.class);

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

		if (req.getSession().getAttribute(RequestConsts.USER) != null ||
				req.getRequestURI().endsWith(PathConsts.REGISTER)) {
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
