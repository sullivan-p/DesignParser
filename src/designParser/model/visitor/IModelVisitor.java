package designParser.model.visitor;

import designParser.model.api.IClass;
import designParser.model.api.IDesignModel;
import designParser.model.api.IEnum;
import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;

public interface IModelVisitor {
    public void previsit(IDesignModel d);
    public void visit(IDesignModel d);
    public void postvisit(IDesignModel d);
    
    public void previsit(IClass c);
    public void visit(IClass c);
    public void postvisit(IClass c);
    
    public void previsit(IInterface i);
    public void visit(IInterface i);
    public void postvisit(IInterface i);
    
    public void previsit(IEnum e);
    public void visit(IEnum e);
    public void postvisit(IEnum e);

    public void previsit(IMethod m);
    public void visit(IMethod m);
    public void postvisit(IMethod m);
    
    public void previsit(IField f);
    public void visit(IField f);
    public void postvisit(IField f);
}