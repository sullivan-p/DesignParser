package designParser.model.impl;

import designParser.model.api.IObject;

public class ImplementsRelation extends AbstractObjectRelation {
    private ClassModel implementationModel;
    private InterfaceModel interfaceModel;

    public ImplementsRelation(ClassModel implementationModel, InterfaceModel interfaceModel) {
        this.implementationModel = implementationModel;
        this.interfaceModel = interfaceModel;
    }

    @Override
    public IObject getSource() {
        return implementationModel;
    }

    @Override
    public IObject getDestination() {
        return interfaceModel;
    }
}
