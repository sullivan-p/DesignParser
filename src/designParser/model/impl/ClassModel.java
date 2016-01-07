package designParser.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import designParser.model.api.IClass;
import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class ClassModel implements IClass {
	private String name;
	private Collection<IField> fields;
	private Collection<IMethod> methods;
	private IClass extendedClass;
	private Collection<IInterface> interfaces;
	private boolean isConcrete;

	public ClassModel(String name, boolean isConcrete) {
		this.name = name;
		this.isConcrete = isConcrete;
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
		this.interfaces = new ArrayList<IInterface>();
	}

	public ClassModel(String name, List<IField> fields, List<IMethod> methods, IClass extendedClass,
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
	public Collection<IField> getFields() {
		return fields;
	}

	@Override
	public Collection<IMethod> getMethods() {
		return methods;
	}

	@Override
	public IClass getExtendedClass() {
		return extendedClass;
	}

	@Override
	public Collection<IInterface> getInterfaces() {
		return interfaces;
	}

	@Override
	public boolean getIsConcrete() {
		return isConcrete;
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
	public void setExtendedClass(IClass extClass) {
		this.extendedClass = extClass;
	}

	@Override
	public void setInterfaces(Collection<IInterface> interfaces) {
		this.interfaces = interfaces;
	}

	@Override
	public void setIsConcrete(boolean isConcrete) {
		this.isConcrete = isConcrete;
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
