package designParser.markupGen.api;

import designParser.model.api.ITraversable;

public interface IModelVisitor {
    public void previsit(ITraversable t);
    public void visit(ITraversable t);
    public void postvisit(ITraversable t);
}