package designParser.model.api;

import java.util.Collection;

import designParser.model.impl.AccessLevel;

public interface IMethod extends IModelComponent {
    public String getObjectName();
    public String getReturnTypeName();
    public String[] getParamTypeNames();
    public AccessLevel getAccessLevel();
    public boolean isConstructor();
    public boolean isStatic();
    public String getSignature();
    public String getAbbrevSignature();
    public void setAccessLevel(AccessLevel accessLevel);
    public void setParamTypeNames(String[] paramTypeNames);
    public void setReturnTypeName(String returnTypeName);
    public void putMethodCall(IMethod methodModel);
    public Collection<IMethod> getMethodCalls();
}