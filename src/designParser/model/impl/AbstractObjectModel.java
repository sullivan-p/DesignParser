package designParser.model.impl;

import java.util.HashMap;
import java.util.Map;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;

public abstract class AbstractObjectModel implements IObject {
    private String name;
    protected Map<String, IField> nameToField;
    
    // Use abbreviated signature (method name and parameter type names only) as 
    // keys that map to method models.
    protected Map<String, IMethod> abbrevSigToMethod;
    
    public AbstractObjectModel(String name) {
        this.name = name;
        this.nameToField = new HashMap<String, IField>();
        this.abbrevSigToMethod = new HashMap<String, IMethod>();
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void putFieldModel(String fieldName, AccessLevel accessLevel, String fieldSig) {
        // Instantiate the model if no model has been created for this 
        // signature.
        if (nameToField.get(fieldName) == null) {
            nameToField.put(fieldName, new FieldModel(fieldName, accessLevel, fieldSig));
        }
    }
    
    @Override
    public void putMethodModel(String objName, String methodName, AccessLevel accessLevel, 
            String retTypeName, String[] paramTypeNames) {
        // Instantiate the model if no model has been created for this 
        // signature.
        String sig = MethodModel.getAbbrevSignature(methodName, paramTypeNames);
        if (abbrevSigToMethod.get(sig) == null) {
            abbrevSigToMethod.put(sig, new MethodModel(objName, methodName, accessLevel, 
                    retTypeName, paramTypeNames));
        }
    }
}
