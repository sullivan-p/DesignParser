package designParser.asm.visitor;

import org.objectweb.asm.MethodVisitor;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IDesignModel;

public class MethodCodeVisitor extends MethodVisitor {
    private IDesignModel model;
    private String objectName;
    private String methodName;

    public MethodCodeVisitor(IDesignModel m, String objectName, String methodName, int api, MethodVisitor mv) {
        super(api, mv);
        this.model = m;
        this.objectName = objectName;
        this.methodName = methodName;
    }
    
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        String calleeClassName = AsmProcessData.qualifiedToUnqualifiedName(
                AsmProcessData.convertAsmToJavaName(owner));
        String calleeMethodName = name;
        String callerClassName = objectName;
        String callerMethodName = methodName;
        String[] paramTypesNames = AsmProcessData.prettyParamTypesFromMthdDesc(desc);
        String returnTypeName = AsmProcessData.prettyRetTypeFromMthdDesc(desc);
        model.putMethodCall(callerClassName, callerMethodName, calleeClassName, calleeMethodName, 
                paramTypesNames, returnTypeName, (calleeMethodName.equals("<init>")));
    }    
    
    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {}
    
    @Override
    public void visitTypeInsn(int opcode, String type) {}    
    
    @Override
    public void visitVarInsn(int opcode, int var) {}    
}
