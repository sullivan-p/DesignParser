package designParser.model.impl;

import java.util.List;

import designParser.model.api.IEnum;
import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;
import designParser.model.visitor.IModelVisitor;

public class EnumModel implements IEnum {
	private String name;
	private List<IField> fields;
	private List<IMethod> methods;
	private List<IInterface> interfaces;
	private List<String> enumElements;

	public EnumModel(String name) {
	    this.name = name;
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
	public List<IField> getFields() {
		return fields;
	}

	@Override
	public List<IMethod> getMethods() {
		return methods;
	}

	@Override
	public List<IInterface> getInterfaces() {
		return interfaces;
	}

	@Override
	public List<String> getEnumElements() {
		return enumElements;
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
    public void setInterfaces(List<IInterface> interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    public void setEnumElements(List<String> enumElements) {
        this.enumElements = enumElements;
    }

	@Override
	public void accept(IModelVisitor visitor) {
		//TODO: Implement this
	}
}
