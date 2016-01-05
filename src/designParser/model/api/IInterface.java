package designParser.model.api;

import java.util.List;

public interface IInterface extends IObjOrientedEntity {
    public List<IInterface> getExtendedInterfaces();
    public void setExtendedInterfaces(List<IInterface> extInterfaces);
}
