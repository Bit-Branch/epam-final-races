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
	private UserAccount account;
	
	public User(long id, Role role, String login) {
		userId = id;
		this.role = role;
		this.login = login;
		account = new UserAccount();
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
	
	public double getAccoutBalance() {
		return account.balance.doubleValue();
	}
	
	public void addMoney(double howMuch) {
		if (howMuch > 0) {
			account.balance.add(BigDecimal.valueOf(howMuch));
		}
	}
	
	public enum Role {
		USER, ADMIN
	}
	
	private class UserAccount {
		public BigDecimal balance;
	}
	
	@Override
	public String toString() {
		return String.format("User ID: %s, role: %s, login: %s, balance: %s", 
								userId, role, login, account.balance);
	}
}
