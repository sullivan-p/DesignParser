package designParser.model.api;

import java.util.List;

public interface IEnum extends IObjOrientedEntity {
	public List<IInterface> getInterfaces();
	public List<String> getEnumElements();
}
