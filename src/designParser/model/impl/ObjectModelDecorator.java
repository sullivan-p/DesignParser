package designParser.model.impl;

import java.util.Collection;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.visitor.api.IModelVisitor;

public abstract class ObjectModelDecorator implements IObject {
    private IObject decorated;

    public ObjectModelDecorator(IObject decorated) {
        this.decorated = decorated;
    }
    
    @Override
    public String getName() {
        return decorated.getName();
    }

    @Override
    public void accept(IModelVisitor visitor) {
        visitor.visit(this);
        decorated.accept(visitor);
    }

    @Override
    public void putMethodModel(String methodName, AccessLevel accessLevel, String retTypeName, String[] paramTypeNames,
            boolean isStatic) {
        decorated.putMethodModel(methodName, accessLevel, retTypeName, paramTypeNames, isStatic);
    }

    @Override
    public void putMethodModel(String methodName, String retTypeName, String[] paramTypeNames, boolean isStatic) {
        decorated.putMethodModel(methodName, retTypeName, paramTypeNames, isStatic);
    }

    @Override
    public void putFieldModel(String fieldName, AccessLevel accessLevel, String fieldSig) {
        decorated.putFieldModel(fieldName, accessLevel, fieldSig);
    }

    @Override
    public void putMethodCall(String callerMethodName, String[] callerParamTypeNames, IMethod calleeMethodModel) {
        decorated.putMethodCall(callerMethodName, callerParamTypeNames, calleeMethodModel);
    }

    @Override
    public IMethod getMethodModel(String methodName, String[] paramTypeNames) {
        return decorated.getMethodModel(methodName, paramTypeNames);
    }

    @Override
    public Collection<IField> getAllFieldModels() {
        return decorated.getAllFieldModels();
    }

    @Override
    public Collection<IMethod> getAllMethodModels() {
        return decorated.getAllMethodModels();
    }

    @Override
    public void setFieldModels(Collection<IField> fields) {
        decorated.setFieldModels(fields);
    }

    @Override
    public void setMethodModels(Collection<IMethod> methods) {
        decorated.setMethodModels(methods);
    }

    @Override
    public boolean getIsConcrete() {
        return decorated.getIsConcrete();
    }
}
