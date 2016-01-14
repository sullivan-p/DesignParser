package designParser.asm.util.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import designParser.asm.util.AsmProcessData;

public class AsmProcessDataTest {

	@Test
	public void testGetTypeNamesFromDescriptor() {
		Set<String> expected = new HashSet<String>();
		String testStr = "LtestClass/TestObject;";
		expected.add("TestObject");
		Set<String> actual = AsmProcessData.getTypeNamesFromDescriptor(testStr);
		assertEquals(expected, actual);
		testStr = "LtestClass-TestObject;";
		actual = AsmProcessData.getTypeNamesFromDescriptor(testStr);
		assertNotEquals(expected, actual);
	}

	@Test
	public void testGetTypeNamesFromDescriptor2() {
		Set<String> expected = new HashSet<String>();
		String testStr = "LtestClass/TestObject;";
		expected.add("TestObject");
		Set<String> actual = AsmProcessData.getTypeNamesFromDescriptor(testStr);
		
	}

	@Test
	public void testGetPrettyTypeNames() {
		String testStr = "LtestClass/TestObject;";
		String expected = "TestObject";
		String actual = AsmProcessData.getPrettyTypeNames(testStr);
		assertEquals(expected, actual);
		testStr = "LtestClass-TestObject;";
		actual = AsmProcessData.getPrettyTypeNames(testStr);
		assertNotEquals(expected, actual);
	}
}