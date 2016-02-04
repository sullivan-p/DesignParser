package designParser.model.impl;

import java.util.ArrayList;
import java.util.List;

import designParser.model.api.IMethod;
import designParser.visitor.api.IModelVisitor;

public class MethodModel implements IMethod {
    private String objName;
    private String mthdName;
    private AccessLevel accessLevel;
    private String retTypeName;
    private String[] paramTypeNames;   
    private List<IMethod> methodCalls;
    private boolean isStatic;

    public MethodModel(String objName, String mthdName, AccessLevel accessLevel,
            String retTypeName, String[] paramTypeNames, boolean isStatic) {
        this.objName = objName;
        this.mthdName = mthdName;
        this.accessLevel = accessLevel;
        this.retTypeName = retTypeName;
        this.paramTypeNames = paramTypeNames;
        this.isStatic = isStatic;
        this.methodCalls = new ArrayList<IMethod>();
    }
    
    @Override
    public void accept(IModelVisitor visitor) {       
        visitor.incrementCallDepth();
        visitor.previsit(this);
        visitor.visit(this);
        if (visitor.getCallDepth() <= visitor.getMaxCallDepth()) {
            for (IMethod m : methodCalls) {
                m.accept(visitor);
            }
        }
        visitor.postvisit(this);
        visitor.decrementCallDepth();
    }
    
    @Override
    public String getSignature() {
        return getSignature(mthdName, accessLevel, retTypeName, paramTypeNames, isConstructor(), isStatic);
    }
    
    @Override
    public String getAbbrevSignature() {
        return getAbbrevSignature(mthdName, paramTypeNames);
    }
    
    @Override
    public String getName() {
        return mthdName;
    }
    
    @Override
    public String getObjectName() {
        return objName;
    }
    
    @Override
    public String getReturnTypeName() {
        return retTypeName;
    }
    
    @Override
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
    
    @Override
    public boolean isConstructor() {
        return objName.equals(mthdName);
    }
    
    @Override
    public boolean isStatic() {
        return isStatic;
    }
    
    @Override
    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public void setParamTypeNames(String[] paramTypeNames) {
        this.paramTypeNames = paramTypeNames;
    }

    @Override
    public void setReturnTypeName(String returnTypeName) {
        this.retTypeName = returnTypeName;
    }
    
    @Override
    public void putMethodCall(IMethod methodModel) {
        methodCalls.add(methodModel);
    }

    public static String getSignature(String methodName, AccessLevel accessLevel, String retTypeName, 
            String[] paramTypeNames, boolean isConstr, boolean isStat) {
        String sig = "";
        if (accessLevel != null && accessLevel != AccessLevel.Default) {
            sig = accessLevel.toUmlString() + " ";
        }        
        if (isStat) {
            sig += "static ";
        } 
        if (!isConstr) {
            sig += retTypeName + " ";
        }
        sig += getAbbrevSignature(methodName, paramTypeNames);
        return sig;
    }
    
    public static String getAbbrevSignature(String methodName, String[] paramTypeNames) {
        return methodName + "(" + String.join(", ", paramTypeNames) + ")";
    }
}
