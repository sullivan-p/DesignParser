package designParser.umlGen.impl;

import designParser.model.impl.AssociatesWithRelation;
import designParser.model.impl.ReferencesRelation;
import designParser.umlGen.api.UmlModelVisitor;
import designParser.umlGen.util.UmlArrowMarkup;

public class UmlAssociationVisitor extends UmlModelVisitor{
    private StringBuilder sb;

    public UmlAssociationVisitor() {
        sb = new StringBuilder();
    }

    public String getUmlMarkup() {
        return sb.toString();
    }

    public void visit(AssociatesWithRelation r) {
        sb.append(UmlArrowMarkup.getAssociatesArrow(r.getSourceName(), r.getDestinationName()));
    }

    public void visit(ReferencesRelation r) {
        sb.append(UmlArrowMarkup.getReferencesArrow(r.getSourceName(), r.getDestinationName()));
    }   
}
