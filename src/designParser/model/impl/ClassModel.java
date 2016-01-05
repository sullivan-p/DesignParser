package designParser.model.impl;

import java.util.List;

import designParser.model.api.IClass;
import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;
import designParser.model.visitor.IModelVisitor;

public class ClassModel implements IClass {
    private String name;
    private List<IField> fields;
    private List<IMethod> methods;
    private IClass extendedClass;
    private List<IInterface> interfaces;
    private boolean isConcrete;
    
    public ClassModel(String name, boolean isConcrete) {
        this.name = name;
        this.isConcrete = isConcrete;
    }
    
    public ClassModel(String name, List<IField> fields, 
            List<IMethod> methods, IClass extendedClass,
            List<IInterface> interfaces, boolean isConcrete) {
        this(name, isConcrete);
        this.fields = fields;
        this.methods = methods;
        this.extendedClass = extendedClass;
        this.interfaces = interfaces;
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
    public IClass getExtendedClass() {
        return extendedClass;
    }

    @Override
    public List<IInterface> getInterfaces() {
        return interfaces;
    }
    
    @Override
    public boolean getIsConcrete() {
        return isConcrete;
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
    public void setExtendedClass(IClass extClass) {
        this.extendedClass = extClass;
    }

    @Override
    public void setInterfaces(List<IInterface> interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    public void setIsConcrete(boolean isConcrete) {
        this.isConcrete = isConcrete;
    }
    
    @Override
    public void accept(IModelVisitor visitor) {
        // TODO Auto-generated method stub
    }
}
