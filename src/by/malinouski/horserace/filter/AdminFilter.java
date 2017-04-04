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
import by.malinouski.horserace.logic.entity.User;
import by.malinouski.horserace.logic.entity.User.Role;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter("/admin")
public class AdminFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(AdminFilter.class);

    /**
     * Default constructor. 
     */
    public AdminFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, 
			ServletResponse response, FilterChain chain) 
								throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		User user = (User) req.getSession().getAttribute(RequestConsts.USER);

		if (user == null || user.getRole() != Role.ADMIN) {
			logger.debug("redirecting home");
			resp.sendRedirect(PathConsts.ROOT + PathConsts.HOME);
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("initiated AdminFilter");
	}

}
