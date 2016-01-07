package designParser.model.impl;

import java.util.Collection;

import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class MethodModel implements IMethod {
    private String name;
    private Collection<String> refTypeNames;
    private AccessLevel accessLevel;
    private String methodSig;

    public MethodModel(String name, Collection<String> refTypeNames, 
            AccessLevel accessLevel, String methodSig) {
        this.name = name;
        this.refTypeNames = refTypeNames;
        this.accessLevel = accessLevel;
        this.methodSig = methodSig;
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
    public Collection<String> getReferencedTypeNames() {
        return refTypeNames;
    }

    @Override
    public String getMethodSignature() {
        return methodSig;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.visit(this);
    }
}
