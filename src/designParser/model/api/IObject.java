package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IObject extends IDataType {
//    public Collection<IField> getFields();
//    public Collection<IMethod> getMethods();
//    public void setFields(Collection<IField> fields);
//    public void setMethods(Collection<IMethod> methods);
    public void putMethodModel(String methodSig, AccessLevel accessLevel, String methodSig2);
    public void putFieldModel(String fieldName, AccessLevel accessLevel, String fieldSig);
}
