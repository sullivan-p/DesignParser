package designParser.model.api;

import designParser.visitor.api.IModelVisitor;

public abstract class PrimitiveDataType implements IDataType {
    private String name;
    
    public PrimitiveDataType(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {}
}
