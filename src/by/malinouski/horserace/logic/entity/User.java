/**
 * 
 */
package by.malinouski.horserace.logic.entity;

import java.math.BigDecimal;

/**
 * @author makarymalinouski
 *
 */
public class User implements Entity {
	
	private long userId;
	private Role role;
	private String login;
	private BigDecimal balance;
	
	public User(long id, Role role, String login, BigDecimal balance) {
		userId = id;
		this.role = role;
		this.login = login;
		this.balance = balance;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public Role getRole() {
		return role;
	}
	
	public String getLogin() {
		return login;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public enum Role {
		USER, ADMIN
	}
	
	@Override
	public String toString() {
		return String.format("User ID: %s, role: %s, login: %s, balance: %s", 
								userId, role, login, balance);
	}
}
