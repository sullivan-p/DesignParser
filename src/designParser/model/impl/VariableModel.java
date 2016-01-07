package designParser.model.impl;

import java.util.Collection;

import designParser.model.api.IVariable;
import designParser.model.visitor.IModelVisitor;

public class VariableModel implements IVariable {
    private String name;
    private Collection<String> typeNames;

    /**
     * @param name
     * @param types If the variable is not a generic data type, should be a 
     * single-element collection that contains only the variable's type name. 
     * If the variable is a generic type, should be a collection containing the 
     * variable's type name in addition to any type parameter names.
     */
    public VariableModel(String name, Collection<String> typeNames) {
        this.name = name;
        this.typeNames = typeNames;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<String> getTypeNames() {
        return typeNames;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
    }
}
