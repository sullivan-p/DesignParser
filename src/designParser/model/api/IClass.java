package designParser.model.api;

import java.util.Collection;

public interface IClass extends ICustomObject {
    public IClass getExtendedClass();
    public Collection<IInterface> getInterfaces();
    public boolean getIsConcrete();
    public void setExtendedClass(IClass extClass);
    public void setInterfaces(Collection<IInterface> interfaces);
    public void setIsConcrete(boolean isConcrete);
}
