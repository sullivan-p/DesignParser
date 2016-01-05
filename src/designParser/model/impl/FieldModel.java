package designParser.model.impl;

import designParser.model.api.AccessLevel;
import designParser.model.api.IDataType;
import designParser.model.api.IField;
import designParser.model.visitor.IModelVisitor;

public class FieldModel extends VariableModel implements IField {
    private AccessLevel accessLevel;

    public FieldModel(String name, IDataType type, AccessLevel accessLevel) {
        super(name, type);
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
