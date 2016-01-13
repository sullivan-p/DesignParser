package designParser.model.impl;

import designParser.model.api.IObject;

public class AssociatesWithRelation extends AbstractObjectRelation {
    private IObject sourceModel;
    private IObject associatedModel;

    public AssociatesWithRelation(ClassModel sourceModel, ClassModel associatedModel) {
        this.sourceModel = sourceModel;
        this.associatedModel = associatedModel;
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
