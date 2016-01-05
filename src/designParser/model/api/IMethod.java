package designParser.model.api;

import java.util.List;

public interface IMethod extends IModelComponent {
    public AccessLevel getAccessLevel();
    public List<IVariable> getParams();
}