package designParser.model.api;

import java.util.Collection;

public interface IObject extends IDataType {
    public Collection<IField> getFields();
    public Collection<IMethod> getMethods();
}
