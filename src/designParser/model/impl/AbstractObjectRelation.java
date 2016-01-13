package designParser.model.impl;

import designParser.model.api.IModelVisitor;
import designParser.model.api.IObjectRelation;

public abstract class AbstractObjectRelation implements IObjectRelation {

    @Override
    public void accept(IModelVisitor visitor) {}
    
    @Override
    public int compareTo(IObjectRelation rltn) {
        
        // Order relations alphabetically by the source object and then the
        // destination object names.
        String thisSrcName = this.getSource().getName();
        String thisDestName = this.getDestination().getName();
        String rltnSrcName = rltn.getSource().getName();
        String rltnDestName = rltn.getSource().getName();
        if (thisSrcName.compareTo(rltnSrcName) != 0) {
            return thisSrcName.compareTo(rltnSrcName);
        } else if (thisDestName.compareTo(rltnDestName) != 0) {
            return thisDestName.compareTo(rltnDestName);
        } 
        
        // Order relations by type.
        int thisRank = getRelationTypeRank(this);
        int rltnRank = getRelationTypeRank(rltn);
        if (thisRank > rltnRank) {
            return 1;
        } else if (thisRank < rltnRank) {
            return -1;
        }
        return 0;
    }
//    
//    @Override
//    public int hashCode() {
//        String sName = this.getSource().getName();
//        String dName = this.getDestination().getName();
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((dName == null) ? 0 : dName.hashCode());
//        result = prime * result + ((sName == null) ? 0 : sName.hashCode());
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        
//        AbstractObjectRelation other = (AbstractObjectRelation) obj;
//        String thisSrcName = this.getSource().getName();
//        String thisDestName = this.getDestination().getName();        
//        String otherSrcName = other.getSource().getName();
//        String otherDestName = other.getSource().getName();
//        if (thisDestName == null) {
//            if (otherDestName != null)
//                return false;
//        } else if (!thisDestName.equals(otherDestName))
//            return false;
//        if (thisSrcName == null) {
//            if (otherSrcName != null)
//                return false;
//        } else if (!thisSrcName.equals(otherSrcName))
//            return false;
//        return true;
//    }

    private static int getRelationTypeRank(IObjectRelation r) {
        if (r.getClass().isAssignableFrom(ExtendsRelation.class)) {
            return 3;
        } else if (r.getClass().isAssignableFrom(ImplementsRelation.class)) {
            return 3;
        } else if (r.getClass().isAssignableFrom(AssociatesWithRelation.class)) {
            return 2;
        } else if (r.getClass().isAssignableFrom(ReferencesRelation.class)) {
            return 1;
        }
        return -1;
    }
}
