package designParser.umlGen.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import designParser.umlGen.util.UmlArrowMarkup;

public class UmlArrowMarkupTest {

	@Test
	public void testExtends() {
		String expected = "ExTestFactory -> TestFactory [arrowhead=\"onormal\", style=\"solid\"];\n";
		String actual = UmlArrowMarkup.getExtendsArrow("ExTestFactory", "TestFactory");
		assertEquals(expected, actual);
	}

	@Test
	public void testImplements() {
		String expected = "ImTestFactory -> TestFactory [arrowhead=\"onormal\", style=\"dashed\"];\n";
		String actual = UmlArrowMarkup.getImplementsArrow("ImTestFactory", "TestFactory");
		assertEquals(expected, actual);
	}

	@Test
	public void testReferences() {
		String expected = "RefTestFactory -> TestFactory [arrowhead=\"vee\", style=\"dashed\"];\n";
		String actual = UmlArrowMarkup.getReferencesArrow("RefTestFactory", "TestFactory");
		assertEquals(expected, actual);
	}

	@Test
	public void testAssociates() {
		String expected = "AscTestFactory -> TestFactory [arrowhead=\"vee\", style=\"solid\"];\n";
		String actual = UmlArrowMarkup.getAssociatesArrow("AscTestFactory", "TestFactory");
		assertEquals(expected, actual);
	}
}