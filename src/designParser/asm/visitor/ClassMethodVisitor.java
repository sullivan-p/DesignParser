package designParser.asm.visitor;

import java.util.Set;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import designParser.asm.util.AsmProcessData;
import designParser.model.impl.AccessLevel;

public class ClassMethodVisitor extends ClassVisitorDecorator {
	ModelBuilderClassVisitor decoratedVisitor;

	public ClassMethodVisitor(int api, ModelBuilderClassVisitor decorated) {
		super(api, decorated);
		decoratedVisitor = decorated;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		
        String objName = this.getCurrentObjectName();
     
		String typeDescriptor = (signature != null) ? signature : desc;
        String retTypeName = AsmProcessData.prettyRetTypeFromMthdDesc(typeDescriptor);
        String[] paramTypeNames = AsmProcessData.prettyParamTypesFromMthdDesc(typeDescriptor);
        AccessLevel accessLevel = AsmProcessData.getAccessLevel(access);        
 		if (name.equals(AsmProcessData.CONSTRUCTOR_NAME)) {
		    name = objName;
		    retTypeName = objName;
		}          
 		boolean isStatic = AsmProcessData.isStatic(access);
 		
        // Add a model for this method to the design model.
        this.getModel().putMethodModel(objName, name, accessLevel, retTypeName, paramTypeNames, isStatic);
        
        // The currently visited object has a references relation with the 
        // method's parameter types and return type.
        Set<String> typeNames = AsmProcessData.getTypeNamesFromDescriptor(typeDescriptor);
        for (String tName : typeNames) {
            this.getModel().putReferencesRelation(objName, tName);
        }
        
		MethodVisitor codeVisitor = new MethodCodeVisitor(this.getModel(), objName, name, paramTypeNames, Opcodes.ASM5, toDecorate);
		return codeVisitor;
	}

	@Override
	public ModelBuilderClassVisitor getDecoratedVisitor() {
		return decoratedVisitor;
	}
}
