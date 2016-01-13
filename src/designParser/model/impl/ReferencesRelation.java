package designParser.model.impl;

import designParser.model.api.IObject;

public class ReferencesRelation extends AbstractObjectRelation {
    private IObject sourceModel;
    private IObject referencedModel;

    public ReferencesRelation(ClassModel sourceModel, ClassModel referencedModel) {
        this.sourceModel = sourceModel;
        this.referencedModel = referencedModel;
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
