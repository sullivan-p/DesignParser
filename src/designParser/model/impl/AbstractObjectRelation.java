package designParser.model.impl;

import designParser.model.api.IModelVisitor;
import designParser.model.api.IObjectRelation;

public abstract class AbstractObjectRelation implements IObjectRelation {

    @Override
    public void accept(IModelVisitor visitor) {}
}
