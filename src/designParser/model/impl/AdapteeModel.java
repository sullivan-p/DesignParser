package designParser.model.impl;

import designParser.model.api.IObject;

public class AdapteeModel extends ObjectModelDecorator {

    public AdapteeModel(IObject decorated) {
        super(decorated);
    }
}
