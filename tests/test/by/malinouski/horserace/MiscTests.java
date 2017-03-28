package test.by.malinouski.horserace;

import static org.junit.Assert.*;

import java.lang.management.ThreadInfo;

import org.junit.AfterClass;
import org.junit.Test;

import com.sun.jmx.snmp.tasks.ThreadService;

public class MiscTests {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		new Thread(() -> Thread.getAllStackTraces().keySet().forEach(thread -> thread.interrupt()));
		System.out.println(Thread.getAllStackTraces());
	}

}
