package designParser.model.impl;

import java.util.HashMap;
import java.util.Map;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;

public abstract class AbstractObjectModel implements IObject {
    private String name;
    protected Map<String, IField> nameToField;
    
    // Use signatures without access levels as keys that map to method models.
    protected Map<String, IMethod> sigNoAccessLvlToMethod;
    
    public AbstractObjectModel(String name) {
        this.name = name;
        this.nameToField = new HashMap<String, IField>();
        this.sigNoAccessLvlToMethod = new HashMap<String, IMethod>();
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
        String sig = MethodModel.getSignatureNoAccessLvl(methodName, retTypeName, paramTypeNames);
        if (sigNoAccessLvlToMethod.get(sig) == null) {
            sigNoAccessLvlToMethod.put(sig, new MethodModel(objName, methodName, accessLevel, 
                    retTypeName, paramTypeNames));
        }
    }
}
