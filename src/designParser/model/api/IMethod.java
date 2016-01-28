package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IMethod extends IModelComponent {
    public String getObjectName();
    public String getReturnTypeName();
    public AccessLevel getAccessLevel();
    public boolean isConstructor();
    public boolean isStatic();
    public String getSignature();
    public String getAbbrevSignature();
    public void setAccessLevel(AccessLevel accessLevel);
    public void setParamTypeNames(String[] paramTypeNames);
    public void setReturnTypeName(String returnTypeName);
    public void putMethodCall(IMethod methodModel);
}