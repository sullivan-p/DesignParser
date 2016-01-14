package designParser.model.impl;

import designParser.model.api.IObject;

public class AbstractDependencyRelation extends AbstractObjectRelation 
    implements Comparable<AbstractDependencyRelation> {

    public AbstractDependencyRelation(IObject srcModel, IObject dstModel) {
        super(srcModel, dstModel);
    }
    
    @Override
    public int compareTo(AbstractDependencyRelation rltn) {
        
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
    
    private static int getRelationTypeRank(AbstractDependencyRelation r) {
        if (r.getClass().isAssignableFrom(AssociatesWithRelation.class)) {
            return 2;
        } else if (r.getClass().isAssignableFrom(ReferencesRelation.class)) {
            return 1;
        }
        return -1;
    }
}
