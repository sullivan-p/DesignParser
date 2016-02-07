package designParser.markupGen.impl;

import designParser.markupGen.util.UmlArrowMarkup;
import designParser.model.impl.ExtendsRelation;
import designParser.model.impl.ImplementsRelation;

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
	    sb.append(UmlArrowMarkup.getExtendsArrow(r.getSourceName(), r.getDestinationName(), ""));
	}

    private void visitImplementsRelation(ImplementsRelation r) {
        sb.append(UmlArrowMarkup.getImplementsArrow(r.getSourceName(), r.getDestinationName(), ""));
    }
}
