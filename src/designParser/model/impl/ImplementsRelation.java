package designParser.model.impl;

import designParser.markupGen.api.IModelVisitor;

public class ImplementsRelation extends AbstractHierarchyRelation {
    
    public ImplementsRelation(String srcName, String dstName) {
        super(srcName, dstName);
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        visitor.visit(this);
        visitor.postvisit(this);
    }
}
