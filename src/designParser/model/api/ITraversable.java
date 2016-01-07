package designParser.model.api;

public interface ITraversable {
    public void accept(IModelVisitor visitor);
}
