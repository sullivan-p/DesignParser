package designParser.umlGen.impl;

import designParser.model.impl.ExtendsRelation;
import designParser.model.impl.ImplementsRelation;
import designParser.umlGen.api.UmlModelVisitor;
import designParser.umlGen.util.UmlArrowMarkup;

public class UmlHierarchyVisitor extends UmlModelVisitor{
    private StringBuilder sb;

    public UmlHierarchyVisitor() {
        super();
        
        // Set visit methods.
        addVisitMethod(ExtendsRelation.class, (r) -> {
            visitExtendsRelation((ExtendsRelation) r); 
        });
        addVisitMethod(ImplementsRelation.class, (r) -> {
            visitImplementsRelation((ImplementsRelation) r); 
        });      
        
        sb = new StringBuilder();
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
