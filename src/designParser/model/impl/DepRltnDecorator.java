package designParser.model.impl;

import designParser.model.api.IDependencyRelation;
import designParser.visitor.api.IModelVisitor;

public abstract class DepRltnDecorator implements IDependencyRelation {
    private IDependencyRelation decorated;
    
    public DepRltnDecorator(IDependencyRelation decorated) {
        this.decorated = decorated;
    }

    @Override
    public int compareTo(IDependencyRelation r) {
        return decorated.compareTo(r);
    }

    @Override
    public String getSourceName() {
        return decorated.getSourceName();
    }

    @Override
    public String getDestinationName() {
        return decorated.getDestinationName();
    }

    @Override
    public void accept(IModelVisitor visitor) {
        visitor.visit(this);
        decorated.accept(visitor);
    }
}
