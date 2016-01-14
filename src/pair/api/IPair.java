package pair.api;

public interface IPair<T, U> {
    public T getFirst();
    public U getSecond();
    public void setFirst(T first);
    public void setSecond(U second);
}
