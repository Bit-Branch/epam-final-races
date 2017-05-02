package test.by.malinouski.horserace;

import static org.junit.Assert.*;

import java.lang.management.ThreadInfo;

import org.junit.AfterClass;
import org.junit.Test;

import com.sun.jmx.snmp.tasks.ThreadService;

import by.malinouski.hrace.dao.UserDao;
import by.malinouski.hrace.exception.DaoException;
import by.malinouski.hrace.exception.HasherException;
import by.malinouski.hrace.logic.entity.User;
import by.malinouski.hrace.security.Hasher;
import by.malinouski.hrace.security.SaltGenerator;

public class MiscTests {

	enum E { A, B, C }
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws HasherException, DaoException {
		byte[] salt = new SaltGenerator().generate();
		byte[] hash = new Hasher().hash("watermelonP@ssw0rd", salt);
		User u = new User();
		u.setUserId(10);
		u.setHash(hash);
		u.setSalt(salt);
		new UserDao().updatePassword(u);
	}

}
