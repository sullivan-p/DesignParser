package designParser.model.api;

import java.util.Collection;

public interface ICustomObject extends IObject {
    public void setFields(Collection<IField> fields);
    public void setMethods(Collection<IMethod> methods);
}
