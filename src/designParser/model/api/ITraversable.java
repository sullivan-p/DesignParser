package designParser.model.api;

import designParser.visitor.api.IModelVisitor;

public interface ITraversable {
    public void accept(IModelVisitor visitor);
}
