package by.malinouski.hrace.security;

import java.security.SecureRandom;

// TODO: Auto-generated Javadoc
/**
 * The Class SaltGenerator.
 */
public class SaltGenerator {

	/**
	 * Generate.
	 *
	 * @return the byte[]
	 */
	public byte[] generate() {
		SecureRandom rand = new SecureRandom();
		byte[] salt = new byte[SecurityConsts.KEY_LENGTH/8];
		rand.nextBytes(salt);
		return salt;
	}
}
