package designParser.model.impl;

import designParser.model.api.IObject;

public class ReferencesRelation extends AbstractDependencyRelation {

    public ReferencesRelation(IObject src, IObject dst) {
        super(src, dst);
    }
}
