package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IMethod extends IModelComponent {
    public String getSignature();
    public AccessLevel getAccessLevel();
    public void putMethodCall(String callerClassName, String callerMethodName,
            String calleeClassName, String calleeMethodName, String[] calleeParamTypeNames,
            String calleeReturnTypeName, boolean isConstructor);
}