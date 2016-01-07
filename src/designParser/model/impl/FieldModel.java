package designParser.model.impl;

import java.util.Collection;

import designParser.model.api.IField;
import designParser.model.api.IModelVisitor;

public class FieldModel extends VariableModel implements IField {
    private AccessLevel accessLevel;

    public FieldModel(String name, String signature, 
            Collection<String> typeNames, AccessLevel accessLevel) {
        super(name, signature, typeNames);
        this.accessLevel = accessLevel;
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
