package designParser.model.impl;

import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class MethodModel implements IMethod {
    private String objName;
    private String mthdName;
    private AccessLevel accessLevel;
    private String retTypeName;
    private String[] paramTypeNames;   

    public MethodModel(String objName, String mthdName, AccessLevel accessLevel,
            String retTypeName, String[] paramTypeNames) {
        this.objName = objName;
        this.mthdName = mthdName;
        this.accessLevel = accessLevel;
        this.retTypeName = retTypeName;
        this.paramTypeNames = paramTypeNames;
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
    public void accept(IModelVisitor visitor) {
        visitor.visit(this);
    }
    
    public static String getSignature(String methodName, AccessLevel accessLevel, String retTypeName, 
            String[] paramTypeNames) {
        String sig = retTypeName + " " + methodName + " (" + String.join(", ", paramTypeNames) + ")";
        if (accessLevel != AccessLevel.Default) {
            sig = accessLevel.toString() + " " + sig;
        }
        return sig;
    }
}
