/**
 * Epam ET Final Project
 * Horseraces
 * March 2017
 * Group #10
 * Instructor Ihar Blinou
 * Student Makary Malinouski
 */
package by.malinouski.hrace.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import by.malinouski.hrace.exception.HasherException;


/**
 * @author makarymalinouski
 *
 */
public class Hasher {

	public byte[] hash(String password, byte[] salt) throws HasherException {
		try {
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 
							SecurityConsts.ITERATIONS, SecurityConsts.KEY_LENGTH);
			
		    SecretKeyFactory fact = SecretKeyFactory.getInstance(SecurityConsts.ALGORITHM);
		   
		    return fact.generateSecret(spec).getEncoded();
		    
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new HasherException("Couldn't produce hash: " + e.getMessage());
		}
	}
	
}
