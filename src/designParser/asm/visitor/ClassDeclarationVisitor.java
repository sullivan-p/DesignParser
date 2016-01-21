package designParser.asm.visitor;

import java.util.ArrayList;
import java.util.List;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IDesignModel;

public class ClassDeclarationVisitor extends ModelBuilderClassVisitor {
	private IDesignModel model;

	// Name of the class, interface, or enum that the visitor is visiting.
	private String currentObjectName;

	public ClassDeclarationVisitor(int api, IDesignModel model) {
		super(api);
		this.model = model;
	}

	@Override
	public IDesignModel getModel() {
		return model;
	}

	@Override
	public String getCurrentObjectName() {
		return currentObjectName;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

		// Convert object name from ASM format to unqualified Java name, and set
	    // the currently visited object's name.
		name = AsmProcessData.qualifiedToUnqualifiedName(
		        AsmProcessData.convertAsmToJavaName(name));
		this.currentObjectName = name;

		// Convert interface names from ASM format to unqualified Java names.
		List<String> interfaceNames = new ArrayList<String>();
		for (String i : interfaces) {
		    String javaName = AsmProcessData.convertAsmToJavaName(i);
		    String unqualifiedName = AsmProcessData.qualifiedToUnqualifiedName(javaName);
		    interfaceNames.add(unqualifiedName);
		}

		// Convert superclass name from ASM format to unqualified Java name.
		if (superName != null) {
            String javaName = AsmProcessData.convertAsmToJavaName(superName);
            String unqualifiedName = AsmProcessData.qualifiedToUnqualifiedName(javaName);
            superName = unqualifiedName;
		}

		// Model the object that is being visited.
        if (AsmProcessData.isInterface(access)) {
            model.putInterfaceModel(name);
        } else if (AsmProcessData.isEnum(access)) {
            model.putEnumModel(name);
        } else {
            boolean isConcrete = !AsmProcessData.isAbstract(access);
            model.putClassModel(name, isConcrete);
        }
        
        // Add a relation for each of the model's interfaces.
        for (String iName : interfaceNames) {           
            
            // Interfaces extend other interfaces, whereas classes and enums
            // implement interfaces.
            if (AsmProcessData.isInterface(access)) {
                model.putExtendsRelation(name, iName);
            } else {
                model.putImplementsRelation(name, iName);
            }
        }
        
        // Classes may have a superclass.
        if (!AsmProcessData.isInterface(access) && !AsmProcessData.isEnum(access) && superName != null) {
            model.putExtendsRelation(name, superName);
        }
	}
}