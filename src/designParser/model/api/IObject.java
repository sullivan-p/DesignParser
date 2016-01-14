package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IObject extends IDataType {
    public void putMethodModel(String methodSig, AccessLevel accessLevel, String methodSig2);
    public void putFieldModel(String fieldName, AccessLevel accessLevel, String fieldSig);
}
