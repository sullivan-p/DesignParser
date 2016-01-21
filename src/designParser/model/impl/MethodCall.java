package designParser.model.impl;

import designParser.model.api.IMethodCall;
import designParser.model.api.IModelVisitor;

public class MethodCall implements IMethodCall {
    private String callerClassName;
    private String callerMethodName;
    private String calleeClassName;
    private String calleeMethodName;
    private String[] paramTypeNames;
    private String returnTypeName;
    private boolean isConstructor;
    
    public MethodCall(String callerClassName, String callerMethodName,
            String calleeClassName, String calleeMethodName,
            String[] paramTypeNames, String returnTypeName, 
            boolean isConstructor) {
        this.callerClassName = callerClassName;
        this.callerMethodName = callerMethodName;
        this.calleeClassName = calleeClassName;
        this.calleeMethodName = calleeMethodName;
        this.paramTypeNames = paramTypeNames;
        this.returnTypeName = returnTypeName;
        this.isConstructor = isConstructor;
    }

    @Override
    public void accept(IModelVisitor visitor) {
        // Check depth of visitor and if so visit...
    }

    @Override
    public String getCallerClassName() {
        return this.callerClassName;
    }

    @Override
    public String getCallerMethodName() {
        return this.callerMethodName;
    }

    @Override
    public String getCalleeClassName() {
        return this.calleeClassName;
    }

    @Override
    public String getCalleeMethodName() {
        return this.calleeMethodName;
    }

    @Override
    public boolean getIsConstructor() {
        return this.isConstructor;
    }

    @Override
    public String getReturnTypeName() {
        return this.returnTypeName;
    }

    @Override
    public String[] getParamTypeNames() {
        return this.paramTypeNames;
    }
}
