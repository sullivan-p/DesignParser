package designParser.model.impl;

import designParser.model.api.IObject;

public class AdapterTargetModel extends ObjectModelDecorator {

    public AdapterTargetModel(IObject decorated) {
        super(decorated);
    }
}
