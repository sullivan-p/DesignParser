package designParser.model.impl;

import designParser.model.api.IModelVisitor;
import designParser.model.api.IObject;
import designParser.model.api.IObjectRelation;

public abstract class AbstractObjectRelation implements IObjectRelation {
    private IObject srcModel;
    private IObject dstModel;

    public AbstractObjectRelation(IObject srcModel, IObject dstModel) {
        this.srcModel = srcModel;
        this.dstModel = dstModel;
    }

    @Override
    public IObject getSource() {
        return srcModel;
    }

    @Override
    public IObject getDestination() {
        return dstModel;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {}
}
