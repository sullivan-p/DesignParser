package designParser.model.api;

import java.util.Collection;

public interface IInterface extends ICustomObject {
    public Collection<IInterface> getExtendedInterfaces();
    public void setExtendedInterfaces(Collection<IInterface> extInterfaces);
}
