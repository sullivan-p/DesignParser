package designParser.model.impl;

public abstract class AbstractDependencyRelation extends AbstractObjectRelation 
    implements Comparable<AbstractDependencyRelation> {

    public AbstractDependencyRelation(String srcNamel, String dstName) {
        super(srcNamel, dstName);
    }
    
    @Override
    public int compareTo(AbstractDependencyRelation rltn) {
        
        // Order relations alphabetically by the source object and then the
        // destination object names.
        String thisSrcName = this.getSourceName();
        String thisDestName = this.getDestinationName();
        String rltnSrcName = rltn.getSourceName();
        String rltnDestName = rltn.getDestinationName();
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
