package designParser.model.impl;

import designParser.model.api.IObject;

public class ReferencesRelation extends AbstractObjectRelation {
    private IObject sourceModel;
    private IObject referencedModel;

    public ReferencesRelation(IObject x, IObject y) {
        this.sourceModel = x;
        this.referencedModel = y;
    }

    @Override
    public IObject getSource() {
        return sourceModel;
    }

    @Override
    public IObject getDestination() {
        return referencedModel;
    }
}
