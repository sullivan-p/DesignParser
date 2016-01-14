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
		MethodVisitor codeVisitor = new MethodCodeVisitor(this.getModel(), objName, name, Opcodes.ASM5, toDecorate);
		
        String typeDescriptor = (signature != null) ? signature : desc;
        Set<String> typeNames = AsmProcessData.getTypeNamesFromDescriptor(typeDescriptor);
        AccessLevel accessLevel = AsmProcessData.getAccessLevel(access);
        String methodSig = AsmProcessData.getMethodSignature(objName, name, typeDescriptor, accessLevel);
        
        // Add the method model to the design model.
        this.getModel().putMethodModel(objName, name, accessLevel, methodSig);
        
        // The currently visited object has a references relation with the 
        // method's parameter types and return type.
        for (String tName : typeNames) {
            this.getModel().putReferencesRelation(objName, tName);
        }
		
		return codeVisitor;
	}

	@Override
	public ModelBuilderClassVisitor getDecoratedVisitor() {
		return decoratedVisitor;
	}
}
