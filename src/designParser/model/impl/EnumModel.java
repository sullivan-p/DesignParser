package designParser.model.impl;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class EnumModel extends AbstractObjectModel {

	public EnumModel(String name) {
		super(name);
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
