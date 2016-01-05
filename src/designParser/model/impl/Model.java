package designParser.model.impl;

import java.util.ArrayList;
import java.util.List;

import designParser.model.api.IModel;
import designParser.model.api.IObjOrientedEntity;
import designParser.model.visitor.IModelVisitor;

public class Model implements IModel {
	private List<IObjOrientedEntity> entities;

	public Model() {
		entities = new ArrayList<IObjOrientedEntity>();
	}

	@Override
	public void accept(IModelVisitor visitor) {
		// TODO Auto-generated method stub
	}
}
