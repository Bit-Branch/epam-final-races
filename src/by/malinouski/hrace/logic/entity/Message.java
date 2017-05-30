/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.logic.entity;


// TODO: Auto-generated Javadoc
/**
 * @author makarymalinouski
 * Class representing a string of text
 */
public class Message implements Entity {
	/**
	 * May 27, 2017 3:48 PM UTC
	 */
	private static final long serialVersionUID = 7008245962529927114L;
	private String text;
	
	/**
	 * Instantiates a new message.
	 *
	 * @param text the text
	 */
	public Message(String text) {
		this.text = text;
	}

	/**
	 * Instantiates a new message.
	 */
	public Message() {
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public EntityType ofType() {
		return EntityType.MESSAGE;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
