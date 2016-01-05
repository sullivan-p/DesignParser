package designParser.asm.visitor;

import java.util.Arrays;
import org.objectweb.asm.Opcodes;
import designParser.model.api.IModel;
import designParser.model.impl.Model;

public class ClassDeclarationVisitor extends ModelBuilderClassVisitor {
	private IModel model;

	public ClassDeclarationVisitor(int api) {
		super(api);
		model = new Model();
	}

	public ClassDeclarationVisitor(int api, IModel model) {
		super(api);
		this.model = model;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		
		String javaName = convertAsmToJavaName(name);
		
		// Determine whether a class, interface, or enum is being visited.
		if (isInterface(access)) {
		    System.out.println("Interface");
		} else if (isEnum(access)) {
		    System.out.println("Enum");
		} else {
		    System.out.println("Class");
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public IModel getModel() {
		return model;
	}
	
	/**
	 * Examine the opcode and return true if the ClassVisitor is visiting an 
	 * interface, false otherwise.
	 */
	private boolean isInterface(int opcode) {
	    int bitmask = Opcodes.ACC_INTERFACE;
	    return (opcode & bitmask) != 0;
	}

    /**
     * Examine the opcode and return true if the ClassVisitor is visiting an 
     * enum, false otherwise.
     */
	private boolean isEnum(int opcode) {
        int bitmask = Opcodes.ACC_ENUM;
        return (opcode & bitmask) != 0;	
    }
	
	/**
	 * Given an ASM name, generate and return a Java qualified name.
	 */
	private String convertAsmToJavaName(String asmName) {
	    return asmName.replace("/", ".");
	}
}