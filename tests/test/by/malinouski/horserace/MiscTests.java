package test.by.malinouski.horserace;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class MiscTests {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		int i = 1;
		double d = 0.5;
		System.out.println(d%0.4);
	}

}
