package by.malinouski.hrace.security;

import java.security.SecureRandom;

public class SaltGenerator {

	public byte[] generate() {
		SecureRandom rand = new SecureRandom();
		byte[] salt = new byte[SecurityConsts.KEY_LENGTH/8];
		rand.nextBytes(salt);
		return salt;
	}
}
