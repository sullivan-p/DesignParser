package designParser.model.api;

import java.util.List;

public interface IObjOrientedEntity extends IDataType {
    public List<IField> getFields();
    public List<IMethod> getMethods();
}
