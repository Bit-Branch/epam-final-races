/**
 * 
 */
package by.malinouski.horserace.command.receiver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.malinouski.horserace.constant.RequestMapKeys;

/**
 * @author makarymalinouski
 *
 */
public class ParseRequestReceiver implements CommandReceiver {
	private static final Logger logger = LogManager.getLogger(CommandReceiver.class);
	private static final Object PARSE_REDIRECT_PATH = "/jsp/parseResult.jsp";
	private static final String TEMP_SUFFIX = ".xml";
	private static final String TEMP_PREFIX = "part";
	private Map<String, Object> requestMap;
	/**
	 * @param requestMap
	 */
	public ParseRequestReceiver(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	
	@Override
	public void act() {
		String locale = ((String[]) requestMap.get(RequestMapKeys.LOCALE))[0];
		requestMap.put(RequestMapKeys.LOCALE, locale);
		
//		PeriodicalsBuilderFactory fact = new PeriodicalsBuilderFactory();
		String parserType = ((String[]) requestMap.get(RequestMapKeys.PARSER_TYPE))[0];
//		AbstractPeriodicalsBuilder builder = fact.createPeriodicalBuilder(parserType);
		
		Part part = (Part) requestMap.get(RequestMapKeys.PART);
		Path path = writePartToFile(part);
//		builder.buildSetPeriodicals(path.toString());
		try {
			Files.delete(path);
		} catch (IOException e) {
			logger.error("Exception while deleting temp file " + path);
		}
		requestMap.put(RequestMapKeys.PARSER_TYPE, parserType);
//		requestMap.put(RequestMapKeys.RESULT_KEY, builder.getPeriodicals());
		requestMap.put(RequestMapKeys.REDIRECT_PATH, PARSE_REDIRECT_PATH);
	}

	private Path writePartToFile(Part part) {
		Path path = null;
		try {
			path = Files.createTempFile(TEMP_PREFIX, TEMP_SUFFIX);
			part.write(path.toString());
			return path;
		} catch (IOException e) {
			logger.error("Exception while writing parts to file");
		}
		return null;
	}
}
