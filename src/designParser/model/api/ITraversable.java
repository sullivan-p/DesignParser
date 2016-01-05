package designParser.model.api;

import designParser.model.visitor.IModelVisitor;

public interface ITraversable {
    public void accept(IModelVisitor visitor);
}
