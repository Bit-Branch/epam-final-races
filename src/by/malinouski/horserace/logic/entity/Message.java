/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.horserace.logic.entity;


/**
 * @author makarymalinouski
 *
 */
public class Message implements Entity {
	private String text;
	
	public Message(String text) {
		this.text = text;
	}

	public Message() {
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
