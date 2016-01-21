package designParser.model.api;

import designParser.markupGen.api.IModelVisitor;

public interface ITraversable {
    public void accept(IModelVisitor visitor);
}
