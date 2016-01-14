package designParser.model.impl;

import java.util.HashMap;
import java.util.Map;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;

public abstract class AbstractObjectModel implements IObject {
    private String name;
    protected Map<String, IField> nameToFieldMap;
    protected Map<String, IMethod> sigToMethodMap;
    
    public AbstractObjectModel(String name) {
        this.name = name;
        this.nameToFieldMap = new HashMap<String, IField>();
        this.sigToMethodMap = new HashMap<String, IMethod>();
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void putFieldModel(String fieldName, AccessLevel accessLevel, String fieldSig) {
        // Instantiate the model if no model has been created for this 
        // signature.
        if (nameToFieldMap.get(fieldName) == null) {
            nameToFieldMap.put(fieldName, new FieldModel(fieldName, accessLevel, fieldSig));
        }
    }
    
    @Override
    public void putMethodModel(String methodName, AccessLevel accessLevel, String methodSig) {
        // Instantiate the model if no model has been created for this 
        // signature.
        if (sigToMethodMap.get(methodName) == null) {
            sigToMethodMap.put(methodName, new MethodModel(methodName, accessLevel, methodSig));
        }
    }
}
