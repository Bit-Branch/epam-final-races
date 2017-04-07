/**
 * 
 */
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
 * @author makarymalinouski
 *
 */
@WebFilter(asyncSupported = true, urlPatterns = {"/index.jsp"})
public class LoginFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(LoginFilter.class);
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("Initiated LoginFilter");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, 
	 * 						javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		if (request.getSession().getAttribute(RequestConsts.USER) != null) {
			logger.info("loggin in");
			request.getRequestDispatcher(PathConsts.HOME).forward(request, response);
		} else {
			logger.info("chaining");
			chain.doFilter(request, response);
		}
	}


}
