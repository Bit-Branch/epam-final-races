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
import javax.servlet.http.HttpServletResponse;

import by.malinouski.hrace.constant.PathConsts;
import by.malinouski.hrace.constant.RequestConsts;
import by.malinouski.hrace.logic.entity.User;
import by.malinouski.hrace.logic.entity.User.Role;

// TODO: Auto-generated Javadoc
/**
 * Servlet Filter implementation class AdminFilter.
 */
@WebFilter(filterName = "admin")
public class AdminFilter implements Filter {
	
    /**
     * Default constructor. 
     */
    public AdminFilter() {
    }

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
			ServletResponse response, FilterChain chain) 
								throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		User user = (User) req.getSession().getAttribute(RequestConsts.USER);

		if (user == null || user.getRole() != Role.ADMIN) {
			resp.sendRedirect(PathConsts.ROOT + PathConsts.HOME);
		} else {
			chain.doFilter(request, response);
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
