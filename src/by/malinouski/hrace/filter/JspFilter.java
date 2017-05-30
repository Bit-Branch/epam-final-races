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

import by.malinouski.hrace.constant.PathConsts;
import by.malinouski.hrace.constant.RequestConsts;

// TODO: Auto-generated Javadoc
/**
 * Servlet Filter implementation class ProfileFilter.
 */
@WebFilter(filterName = "jsp")
public class JspFilter implements Filter {
	
	/**
	 * Destroy.
	 *
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * Do filter.
	 *
	 * @param request the request
	 * @param response the response
	 * @param chain the chain
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, 
			ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		if (req.getSession().getAttribute(RequestConsts.USER) != null ||
				req.getRequestURI().endsWith(PathConsts.REGISTER)) {
			chain.doFilter(request, response);
		} else {
			request.getRequestDispatcher(PathConsts.INDEX).forward(request, response);
		}
	}

	/**
	 * Inits the.
	 *
	 * @param fConfig the f config
	 * @throws ServletException the servlet exception
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
