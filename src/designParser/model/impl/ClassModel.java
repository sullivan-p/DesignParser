package designParser.model.impl;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class ClassModel extends AbstractObjectModel {
	protected boolean isConcrete;

	public ClassModel(String name, boolean isConcrete) {
		super(name);
		this.isConcrete = isConcrete;
	}

	public boolean getIsConcrete() {
		return isConcrete;
	}
	
    public void putMethodCall(
            String callerClassName, String callerMethodName, String[] callerParamTypeNames,
            String calleeClassName, String calleeMethodName,
            String[] calleeParamTypeNames, String calleeReturnTypeName, boolean isConstructor) {
        
        String sig = MethodModel.getAbbrevSignature(callerMethodName, callerParamTypeNames);
        if (!abbrevSigToMethod.containsKey(sig) || abbrevSigToMethod.get(sig) == null) {
            StringBuilder error = new StringBuilder();
            error.append("The caller method model must be created before ");
            error.append("method calls can be assigned to it.");
            throw new IllegalArgumentException(error.toString());
        }
        
        IMethod mthdModel = abbrevSigToMethod.get(sig);
        mthdModel.putMethodCall(calleeClassName, calleeMethodName, 
                calleeParamTypeNames, calleeReturnTypeName, isConstructor);
    }
	
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        for (IField f : nameToField.values()) {
            f.accept(visitor);
        }
        visitor.visit(this);
        for (IMethod m : abbrevSigToMethod.values()) {
            m.accept(visitor);
        }
        visitor.postvisit(this);
    }
}
