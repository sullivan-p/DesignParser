package designParser.model.impl;

import java.util.ArrayList;
import java.util.List;

import designParser.model.api.IMethod;
import designParser.model.api.IMethodCall;
import designParser.model.api.IModelVisitor;

public class MethodModel implements IMethod {
    private String objName;
    private String mthdName;
    private AccessLevel accessLevel;
    private String retTypeName;
    private String[] paramTypeNames;   
    private List<IMethodCall> methodCalls;

    public MethodModel(String objName, String mthdName, AccessLevel accessLevel,
            String retTypeName, String[] paramTypeNames) {
        this.objName = objName;
        this.mthdName = mthdName;
        this.accessLevel = accessLevel;
        this.retTypeName = retTypeName;
        this.paramTypeNames = paramTypeNames;
        this.methodCalls = new ArrayList<IMethodCall>();
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String getSignature() {
        return getSignature(mthdName, accessLevel, retTypeName, paramTypeNames);
    }
    
    @Override
    public String getName() {
        return mthdName;
    }
    
    @Override
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    @Override
    public void putMethodCall(String calleeClassName, String calleeMethodName, 
            String[] calleeParamTypeNames, String calleeReturnTypeName, boolean isConstructor) {
        methodCalls.add(new MethodCall(calleeClassName, calleeMethodName, 
                calleeParamTypeNames, calleeReturnTypeName, isConstructor));
    }
    
    public static String getSignature(String methodName, AccessLevel accessLevel, String retTypeName, 
            String[] paramTypeNames) {
        String sig = retTypeName + " " + getAbbrevSignature(methodName, paramTypeNames);
        if (accessLevel != AccessLevel.Default) {
            sig = accessLevel.toUmlString() + " " + sig;
        }
        return sig;
    }
    
    public static String getAbbrevSignature(String methodName, String[] paramTypeNames) {
        return methodName + " (" + String.join(", ", paramTypeNames) + ")";
    }
}
