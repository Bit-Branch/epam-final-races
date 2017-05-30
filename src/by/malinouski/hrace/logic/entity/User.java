/**
 * 
 */
package by.malinouski.hrace.logic.entity;

import java.math.BigDecimal;
import java.time.YearMonth;

// TODO: Auto-generated Javadoc
/**
 * @author makarymalinouski
 *
 */
public class User implements Entity {
	/**
	 * May 27, 2017 4:00 PM UTC
	 */
	private static final long serialVersionUID = 9025396325963843026L;
	private long userId;
	private Role role;
	private String login;
	private BigDecimal balance;
	private transient String password;
	private transient byte[] salt;
	private transient byte[] hash;
	private transient CreditCard card;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param id the id
	 * @param role the role
	 * @param login the login
	 * @param password the password
	 * @param salt the salt
	 * @param balance the balance
	 */
	public User(long id, 
				Role role, 
				String login, 
				String password, 
				byte[] salt, 
				BigDecimal balance) {
		
		userId = id;
		this.role = role;
		this.login = login;
		this.password = password;
		this.salt = salt;
		this.balance = balance;
	}
	
	/**
	 * Instantiates a new user.
	 */
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
	
	public byte[] getSalt() {
		return salt;
	}
	
	public byte[] getHash() {
		return hash;
	}
	
	public CreditCard getCard() {
		return card;
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
	
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	
	public void setCard(CreditCard card) {
		this.card = card;
	}
	
	/**
	 * The Enum Role.
	 */
	public enum Role {
		USER, ADMIN
	}
	
	@Override
	public String toString() {
		return String.format("User ID: %s, role: %s, login: %s, balance: %s", 
								userId, role, login, balance);
	}

	@Override
	public EntityType ofType() {
		return EntityType.USER;
	}
	
	/**
	 * The Class CreditCard.
	 */
	public class CreditCard {
		private long number;
		private String name;
		private YearMonth valid;
		private int cvv;
		
		/**
		 * Instantiates a new credit card.
		 *
		 * @param number the number
		 * @param name the name
		 * @param valid2 the valid 2
		 * @param cvv the cvv
		 */
		public CreditCard(long number, String name, 
							YearMonth valid2, int cvv) {
			this.number = number;
			this.name = name;
			this.valid = valid2;
			this.cvv = cvv;
		}
		
		public long getNumber() {
			return number;
		}
		
		public String getName() {
			return name;
		}
		
		public YearMonth getValid() {
			return valid;
		}
		
		public int getCvv() {
			return cvv;
		}
	}
}
