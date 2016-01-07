package designParser.model.api;

import java.util.Collection;

public interface IEnum extends ICustomObject {
	public Collection<IInterface> getInterfaces();
	public Collection<String> getEnumElements();
    public void setInterfaces(Collection<IInterface> interfaces);
    public void setEnumElements(Collection<String> enumElements);
}
