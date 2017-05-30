/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.customtag;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.malinouski.hrace.constant.RequestConsts;

/**
 * @author makarymalinouski
 *
 */
public class MessageTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		HttpSession session = pageContext.getSession();
		Object msg = session.getAttribute(RequestConsts.MESSAGE);
		
		if (msg != null) {
			Object loc = session.getAttribute(RequestConsts.LOCALE);
			Locale locale = loc != null ? (Locale) loc
						  : pageContext.getRequest().getLocale();
			
			ResourceBundle bundle = ResourceBundle.getBundle(
						"resources.bundle.pagecontent", locale);
			try {
				out.print(bundle.getString(msg.toString()));
			} catch (IOException e) {
				throw new JspException();
			}
			session.removeAttribute(RequestConsts.MESSAGE);
		}
		
		return SKIP_BODY;
	}

}
