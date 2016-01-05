package designParser.model.api;

import designParser.model.visitor.IModelVisitor;

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
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
    }
}