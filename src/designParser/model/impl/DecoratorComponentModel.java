package designParser.model.impl;

import designParser.model.api.IObject;

public class DecoratorComponentModel extends ObjectModelDecorator {

    public DecoratorComponentModel(IObject decorated) {
        super(decorated);
    }
}
