package designParser.model.impl;

import designParser.markupGen.api.IModelVisitor;
import designParser.model.api.IField;

public class FieldModel extends VariableModel implements IField {
    private AccessLevel accessLevel;

    public FieldModel(String name, AccessLevel accessLevel, String signature) {
        super(name, signature);
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
