package by.malinouski.hrace.filter;

import java.io.IOException;
import java.util.Locale;

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

import by.malinouski.hrace.constant.RequestConsts;

/**
 * Servlet Filter implementation class LocaleFilter
 */
@WebFilter(filterName = "locale")
public class LocaleFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(LocaleFilter.class);

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, 
					FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		Locale locale = new Locale(req.getParameter(RequestConsts.LANG), 
									req.getParameter(RequestConsts.COUNTRY));
		res.setLocale(locale);
		logger.debug(locale.getLanguage());
		req.getSession().setAttribute(RequestConsts.LOCALE, locale);
		res.sendRedirect(req.getHeader(RequestConsts.REFERER));
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("initiated LocaleFilter");
	}

	@Override
	public void destroy() {
	}

}
