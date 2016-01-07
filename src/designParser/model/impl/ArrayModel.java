package designParser.model.impl;

import java.util.Collection;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;
import designParser.model.api.IObject;

public class ArrayModel implements IObject {
    private String name;

    public ArrayModel() {
        name = "[]";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<IField> getFields() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<IMethod> getMethods() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
}
