package designParser.model.impl;

import designParser.model.api.IDesignModel;
import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class EmptyModelVisitor implements IModelVisitor {

	@Override
	public void previsit(IDesignModel d) {}

	@Override
	public void visit(IDesignModel d) {}

	@Override
	public void postvisit(IDesignModel d) {}

	@Override
	public void previsit(ClassModel c) {}

	@Override
	public void visit(ClassModel c) {}

	@Override
	public void postvisit(ClassModel c) {}

	@Override
	public void previsit(InterfaceModel i) {}

	@Override
	public void visit(InterfaceModel i) {}

	@Override
	public void postvisit(InterfaceModel i) {}

	@Override
	public void previsit(EnumModel e) {}

	@Override
	public void visit(EnumModel e) {}

	@Override
	public void postvisit(EnumModel e) {}

	@Override
	public void previsit(IMethod m) {}

	@Override
	public void visit(IMethod m) {}

	@Override
	public void postvisit(IMethod m) {}

	@Override
	public void previsit(IField f) {}

	@Override
	public void visit(IField f) {}

	@Override
	public void postvisit(IField f) {}

    @Override
    public void previsit(ExtendsRelation r) {}

    @Override
    public void visit(ExtendsRelation r) {}

    @Override
    public void postvisit(ExtendsRelation r) {}

    @Override
    public void previsit(ImplementsRelation r) {}

    @Override
    public void visit(ImplementsRelation r) {}

    @Override
    public void postvisit(ImplementsRelation r) {}

    @Override
    public void previsit(AssociatesWithRelation r) {}

    @Override
    public void visit(AssociatesWithRelation r) {}

    @Override
    public void postvisit(AssociatesWithRelation r) {}

    @Override
    public void previsit(ReferencesRelation r) {}

    @Override
    public void visit(ReferencesRelation r) {}

    @Override
    public void postvisit(ReferencesRelation r) {}
}
