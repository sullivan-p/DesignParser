package designParser.model.api;

import java.util.List;

public interface ICustomObject extends IObject {
    public void setFields(List<IField> fields);
    public void setMethods(List<IMethod> methods);
}
