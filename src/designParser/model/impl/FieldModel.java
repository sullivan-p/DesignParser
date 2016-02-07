package designParser.model.impl;

import designParser.model.api.IField;
import designParser.visitor.api.IModelVisitor;

public class FieldModel implements IField {
    private String name;
    private AccessLevel accessLevel;
    private String typeName;
   
    public FieldModel(String name, AccessLevel accessLevel, String typeName) {
        this.name = name;
        this.accessLevel = accessLevel;
        this.typeName = typeName;
    }
        
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.visit(this);
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
    public String getTypeName() {
        return typeName;
    }
    
    @Override
    public String getSignature() {
        return accessLevel.toUmlString() + " " + typeName + " " + name;
    }
}
