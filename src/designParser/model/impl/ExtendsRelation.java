package designParser.model.impl;

import designParser.model.api.IObject;

public class ExtendsRelation extends AbstractObjectRelation {
    private IObject sourceModel;
    private IObject extendedModel;

    public ExtendsRelation(IObject sourceModel, IObject extendedModel) {
        this.sourceModel = sourceModel;
        this.extendedModel = extendedModel;
    }

    @Override
    public IObject getSource() {
        return sourceModel;
    }

    @Override
    public IObject getDestination() {
        return extendedModel;
    }
}
