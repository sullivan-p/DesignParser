package designParser.model.impl;

import java.util.ArrayList;
import java.util.Collection;

import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;
import designParser.model.visitor.IModelVisitor;

public class InterfaceModel implements IInterface {
	private String name;
	private Collection<IField> fields;
	private Collection<IMethod> methods;
	private Collection<IInterface> extendedInterfaces;

	public InterfaceModel(String name) {
		this.name = name;
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
		this.extendedInterfaces = new ArrayList<IInterface>();
	}

	public InterfaceModel(String name, Collection<IField> fields, Collection<IMethod> methods,
			Collection<IInterface> extendedInterfaces) {
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
	public Collection<IField> getFields() {
		return fields;
	}

	@Override
	public Collection<IMethod> getMethods() {
		return methods;
	}

	@Override
	public Collection<IInterface> getExtendedInterfaces() {
		return extendedInterfaces;
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
	public void setExtendedInterfaces(Collection<IInterface> extInterfaces) {
		this.extendedInterfaces = extInterfaces;
	}

    @Override
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        for (IMethod m : methods) {
            m.accept(visitor);
        }
        visitor.postvisit(this);
    }
}
