package designParser.model.impl;

import designParser.model.api.IDataType;
import designParser.model.api.IVariable;
import designParser.model.visitor.IModelVisitor;

public class VariableModel implements IVariable {
    private String name;
    private IDataType type;

    public VariableModel(String name, IDataType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IDataType getType() {
        return type;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
    }
}
