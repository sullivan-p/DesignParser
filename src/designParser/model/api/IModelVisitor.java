package designParser.model.api;

import designParser.model.impl.ClassModel;
import designParser.model.impl.EnumModel;
import designParser.model.impl.InterfaceModel;

public interface IModelVisitor {
    public void previsit(IDesignModel d);
    public void visit(IDesignModel d);
    public void postvisit(IDesignModel d);
    
    public void previsit(ClassModel c);
    public void visit(ClassModel c);
    public void postvisit(ClassModel c);
    
    public void previsit(InterfaceModel i);
    public void visit(InterfaceModel i);
    public void postvisit(InterfaceModel i);
    
    public void previsit(EnumModel e);
    public void visit(EnumModel e);
    public void postvisit(EnumModel e);

    public void previsit(IMethod m);
    public void visit(IMethod m);
    public void postvisit(IMethod m);
    
    public void previsit(IField f);
    public void visit(IField f);
    public void postvisit(IField f);
}