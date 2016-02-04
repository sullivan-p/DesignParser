package designParser.model.impl;

import designParser.model.api.IObject;

public class SingletonModel extends ObjectModelDecorator {

    public SingletonModel(IObject decorated) {
        super(decorated);
    }
}
