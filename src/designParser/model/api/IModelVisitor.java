package designParser.model.api;

public interface IModelVisitor {
    public void previsit(ITraversable t);
    public void visit(ITraversable t);
    public void postvisit(ITraversable t);
}