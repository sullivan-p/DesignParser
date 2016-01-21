package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IObject extends IDataType {
//    public void putMethodModel(String methodName, AccessLevel accessLevel, String methodSig);
    public void putMethodModel(String objName, String methodName, AccessLevel accessLevel, 
            String retTypeName, String[] paramTypeNames);    
    public void putFieldModel(String fieldName, AccessLevel accessLevel, String fieldSig);
}
