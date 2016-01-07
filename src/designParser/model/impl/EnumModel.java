package designParser.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import designParser.model.api.IEnum;
import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class EnumModel implements IEnum {
	private String name;
	private Collection<IField> fields;
	private Collection<IMethod> methods;
	private Collection<IInterface> interfaces;
	private Collection<String> enumElements;

	public EnumModel(String name) {
		this.name = name;
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
		this.interfaces = new ArrayList<IInterface>();
		this.enumElements = new ArrayList<String>();
	}

	public EnumModel(String name, List<IField> fields, List<IMethod> methods, List<IInterface> interfaces,
			List<String> enumElements) {
		this.name = name;
		this.fields = fields;
		this.methods = methods;
		this.interfaces = interfaces;
		this.enumElements = enumElements;
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
	public Collection<IInterface> getInterfaces() {
		return interfaces;
	}

	@Override
	public Collection<String> getEnumElements() {
		return enumElements;
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
	public void setInterfaces(Collection<IInterface> interfaces) {
		this.interfaces = interfaces;
	}

	@Override
	public void setEnumElements(Collection<String> enumElements) {
		this.enumElements = enumElements;
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
