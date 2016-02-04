package designParser.model.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.visitor.api.IModelVisitor;

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
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        visitor.visit(this);
        traverseMethodCalls(visitor);
        visitor.postvisit(this);
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
    public void putMethodModel(String methodName, AccessLevel accessLevel, 
            String retTypeName, String[] paramTypeNames, boolean isStatic) {
        
        // Create or update model using no information about access level.
        putMethodModel(methodName, retTypeName, paramTypeNames, isStatic);
        
        // If access level is provided, update access level information.
        String sig = MethodModel.getAbbrevSignature(methodName, paramTypeNames);
        IMethod methodModel = abbrevSigToMethod.get(sig);
        if (accessLevel != null) {
            methodModel.setAccessLevel(accessLevel);
        }
    }
    
    @Override
    public void putMethodModel(String methodName, String retTypeName, String[] paramTypeNames, boolean isStatic) {
        String sig = MethodModel.getAbbrevSignature(methodName, paramTypeNames);
        IMethod maybeMethod = abbrevSigToMethod.get(sig);
        if (maybeMethod == null) {
            // Instantiate the model if no model has been created for this 
            // signature.
            IMethod methodModel = new MethodModel(name, methodName, null, retTypeName, paramTypeNames, isStatic);
            abbrevSigToMethod.put(sig, methodModel);
        } else {
            // Update the existing method model.
            maybeMethod.setParamTypeNames(paramTypeNames);
            maybeMethod.setReturnTypeName(retTypeName);
        }
    }
    
    @Override
    public void putMethodCall(String callerMethodName, String[] callerParamTypeNames, 
            IMethod calleeMethodModel) {
        // Assume the caller method model already exists in the class model.
        IMethod callerMethodModel = this.getMethodModel(callerMethodName, callerParamTypeNames);
        callerMethodModel.putMethodCall(calleeMethodModel);
    }
    
    @Override
    public IMethod getMethodModel(String methodName, String[] paramTypeNames) {
        String sig = MethodModel.getAbbrevSignature(methodName, paramTypeNames);
        IMethod maybeMethod = abbrevSigToMethod.get(sig);
        if (maybeMethod == null) {
            throw new IllegalArgumentException("No such method model exists in this class model.");
        }
        return maybeMethod;
    }
    
    @Override
    public Collection<IField> getAllFieldModels() {
        return nameToField.values();
    }
    
    @Override
    public Collection<IMethod> getAllMethodModels() {
        return abbrevSigToMethod.values();
    }
    
    @Override
    public void setFieldModels(Collection<IField> fields) {
        nameToField = new HashMap<String, IField>();
        for (IField f : fields) {
            nameToField.put(f.getName(), f);
        }
    }
    
    @Override
    public void setMethodModels(Collection<IMethod> methods) {
        abbrevSigToMethod = new HashMap<String, IMethod>();
        for (IMethod m : methods) {
            abbrevSigToMethod.put(m.getAbbrevSignature(), m);
        }
    }
    
    @Override
    public boolean getIsConcrete() {
        return false;
    }
    
    /**
     * If visitor intends to traverse through method calls and the class model 
     * contains the visitor's target method call, then start traversing the 
     * visitor through method calls.
     */
    private void traverseMethodCalls(IModelVisitor visitor) {
        String mthdClassName = visitor.getTargetMethodClassName();
        if (mthdClassName == null || !mthdClassName.equals(name)) {
            return;
        }
        
        String mthdName = visitor.getTargetMethodName();
        String[] mthdParamTypes = visitor.getTargetMethodParamTypeNames();
        if (mthdName == null || mthdParamTypes == null) {
            return;
        }
        
        String mthdSig = MethodModel.getAbbrevSignature(mthdName, mthdParamTypes);
        if (abbrevSigToMethod.containsKey(mthdSig) && visitor.getMaxCallDepth() > 0) {
            abbrevSigToMethod.get(mthdSig).accept(visitor);
        }
    }
}
