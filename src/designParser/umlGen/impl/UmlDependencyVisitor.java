package designParser.umlGen.impl;

import designParser.model.impl.AssociatesWithRelation;
import designParser.model.impl.ReferencesRelation;
import designParser.umlGen.api.UmlModelVisitor;
import designParser.umlGen.util.UmlArrowMarkup;

public class UmlDependencyVisitor extends UmlModelVisitor{
    private StringBuilder sb;

    public UmlDependencyVisitor() {
        super();
        sb = new StringBuilder();
    }

    @Override
    protected void initVisitMethods() {
        addVisitMethod(AssociatesWithRelation.class, (r) -> {
            visitAssociatesWithRelation((AssociatesWithRelation) r); 
        });
        addVisitMethod(ReferencesRelation.class, (r) -> {
            visitReferencesRelation((ReferencesRelation) r); 
        });     
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
