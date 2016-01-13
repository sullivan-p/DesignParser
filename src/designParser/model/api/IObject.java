package designParser.model.api;

import java.util.Collection;

public interface IObject extends IDataType {
    public Collection<IField> getFields();
    public Collection<IMethod> getMethods();
    public void setFields(Collection<IField> fields);
    public void setMethods(Collection<IMethod> methods);
}
