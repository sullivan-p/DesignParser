//package designParser.asm.visitor.test;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//import org.objectweb.asm.Opcodes;
//
//import designParser.asm.util.AsmProcessData;
//import designParser.asm.visitor.ClassDeclarationVisitor;
//import designParser.model.api.IDesignModel;
//import designParser.model.impl.DesignModel;
//
//public class ClassDeclarationVisitorTest {
//
//	private final static String[] NAMES = { "appLauncherSltn.AppLauncherApplication" };
//	// @Test
//	// public void test() {
//	// String str = "TestPackage/TestClass";
//	// String expectedJavaName = "TestPackage.TestClass";
//	// String expected = "TestClass";
//	// String actualUnqualified =
//	// AsmProcessData.qualifiedToUnqualifiedName(AsmProcessData.convertAsmToJavaName(str));
//	// assertEquals(expected, actualUnqualified);
//	// }
//
//	@Test
//	public void testVisit() {
//		IDesignModel designModel = new DesignModel(NAMES);
//		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, designModel);
//		String[] interfaces = new String[0];
//		String name = AsmProcessData.qualifiedToUnqualifiedName(AsmProcessData.convertAsmToJavaName(NAMES[0]));
//		cdv.visit(0, 0, name, null, "java/lang/Object", interfaces);
//		String realInterfaces = "[" + name + "]";
//		assertEquals(designModel.getObjNamesToModel().toString(), realInterfaces);
//
//	}
//}