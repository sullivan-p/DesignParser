package designParser.asm.visitor;

import java.util.Set;

import org.objectweb.asm.MethodVisitor;

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
		
        String typeDescriptor = (signature != null) ? signature : desc;
        Set<String> typeNames = AsmProcessData.getTypeNamesFromDescriptor(typeDescriptor);
        AccessLevel accessLevel = AsmProcessData.getAccessLevel(access);
        String methodSig = getMethodSignature(name, accessLevel, typeDescriptor);
        
        // Add the method model to the design model.
        String objName = this.getCurrentObjectName();
        this.getModel().putMethodModel(objName, name, accessLevel, methodSig);
        
        // The currently visited object has a references relation with the 
        // method's parameter types and return type.
        for (String tName : typeNames) {
            this.getModel().putReferencesRelation(objName, tName);
        }
		
		return toDecorate;
	}

	@Override
	public ModelBuilderClassVisitor getDecoratedVisitor() {
		return decoratedVisitor;
	}
	
	private String getMethodSignature(String name, AccessLevel al, String descriptor) {
        int paramsEnd = descriptor.indexOf(')');
        String paramsSubStr = descriptor.substring(1, paramsEnd);
        String returnSubStr = descriptor.substring(paramsEnd+1);
        
        String prettyParamTypes = AsmProcessData.getPrettyTypeNames(paramsSubStr);
        String prettyReturnType = AsmProcessData.getPrettyTypeNames(returnSubStr);
        
        // Special case for constructor methods.
        if (name.equals("<init>")) {
            String objName = this.getCurrentObjectName();
            name = AsmProcessData.qualifiedToUnqualifiedName(objName);
            return al.toUmlString() + " " + name + "(" + prettyParamTypes + ")";
        }
        
        return al.toUmlString() + " " + prettyReturnType + " " + name + "(" + prettyParamTypes + ")";
    }
}
