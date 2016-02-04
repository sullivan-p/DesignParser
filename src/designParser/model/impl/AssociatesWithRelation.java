package designParser.model.impl;

import designParser.visitor.api.IModelVisitor;

public class AssociatesWithRelation extends AbstractDependencyRelation {
   
    public AssociatesWithRelation(String srcName, String dstName) {
        super(srcName, dstName);
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        visitor.visit(this);
        visitor.postvisit(this);
    }
}
