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

import by.malinouski.hrace.constant.RequestConsts;

// TODO: Auto-generated Javadoc
/**
 * Servlet Filter implementation class LocaleFilter.
 */
@WebFilter(filterName = "locale")
public class LocaleFilter implements Filter {
	
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
	public void doFilter(ServletRequest request, ServletResponse response, 
					FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String lang = req.getParameter(RequestConsts.LANG);
		String cntr = req.getParameter(RequestConsts.COUNTRY);
		
		Locale locale = (lang != null && cntr != null) 
					  ? new Locale(lang, cntr) 
					  : Locale.getDefault(); 
		res.setLocale(locale);
		req.getSession().setAttribute(RequestConsts.LOCALE, locale);
		res.sendRedirect(req.getHeader(RequestConsts.REFERER));
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

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}
