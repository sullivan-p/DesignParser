package designParser.markupGen.api;

import designParser.model.api.ITraversable;

public interface IModelVisitor {
    public void previsit(ITraversable t);
    public void visit(ITraversable t);
    public void postvisit(ITraversable t);

    // Methods for traversing through method calls.
    public String getTargetMethodName();
    public String getTargetMethodClassName();
    public String[] getTargetMethodParamTypeNames();
    public int getCallDepth();
    public int getMaxCallDepth();
    public void incrementCallDepth();
    public void decrementCallDepth();
}