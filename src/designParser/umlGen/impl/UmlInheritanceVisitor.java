package designParser.umlGen.impl;

import java.util.Collection;

import designParser.model.api.IClass;
import designParser.model.api.IEnum;
import designParser.model.api.IInterface;
import designParser.umlGen.api.UmlModelVisitor;
import designParser.umlGen.util.UmlArrowMarkup;

public class UmlInheritanceVisitor extends UmlModelVisitor{
    private StringBuilder sb;
    private Collection<String> objNamesToModel;

    public UmlInheritanceVisitor(Collection<String> objNamesToModel) {
        this.objNamesToModel = objNamesToModel;
        sb = new StringBuilder();
    }

	public String getUmlMarkup() {
        return sb.toString();
	}

	public void previsit(IClass c) {
	    IClass superclassModel = c.getExtendedClass();
	    if (superclassModel != null && objNamesToModel.contains(superclassModel.getName())) {
	        sb.append(UmlArrowMarkup.getExtendsArrow(c.getName(), c.getExtendedClass().getName()));
	    }
	    for (IInterface i : c.getInterfaces()) {
	        if (objNamesToModel.contains(i.getName())) {
	            sb.append(UmlArrowMarkup.getImplementsArrow(c.getName(), i.getName()));
	        }
	    }
	}

	public void previsit(IInterface i) {
	    for (IInterface extInterface : i.getExtendedInterfaces()) {
            if (objNamesToModel.contains(extInterface.getName())) {
                sb.append(UmlArrowMarkup.getExtendsArrow(i.getName(), extInterface.getName()));
            }
        }
	}

	public void previsit(IEnum e) {
        for (IInterface i : e.getInterfaces()) {
            if (objNamesToModel.contains(i.getName())) {
                sb.append(UmlArrowMarkup.getImplementsArrow(e.getName(), i.getName()));
            }
        }
    }
}
