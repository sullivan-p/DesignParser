package designParser.model.api;

import designParser.model.impl.DesignModel;

public interface IUmlGenerator {
	public String getUmlMarkup(DesignModel model);

}
