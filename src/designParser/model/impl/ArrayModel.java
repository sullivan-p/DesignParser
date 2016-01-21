package designParser.model.impl;

import designParser.markupGen.api.IModelVisitor;

public class ArrayModel extends AbstractObjectModel {
    private final static String ARRAY_STR_REPRESENTATION = "[]";
    
    public ArrayModel() {
        super(ARRAY_STR_REPRESENTATION);
    }
    
    @Override
    public void accept(IModelVisitor visitor) {}
}
