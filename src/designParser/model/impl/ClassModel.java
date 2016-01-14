package designParser.model.impl;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class ClassModel extends AbstractObjectModel {
	private boolean isConcrete;

	public ClassModel(String name, boolean isConcrete) {
		super(name);
		this.isConcrete = isConcrete;
	}

	public boolean getIsConcrete() {
		return isConcrete;
	}

	public void setIsConcrete(boolean isConcrete) {
		this.isConcrete = isConcrete;
	}
	
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        for (IField f : nameToFieldMap.values()) {
            f.accept(visitor);
        }
        visitor.visit(this);
        for (IMethod m : sigToMethodMap.values()) {
            m.accept(visitor);
        }
        visitor.postvisit(this);
    }
}
