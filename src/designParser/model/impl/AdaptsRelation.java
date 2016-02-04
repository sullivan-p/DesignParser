package designParser.model.impl;

import designParser.model.api.IDependencyRelation;

public class AdaptsRelation extends DepRltnDecorator {

    public AdaptsRelation(IDependencyRelation decorated) {
        super(decorated);
    }
}
