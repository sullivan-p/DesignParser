package designParser.model.impl;

import java.util.ArrayList;
import java.util.List;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IMethodCall;
import designParser.model.api.IModelVisitor;

public class ClassModel extends AbstractObjectModel {
	protected boolean isConcrete;
    protected List<IMethodCall> methodCalls;

	public ClassModel(String name, boolean isConcrete) {
		super(name);
		this.isConcrete = isConcrete;
        this.methodCalls = new ArrayList<IMethodCall>();
	}

	public boolean getIsConcrete() {
		return isConcrete;
	}
	
    public void putMethodCall(String callerClassName, String callerMethodName,
            String calleeClassName, String calleeMethodName,
            String[] paramTypeNames, String returnTypeName, boolean isConstructor) {
        methodCalls.add(new MethodCall(callerClassName, callerMethodName,
                calleeClassName, calleeMethodName, 
                paramTypeNames, returnTypeName, isConstructor));
    }
	
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        for (IField f : nameToField.values()) {
            f.accept(visitor);
        }
        visitor.visit(this);
        for (IMethod m : sigNoAccessLvlToMethod.values()) {
            m.accept(visitor);
        }
        visitor.postvisit(this);
    }
}
