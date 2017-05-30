package by.malinouski.hrace.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.constant.ParamsMapKeys;
import by.malinouski.hrace.constant.PathConsts;
import by.malinouski.hrace.constant.RequestConsts;
import by.malinouski.hrace.logic.entity.Message;

// TODO: Auto-generated Javadoc
/**
 * Servlet Filter implementation class ValidateFilter.
 */
@WebFilter(filterName = "validate")
public class ValidateFilter implements Filter {
	
    /** The Constant PASSWORD_REGEX. */
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$";

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
		HttpServletResponse res = (HttpServletResponse) response;
		String pass = req.getParameter(ParamsMapKeys.PASSWORD);
		Message message = new Message();
		
		if (Pattern.matches(PASSWORD_REGEX, pass)) {
			String repeat = request.getParameter(ParamsMapKeys.REPEAT_PASSWORD);
			if ( repeat == null || repeat.equals(pass)) {
				chain.doFilter(request, response);
			} else {
				message.setText(BundleConsts.PASS_DONT_MATCH);
				req.getSession().setAttribute(RequestConsts.MESSAGE, message);
				res.sendRedirect(req.getHeader(RequestConsts.REFERER));
			}
		} else {
			message.setText(BundleConsts.MATCH_PATTERN);
			req.getSession().setAttribute(RequestConsts.MESSAGE, message);
			res.sendRedirect(req.getHeader(RequestConsts.REFERER));
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
	
	/**
	 * Destroy.
	 *
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}
}
