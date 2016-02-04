package designParser.model.impl;

import designParser.model.api.IDependencyRelation;

public class DecoratesRelation extends DepRltnDecorator {

    public DecoratesRelation(IDependencyRelation decorated) {
        super(decorated);
    }
}
