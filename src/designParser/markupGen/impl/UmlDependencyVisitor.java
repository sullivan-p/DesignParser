package designParser.markupGen.impl;

import java.util.ArrayList;
import java.util.Collection;

import designParser.markupGen.util.UmlArrowMarkup;
import designParser.markupGen.util.UmlProcessString;
import designParser.model.impl.AdaptsRelation;
import designParser.model.impl.AssociatesWithRelation;
import designParser.model.impl.DecoratesRelation;
import designParser.model.impl.ReferencesRelation;

public class UmlDependencyVisitor extends UmlModelVisitor{
    private StringBuilder sb;
    private Collection<String> currLabels;  // Label for the currently visited relation.

    public UmlDependencyVisitor() {
        super();
        this.currLabels = new ArrayList<String>();
        
        // Set visit methods.
        addVisitMethod(AssociatesWithRelation.class, (r) -> {
            visitAssociatesWithRelation((AssociatesWithRelation) r); 
        });
        addVisitMethod(ReferencesRelation.class, (r) -> {
            visitReferencesRelation((ReferencesRelation) r); 
        });     
        addVisitMethod(DecoratesRelation.class, (r) -> {
            currLabels.add(UmlProcessString.escapeAngleBraces("<<decorates>>"));
        });     
        addVisitMethod(AdaptsRelation.class, (r) -> {
            currLabels.add(UmlProcessString.escapeAngleBraces("<<adapts>>"));
        });     
        
        this.sb = new StringBuilder();
    }
    
    public String getUmlMarkup() {
        return sb.toString();
    }

    private void visitAssociatesWithRelation(AssociatesWithRelation r) {
        sb.append(UmlArrowMarkup.getAssociatesArrow(r.getSourceName(), r.getDestinationName(), String.join(", ", currLabels)));
        currLabels.clear();
    }

    private void visitReferencesRelation(ReferencesRelation r) {
        sb.append(UmlArrowMarkup.getReferencesArrow(r.getSourceName(), r.getDestinationName(), String.join(", ", currLabels)));
        currLabels.clear();
    }
}
