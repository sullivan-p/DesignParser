package designParser.model.impl;

import designParser.model.api.IModelVisitor;

public class ReferencesRelation extends AbstractDependencyRelation {

    public ReferencesRelation(String srcName, String dstName) {
        super(srcName, dstName);
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        visitor.visit(this);
        visitor.postvisit(this);
    }
}
