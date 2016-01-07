package designParser.model.impl;

import designParser.model.api.IClass;
import designParser.model.api.IDesignModel;
import designParser.model.api.IEnum;
import designParser.model.api.IField;
import designParser.model.api.IInterface;
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
	public void previsit(IClass c) {}

	@Override
	public void visit(IClass c) {}

	@Override
	public void postvisit(IClass c) {}

	@Override
	public void previsit(IInterface i) {}

	@Override
	public void visit(IInterface i) {}

	@Override
	public void postvisit(IInterface i) {}

	@Override
	public void previsit(IEnum e) {}

	@Override
	public void visit(IEnum e) {}

	@Override
	public void postvisit(IEnum e) {}

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

}
