package designParser.model.api;

import java.util.List;

public interface IObjOrientedEntity extends IDataType {
    public List<IField> getFields();
    public List<IMethod> getMethods();
    public void setFields(List<IField> fields);
    public void setMethods(List<IMethod> methods);
}
