package designParser.model.api;

import java.util.Collection;

public interface IVariable extends IModelComponent {
    public String getSignature();
    
    /**
     * If the variable is not a generic data type, return a single-element
     * container that contains only the variable's type name. If the variable is 
     * a generic type, return a collection containing the variable's type name  
     * as well as the names of any type parameters.
     */
    public Collection<String> getTypeNames();
}
