package designParser.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import designParser.model.api.IClass;
import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;
import designParser.model.api.IObject;

public class ObjectModel implements IObject {
    private String name;
    private Collection<IField> fields;
    private Collection<IMethod> methods;
    
    public ObjectModel(String name) {
        this.name = name;
        this.fields = new ArrayList<IField>();
        this.methods = new ArrayList<IMethod>();
    }
    
    public ObjectModel(String name, List<IField> fields, List<IMethod> methods) {
        this.name = name;
        this.fields = fields;
        this.methods = methods;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<IField> getFields() {
        return fields;
    }

    @Override
    public Collection<IMethod> getMethods() {
        return methods;
    }

    @Override
    public void setFields(Collection<IField> fields) {
        this.fields = fields;
    }

    @Override
    public void setMethods(Collection<IMethod> methods) {
        this.methods = methods;
    }

    @Override
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        for (IField f : fields) {
            f.accept(visitor);
        }
        visitor.visit(this);
        for (IMethod m : methods) {
            m.accept(visitor);
        }
        visitor.postvisit(this);
    }
}
