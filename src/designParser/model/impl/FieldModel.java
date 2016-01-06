package designParser.model.impl;

import java.util.Collection;

import designParser.model.api.AccessLevel;
import designParser.model.api.IDataType;
import designParser.model.api.IField;
import designParser.model.visitor.IModelVisitor;

public class FieldModel extends VariableModel implements IField {
    private AccessLevel accessLevel;

    public FieldModel(String name, Collection<IDataType> types, AccessLevel accessLevel) {
        super(name, types);
        this.accessLevel = accessLevel;
    }

    @Override
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
    }
}
