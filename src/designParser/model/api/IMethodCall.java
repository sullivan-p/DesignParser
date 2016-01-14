package designParser.model.api;

public interface IMethodCall extends ITraversable {
    public String getCallerClassName();
    public String getCallerMethodName();
    public String getCalleeClassName();
    public String getCalleeMethodName();
    public boolean getIsConstructor();
    public String getReturnTypeName();
    public String[] getParamTypeNames();
}
