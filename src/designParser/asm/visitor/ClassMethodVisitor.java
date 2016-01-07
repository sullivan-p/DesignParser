package designParser.asm.visitor;

import java.util.HashSet;

import org.objectweb.asm.MethodVisitor;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IMethod;
import designParser.model.impl.AccessLevel;
import designParser.model.impl.MethodModel;

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
        HashSet<String> typeNames = AsmProcessData.getTypeNamesFromDescriptor(typeDescriptor);
        AccessLevel accessLevel = AsmProcessData.getAccessLevel(access);
        String methodSig = getMethodSignature(name, accessLevel, typeDescriptor);
        IMethod methodModel = new MethodModel(name, typeNames, accessLevel, methodSig);
        this.getCurrentEntity().getMethods().add(methodModel);
		
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
            String objName = this.getCurrentEntity().getName();
            name = AsmProcessData.qualifiedToUnqualifiedName(objName);
            return al.toUmlString() + " " + name + "(" + prettyParamTypes + ")";
        }
        
        return al.toUmlString() + " " + prettyReturnType + " " + name + "(" + prettyParamTypes + ")";
    }
}
