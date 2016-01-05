package designParser.model.api;

import java.util.List;

public interface IClass extends IObjOrientedEntity {
    public IClass getExtendedClass();
    public List<IInterface> getInterfaces();
    public boolean getIsConcrete();
}
