package designParser.model.api;

import designParser.model.impl.AssociatesWithRelation;
import designParser.model.impl.ClassModel;
import designParser.model.impl.EnumModel;
import designParser.model.impl.ExtendsRelation;
import designParser.model.impl.ImplementsRelation;
import designParser.model.impl.InterfaceModel;
import designParser.model.impl.ReferencesRelation;

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
    
    public void previsit(ExtendsRelation r);
    public void visit(ExtendsRelation r);
    public void postvisit(ExtendsRelation r);
    
    public void previsit(ImplementsRelation r);
    public void visit(ImplementsRelation r);
    public void postvisit(ImplementsRelation r);
    
    public void previsit(AssociatesWithRelation r);
    public void visit(AssociatesWithRelation r);
    public void postvisit(AssociatesWithRelation r);
    
    public void previsit(ReferencesRelation r);
    public void visit(ReferencesRelation r);
    public void postvisit(ReferencesRelation r);
}