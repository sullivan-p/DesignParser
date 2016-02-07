package designParser.markupGen.impl;

import designParser.markupGen.util.UmlArrowMarkup;
import designParser.markupGen.util.UmlProcessString;
import designParser.model.impl.AssociatesWithRelation;
import designParser.model.impl.DecoratesRelation;
import designParser.model.impl.ReferencesRelation;

public class UmlDependencyVisitor extends UmlModelVisitor{
    private StringBuilder sb;
    private String currLabel;  // Label for the currently visited relation.

    public UmlDependencyVisitor() {
        super();
        this.currLabel = "";
        
        // Set visit methods.
        addVisitMethod(AssociatesWithRelation.class, (r) -> {
            visitAssociatesWithRelation((AssociatesWithRelation) r); 
        });
        addVisitMethod(ReferencesRelation.class, (r) -> {
            visitReferencesRelation((ReferencesRelation) r); 
        });     
        addVisitMethod(DecoratesRelation.class, (r) -> {
            currLabel = UmlProcessString.escapeAngleBraces("<<decorates>>");
        });     
        
        this.sb = new StringBuilder();
    }
    
    public String getUmlMarkup() {
        return sb.toString();
    }

    private void visitAssociatesWithRelation(AssociatesWithRelation r) {
        sb.append(UmlArrowMarkup.getAssociatesArrow(r.getSourceName(), r.getDestinationName(), currLabel));
        currLabel = "";
    }

    private void visitReferencesRelation(ReferencesRelation r) {
        sb.append(UmlArrowMarkup.getReferencesArrow(r.getSourceName(), r.getDestinationName(), currLabel));
        currLabel = "";
    }
}
