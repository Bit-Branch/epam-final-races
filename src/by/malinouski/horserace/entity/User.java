/**
 * 
 */
package by.malinouski.horserace.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import by.malinouski.horserace.exception.AddingNegativeAmountException;

/**
 * @author makarymalinouski
 *
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long userId;
	private Role role;
	private String login;
	private transient String password;
	private UserAccount account;
	
	public User(long id, Role role, String login, String password) {
		userId = id;
		this.role = role;
		this.login = login;
		this.password = password;
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
	
	public String getPassword() {
		return password;
	}
	
	public double getAccoutBalance() {
		return account.balance.doubleValue();
	}
	
	public void addMoney(double howMuch) throws AddingNegativeAmountException {
		if (howMuch < 0) {
			throw new AddingNegativeAmountException();
		}
		account.balance.add(BigDecimal.valueOf(howMuch));
	}
	
	public enum Role {
		USER, ADMIN
	}
	
	private class UserAccount {

		public BigDecimal balance;
	}
}
