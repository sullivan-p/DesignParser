package designParser.model.api;

import java.util.Collection;

import designParser.model.impl.AccessLevel;

public interface IObject extends IDataType {
    public void putMethodModel(String methodName, AccessLevel accessLevel, 
            String retTypeName, String[] paramTypeNames);    
    public void putMethodModel(String methodName, String retTypeName, 
            String[] paramTypeNames);
    public void putFieldModel(String fieldName, AccessLevel accessLevel, String fieldSig);
    public void putMethodCall(String callerMethodName, String[] callerParamTypeNames, IMethod calleeMethodModel);
    public IMethod getMethodModel(String methodName, String[] paramTypeNames);
    public Collection<IField> getAllFieldModels();
    public Collection<IMethod> getAllMethodModels();
    public void setFieldModels(Collection<IField> fields);
    public void setMethodModels(Collection<IMethod> methods);
}
