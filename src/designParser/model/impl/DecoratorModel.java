package designParser.model.impl;

import designParser.model.api.IObject;

public class DecoratorModel extends ObjectModelDecorator {

    public DecoratorModel(IObject decorated) {
        super(decorated);
    }
}
