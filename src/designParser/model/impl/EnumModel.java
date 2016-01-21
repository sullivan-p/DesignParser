package designParser.model.impl;

import designParser.markupGen.api.IModelVisitor;
import designParser.model.api.IField;
import designParser.model.api.IMethod;

public class EnumModel extends AbstractObjectModel {

	public EnumModel(String name) {
		super(name);
	}
	
    @Override
    public void accept(IModelVisitor visitor) {
        visitor.previsit(this);
        for (IField f : nameToField.values()) {
            f.accept(visitor);
        }
        visitor.visit(this);
        for (IMethod m : abbrevSigToMethod.values()) {
            m.accept(visitor);
        }
        visitor.postvisit(this);
    }
}
