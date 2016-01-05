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
    private List<IInterface> extendedInterfaces;

    public InterfaceModel(String name) {
        this.name = name;
    }
    
    public InterfaceModel(String name, List<IField> fields, 
            List<IMethod> methods, List<IInterface> extendedInterfaces) {
        this.name = name;
        this.fields = fields;
        this.methods = methods;
        this.extendedInterfaces = extendedInterfaces;
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
    public List<IInterface> getExtendedInterfaces() {
        return extendedInterfaces;
    }

    @Override
    public void setFields(List<IField> fields) {
        this.fields = fields;
    }

    @Override
    public void setMethods(List<IMethod> methods) {
        this.methods = methods;
    }

    @Override
    public void setExtendedInterfaces(List<IInterface> extInterfaces) {
        this.extendedInterfaces = extInterfaces;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
    }
}
