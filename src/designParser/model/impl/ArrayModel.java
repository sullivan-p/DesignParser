package designParser.model.impl;

import java.util.List;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.model.visitor.IModelVisitor;

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
    public List<IField> getFields() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public List<IMethod> getMethods() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
}
