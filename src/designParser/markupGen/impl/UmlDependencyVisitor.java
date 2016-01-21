package designParser.markupGen.impl;

import designParser.markupGen.util.UmlArrowMarkup;
import designParser.model.impl.AssociatesWithRelation;
import designParser.model.impl.ReferencesRelation;

public class UmlDependencyVisitor extends UmlModelVisitor{
    private StringBuilder sb;

    public UmlDependencyVisitor() {
        super();
        
        // Set visit methods.
        addVisitMethod(AssociatesWithRelation.class, (r) -> {
            visitAssociatesWithRelation((AssociatesWithRelation) r); 
        });
        addVisitMethod(ReferencesRelation.class, (r) -> {
            visitReferencesRelation((ReferencesRelation) r); 
        });     
        
        sb = new StringBuilder();
    }
    
    public String getUmlMarkup() {
        return sb.toString();
    }

    public void visitAssociatesWithRelation(AssociatesWithRelation r) {
        sb.append(UmlArrowMarkup.getAssociatesArrow(r.getSourceName(), r.getDestinationName()));
    }

    public void visitReferencesRelation(ReferencesRelation r) {
        sb.append(UmlArrowMarkup.getReferencesArrow(r.getSourceName(), r.getDestinationName()));
    }
}
