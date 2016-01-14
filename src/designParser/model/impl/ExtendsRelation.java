package designParser.model.impl;

import designParser.model.api.IObject;

public class ExtendsRelation extends AbstractHierarchyRelation {

    public ExtendsRelation(IObject srcModel, IObject dstModel) {
        super(srcModel, dstModel);
    }
}
