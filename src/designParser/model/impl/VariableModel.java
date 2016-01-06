package designParser.model.impl;

import java.util.Collection;

import designParser.model.api.IDataType;
import designParser.model.api.IVariable;
import designParser.model.visitor.IModelVisitor;

public class VariableModel implements IVariable {
    private String name;
    private Collection<IDataType> types;

    /**
     * @param name
     * @param types If the variable is not a generic data type, should be a 
     * single-element collection that contains only the variable's data type. 
     * If the variable is a generic type, should be a list that contains the 
     * variable's type in addition to any type parameters.
     */
    public VariableModel(String name, Collection<IDataType> types) {
        this.name = name;
        this.types = types;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<IDataType> getTypes() {
        return types;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
    }
}
