package designParser.umlGen.impl;

import designParser.model.impl.ExtendsRelation;
import designParser.model.impl.ImplementsRelation;
import designParser.umlGen.api.UmlModelVisitor;
import designParser.umlGen.util.UmlArrowMarkup;

public class UmlHierarchyVisitor extends UmlModelVisitor{
    private StringBuilder sb;

    public UmlHierarchyVisitor() {
        super();
        sb = new StringBuilder();
    }

    @Override
    protected void initVisitMethods() {
        addVisitMethod(ExtendsRelation.class, (r) -> {
            visitExtendsRelation((ExtendsRelation) r); 
        });
        addVisitMethod(ImplementsRelation.class, (r) -> {
            visitImplementsRelation((ImplementsRelation) r); 
        });         
    }	
    
	public String getUmlMarkup() {
        return sb.toString();
	}

	private void visitExtendsRelation(ExtendsRelation r) {
	    sb.append(UmlArrowMarkup.getExtendsArrow(r.getSourceName(), r.getDestinationName()));
	}

    private void visitImplementsRelation(ImplementsRelation r) {
        sb.append(UmlArrowMarkup.getImplementsArrow(r.getSourceName(), r.getDestinationName()));
    }
}
