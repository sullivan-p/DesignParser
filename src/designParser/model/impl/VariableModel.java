package designParser.model.impl;

import designParser.model.api.IModelVisitor;
import designParser.model.api.IVariable;

public class VariableModel implements IVariable {
    private String name;
    private String signature;

    public VariableModel(String name, String signature) {
        this.name = name;
        this.signature = signature;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getSignature() {
        return signature;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        throw new UnsupportedOperationException();
    }
}
