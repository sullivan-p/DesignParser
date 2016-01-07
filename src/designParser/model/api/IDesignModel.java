package designParser.model.api;

public interface IDesignModel extends ITraversable {
        
    /**
     * Return true if the model contains a class, interface, or enum with the
     * given name.
     * @param name Fully qualified name of the class, interface, or enum.
     */
    boolean hasObject(String name);
    
    /**
     * Return true if the model contains a model of the class with the given 
     * name, false otherwise.
     * @param name Fully qualified name of the class.
     */
    boolean hasClassModel(String name);
    
    /**
     * Return true if the model contains a model of the interface with the given 
     * name, false otherwise.
     * @param name Fully qualified name of the interface.
     */
    boolean hasInterfaceModel(String name);
    
    /**
     * Return true if the model contains a model of the enum with the given 
     * name, false otherwise.
     * @param name Fully qualified name of the enum.
     */
    boolean hasEnumModel(String name);
        
    /**
     * Return the model for the class with the given name.
     * @param name Fully qualified name of the class.
     */
    IClass getClassModel(String name) throws IllegalArgumentException;
    
    /**
     * Return the model for the interface with the given name.
     * @param name Fully qualified name of the interface.
     */
    IInterface getInterfaceModel(String name) throws IllegalArgumentException;
    
    /**
     * Return the model for the enum with the given name.
     * @param name Fully qualified name of the enum.
     */
    IEnum getEnumModel(String name) throws IllegalArgumentException;
    
    /**
     * Add a model for the class with the given name.
     * @param name Fully qualified name of the class.
     * @param isConcrete True if the class is concrete, false if it is abstract
     * @return The newly created class model
     */
    IClass addNewClassModel(String name, boolean isConcrete);
    
    /**
     * Add a model for the interface with the given name.
     * @param name Fully qualified name of the interface.
     * @return The newly created interface model
     */
    IInterface addNewInterfaceModel(String name);
    
    /**
     * Add a model for the enum with the given name.
     * @param name Fully qualified name of the enum.
     * @return The newly created enum model
     */
    IEnum addNewEnumModel(String name);
}
