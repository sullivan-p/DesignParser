package designParser.model.impl;

import java.util.List;

import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;
import designParser.model.visitor.IModelVisitor;

public class InterfaceModel implements IInterface {
    private String name;
    private List<IField> fields;
    private List<IMethod> methods;
    private IInterface extendedInterface;

    public InterfaceModel(String name, List<IField> fields, 
            List<IMethod> methods, IInterface extendedInterface) {
        this.name = name;
        this.fields = fields;
        this.methods = methods;
        this.extendedInterface = extendedInterface;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public List<IField> getFields() {
        return fields;
    }

    @Override
    public List<IMethod> getMethods() {
        return methods;
    }

    @Override
    public IInterface getExtendedInterface() {
        return extendedInterface;
    }

    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
    }
}
