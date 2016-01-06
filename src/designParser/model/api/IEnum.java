package designParser.model.api;

import java.util.List;

public interface IEnum extends ICustomObject {
	public List<IInterface> getInterfaces();
	public List<String> getEnumElements();
    public void setInterfaces(List<IInterface> interfaces);
    public void setEnumElements(List<String> enumElements);
}
