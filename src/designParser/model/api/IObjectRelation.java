package designParser.model.api;

public interface IObjectRelation extends ITraversable, Comparable<IObjectRelation> {
    public IObject getSource();
    public IObject getDestination();
}
