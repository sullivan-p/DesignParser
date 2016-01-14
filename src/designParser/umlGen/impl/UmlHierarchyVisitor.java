package designParser.umlGen.impl;

import designParser.model.impl.ExtendsRelation;
import designParser.model.impl.ImplementsRelation;
import designParser.umlGen.api.UmlModelVisitor;
import designParser.umlGen.util.UmlArrowMarkup;

public class UmlHierarchyVisitor extends UmlModelVisitor{
    private StringBuilder sb;

    public UmlHierarchyVisitor() {
        sb = new StringBuilder();
    }

	public String getUmlMarkup() {
        return sb.toString();
	}

	public void visit(ExtendsRelation r) {
	    sb.append(UmlArrowMarkup.getExtendsArrow(r.getSourceName(), r.getDestinationName()));
	}

    public void visit(ImplementsRelation r) {
        sb.append(UmlArrowMarkup.getImplementsArrow(r.getSourceName(), r.getDestinationName()));
    }	
}
