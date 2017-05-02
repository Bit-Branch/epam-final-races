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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.hrace.constant.BundleConsts;
import by.malinouski.hrace.constant.ParamsMapKeys;
import by.malinouski.hrace.constant.PathConsts;
import by.malinouski.hrace.constant.RequestConsts;
import by.malinouski.hrace.logic.entity.Message;

/**
 * Servlet Filter implementation class ValidateFilter
 */
@WebFilter(filterName = "validate")
public class ValidateFilter implements Filter {
	private static Logger logger = LogManager.getLogger(ValidateFilter.class);
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$";

	/**
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
				logger.info("chaining");
				chain.doFilter(request, response);
			} else {
				message.setText(BundleConsts.PASS_DONT_MATCH);
				req.setAttribute(RequestConsts.MESSAGE, message);
				req.getRequestDispatcher(PathConsts.REGISTER).forward(req, res);
			}
			
		} else {
			message.setText(BundleConsts.MATCH_PATTERN);
			req.setAttribute(RequestConsts.MESSAGE, message);
			req.getRequestDispatcher(PathConsts.REGISTER).forward(req, res);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("Initiated ValidateFilter");
	}
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}
}
