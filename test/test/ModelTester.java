package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.objectweb.asm.Opcodes;

import designParser.asm.util.AsmProcessData;
import designParser.asm.visitor.ClassDeclarationVisitor;
import designParser.model.api.IDesignModel;
import designParser.model.impl.ClassModel;
import designParser.model.impl.DesignModel;
import designParser.model.impl.EnumModel;
import designParser.model.impl.ImplementsRelation;
import designParser.model.impl.InterfaceModel;
import designParser.model.impl.MethodCall;
import designParser.model.impl.MethodModel;
import designParser.model.impl.TypesVisitor;

public class ModelTester {
	private String[] NAMES = { "appLauncherSltn.ApplicationLauncher", "appLauncherSltn.IApplicationLauncher" };

	@Test
	public void testInterface() {
		String realInterfaces = "";
		IDesignModel designModel = new DesignModel(NAMES);
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, designModel);
		System.out.println(cdv.getModel().getObjNamesToModel());
		String[] interfaces = new String[0];
		String name = AsmProcessData.qualifiedToUnqualifiedName(AsmProcessData.convertAsmToJavaName(NAMES[1]));
		cdv.visit(52, 33, name, null, "java/lang/Object", interfaces);
		realInterfaces += "[" + name;
		name = AsmProcessData.qualifiedToUnqualifiedName(AsmProcessData.convertAsmToJavaName(NAMES[0]));
		cdv.visit(52, 33, name, null, "java/lang/Object", interfaces);
		realInterfaces += ", " + name + "]";
		System.out.println(name);
		// if(designModel.)
		assertEquals(designModel.getObjNamesToModel().toString(), realInterfaces);
		// assertEquals(DesignVisitorTester.interfNames, "");

	}
}

class DesignVisitorTester extends TypesVisitor {
	static List<String> classNames = new ArrayList<String>();
	static List<String> methdNames = new ArrayList<String>();
	static List<String> interfNames = new ArrayList<String>();
	static List<String> enumNames = new ArrayList<String>();
	static List<String> reltnNames = new ArrayList<String>();
	static List<String> methdCalls = new ArrayList<String>();

	public DesignVisitorTester() {
		addVisitMethod(ClassModel.class, (c) -> {
			addClass((ClassModel) c);
		});

		addVisitMethod(EnumModel.class, (c) -> {
			addEnum((EnumModel) c);
		});

		addVisitMethod(InterfaceModel.class, (c) -> {
			addInterface((InterfaceModel) c);
		});

		addVisitMethod(ImplementsRelation.class, (c) -> {
			addReltn((ImplementsRelation) c);
		});

		addVisitMethod(MethodModel.class, (c) -> {
			addMethod((MethodModel) c);
		});

		addVisitMethod(MethodModel.class, (c) -> {
			addMethodCalls((MethodCall) c);
		});
	}

	@Test
	public void testing() {
		
	}

	void addClass(ClassModel c) {
		classNames.add(c.getName());
	}

	void addEnum(EnumModel e) {
		enumNames.add(e.getName());
	}

	void addInterface(InterfaceModel i) {
		interfNames.add(i.getName());
	}

	void addReltn(ImplementsRelation i) {
		reltnNames.add(i.getSourceName());
	}

	void addMethod(MethodModel m) {
		methdNames.add(m.getName());
	}

	void addMethodCalls(MethodCall c) {
		methdCalls.add(c.getCallerClassName() + "." + c.getCallerMethodName() + " " + c.getCalleeClassName() + "."
				+ c.getCalleeMethodName());
	}
}