package designParser.visitor.api;

public interface IModelVisitor extends IVisitor {

    // Methods for traversing through method calls.
    public String getTargetMethodName();
    public String getTargetMethodClassName();
    public String[] getTargetMethodParamTypeNames();
    public int getCallDepth();
    public int getMaxCallDepth();
    public void incrementCallDepth();
    public void decrementCallDepth();
}