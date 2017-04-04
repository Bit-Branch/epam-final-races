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
	private transient String password;
	
	public User(long id, Role role, String login, BigDecimal balance) {
		userId = id;
		this.role = role;
		this.login = login;
		this.balance = balance;
	}
	
	public User() {
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
	
	public String getPassword() {
		return password;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
