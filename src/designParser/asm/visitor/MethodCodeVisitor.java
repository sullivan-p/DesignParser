package designParser.asm.visitor;

import org.objectweb.asm.MethodVisitor;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IDesignModel;

public class MethodCodeVisitor extends MethodVisitor {
    private IDesignModel model;
    private String callerClassName;
    private String callerMethodName;
    private String[] callerParamTypeNames;

    public MethodCodeVisitor(IDesignModel m, String callerClassName, String callerMethodName, 
            String[] callerParamTypeNames, int api, MethodVisitor mv) {
        super(api, mv);
        this.model = m;
        this.callerClassName = callerClassName;
        this.callerMethodName = callerMethodName;
        this.callerParamTypeNames = callerParamTypeNames;
    }
    
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        String calleeClassName = AsmProcessData.qualifiedToUnqualifiedName(
                AsmProcessData.convertAsmToJavaName(owner));
        String calleeMethodName = name;
        String[] calleeParamTypesNames = AsmProcessData.prettyParamTypesFromMthdDesc(desc);
        String calleeReturnTypeName = AsmProcessData.prettyRetTypeFromMthdDesc(desc);
        boolean isConstructor = calleeMethodName.equals("<init>");
        model.putMethodCall(callerClassName, callerMethodName, callerParamTypeNames, 
                calleeClassName, calleeMethodName, calleeParamTypesNames, calleeReturnTypeName, isConstructor);
    }    
    
    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {}
    
    @Override
    public void visitTypeInsn(int opcode, String type) {}    
    
    @Override
    public void visitVarInsn(int opcode, int var) {}    
}
