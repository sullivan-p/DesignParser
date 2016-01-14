package designParser.model.impl;

import designParser.model.api.IObject;

public class AssociatesWithRelation extends AbstractDependencyRelation {
   
    public AssociatesWithRelation(IObject src, IObject dst) {
        super(src, dst);
    }
}
