package designParser.model.api;

import java.util.Collection;

public interface IVariable extends IModelComponent {
    
    /**
     * If the variable is not a generic data type, return a single-element
     * container that contains only the variable's data type. If the variable is 
     * a generic type, return a collection containing the variable's type in 
     * addition to any type parameters.
     */
    public Collection<IDataType> getTypes();
}
