package designParser.model.api;

public interface IObjectRelation extends ITraversable {
    public IObject getSource();
    public IObject getDestination();
}
