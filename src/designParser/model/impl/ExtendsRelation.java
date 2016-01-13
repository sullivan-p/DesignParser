package designParser.model.impl;

import designParser.model.api.IObject;

public class ExtendsRelation extends AbstractObjectRelation {
    private ClassModel subclassModel;
    private ClassModel superclassModel;

    public ExtendsRelation(ClassModel subclassModel, ClassModel superclassModel) {
        this.subclassModel = subclassModel;
        this.superclassModel = superclassModel;
    }

    @Override
    public IObject getSource() {
        return subclassModel;
    }

    @Override
    public IObject getDestination() {
        return superclassModel;
    }
}
