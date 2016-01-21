package designParser.model.impl;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IModelVisitor;

public class InterfaceModel extends AbstractObjectModel {

	public InterfaceModel(String name) {
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
