package designParser.model.impl;

import designParser.model.api.IObject;

public class AdapterModel extends ObjectModelDecorator {

    public AdapterModel(IObject decorated) {
        super(decorated);
    }
}
