package designParser.model.api;

import java.util.List;

public interface IClass extends IObjOrientedEntity {
    public IClass getExtendedClass();
    public List<IInterface> getInterfaces();
    public boolean getIsConcrete();
    public void setExtendedClass(IClass extClass);
    public void setInterfaces(List<IInterface> interfaces);
    public void setIsConcrete(boolean isConcrete);
}
