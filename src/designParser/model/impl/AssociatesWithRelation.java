package designParser.model.impl;

import designParser.model.api.IObject;

public class AssociatesWithRelation extends AbstractObjectRelation {
    private IObject sourceModel;
    private IObject associatedModel;

    public AssociatesWithRelation(IObject src, IObject dst) {
        this.sourceModel = src;
        this.associatedModel = dst;
    }

    @Override
    public IObject getSource() {
        return sourceModel;
    }

    @Override
    public IObject getDestination() {
        return associatedModel;
    }
}
