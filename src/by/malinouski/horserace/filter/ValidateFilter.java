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

import by.malinouski.horserace.constant.RequestConsts;

/**
 * Servlet Filter implementation class ValidateFilter
 */
@WebFilter(asyncSupported = true, urlPatterns = {"/login", "/register"})
public class ValidateFilter implements Filter {

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$";

	/**
     * Default constructor. 
     */
    public ValidateFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, 
									ServletResponse response, FilterChain chain)
														throws IOException, ServletException {
		if (request.getParameter(RequestConsts.PASSWORD_ATTR_KEY)
												.matches(PASSWORD_REGEX)) {
			chain.doFilter(request, response);
		} else {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			String result = "Please match requested pattern";
			
			String path = req.getHeader(RequestConsts.REFERER);
			res.getWriter().println(result);
//								.replace(PathConsts.ROOT, UtilStringConsts.EMPTY);
//			req.setAttribute(RequestMapKeys.RESULT, result);
//			req.getRequestDispatcher(path).include(req, res);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}
}
