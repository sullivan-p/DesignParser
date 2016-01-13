package designParser.model.impl;

import designParser.model.api.IObject;

public class ImplementsRelation extends AbstractObjectRelation {
    private IObject sourceModel;
    private IObject interfaceModel;

    public ImplementsRelation(IObject src, IObject dst) {
        this.sourceModel = src;
        this.interfaceModel = dst;
    }

    @Override
    public IObject getSource() {
        return sourceModel;
    }

    @Override
    public IObject getDestination() {
        return interfaceModel;
    }
}
