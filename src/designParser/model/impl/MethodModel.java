package designParser.model.impl;

import java.util.List;

import designParser.model.api.AccessLevel;
import designParser.model.api.IMethod;
import designParser.model.api.IVariable;
import designParser.model.visitor.IModelVisitor;

public class MethodModel implements IMethod {
    private String name;
    private AccessLevel accessLevel;
    private List<IVariable> params;

    public MethodModel(String name, AccessLevel accessLevel, List<IVariable> params) {
        this.name = name;
        this.accessLevel = accessLevel;
        this.params = params;
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
    public List<IVariable> getParams() {
        return params;
    }

    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
    }
}
