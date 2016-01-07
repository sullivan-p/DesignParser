package designParser.model.impl;

import java.util.Collection;

import designParser.model.api.IModelVisitor;
import designParser.model.api.IVariable;

public class VariableModel implements IVariable {
    private String name;
    private String signature;
    private Collection<String> typeNames;

    /**
     * @param name
     * @param signature
     * @param typesNames If the variable is not a generic data type, should be a 
     * single-element collection that contains only the variable's type name. 
     * If the variable is a generic type, should be a collection containing the 
     * variable's type name in addition to any type parameter names.
     */
    public VariableModel(String name, String signature, Collection<String> typeNames) {
        this.name = name;
        this.signature = signature;
        this.typeNames = typeNames;
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
    public Collection<String> getTypeNames() {
        return typeNames;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        throw new UnsupportedOperationException();
    }
}
