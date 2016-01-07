package designParser.umlGen.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import designParser.model.api.IClass;
import designParser.model.api.IEnum;
import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.umlGen.api.UmlModelVisitor;
import designParser.umlGen.util.UmlArrowMarkup;

public class UmlAssociationVisitor extends UmlModelVisitor{
    private StringBuilder sb;
    private Collection<String> objNamesToModel;

    public UmlAssociationVisitor(Collection<String> objNamesToModel) {
        this.objNamesToModel = objNamesToModel;
        sb = new StringBuilder();
    }

    public String getUmlMarkup() {
        return sb.toString();
    }

    public void visit(IClass c) {
        appendObjAssociatesArrows(c);
        appendObjUsesArrows(c);
    }

    public void visit(IInterface i) {
        appendObjAssociatesArrows(i);
        appendObjUsesArrows(i);

    }

    public void visit(IEnum e) {
        appendObjAssociatesArrows(e);
        appendObjUsesArrows(e);
    }
    
    private void appendObjAssociatesArrows(IObject o) {
        
        // Collect the set of all types with which the object associates.
        Set<String> associatedTypes = new HashSet<String>();
        for (IField f : o.getFields()) {
            for (String type : f.getTypeNames()) {
                if (objNamesToModel.contains(type)) {
                    associatedTypes.add(type);
                }
            }
        }
        
        for (String type : associatedTypes) {
            sb.append(UmlArrowMarkup.getAssociatesArrow(o.getName(), type));
        }
    }
    
    private void appendObjUsesArrows(IObject o) {
        
        // Collect the set of all types that the object uses.
        Set<String> usedTypes = new HashSet<String>();
        for (IMethod m : o.getMethods()) {
            for (String type : m.getReferencedTypeNames()) {
                if (objNamesToModel.contains(type)) {
                    usedTypes.add(type);
                }
            }
        }
        
        for (String type : usedTypes) {
            sb.append(UmlArrowMarkup.getReferencesArrow(o.getName(), type));
        }
    }
}
