package designParser.model.impl;

import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class MethodModel implements IMethod {
    private String name;
    private AccessLevel accessLevel;
    private String signature;

    public MethodModel(String name, AccessLevel accessLevel, String signature) {
        this.name = name;
        this.accessLevel = accessLevel;
        this.signature = signature;
    }

    @Override
    public String getSignature() {
        return signature;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    @Override
    public void accept(IModelVisitor visitor) {
        visitor.visit(this);
    }
}
