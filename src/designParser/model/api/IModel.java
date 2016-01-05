package designParser.model.api;

public interface IModel extends ITraversable {
    
    /**
     * Return true if the model contains a model of the entity with the given 
     * name, false otherwise.
     * @param name Fully qualified name of the class, interface, or enum.
     */
    boolean hasEntity(String name);
    
    /**
     * Return the model for the class, interface, or enum with the given name.
     * @param name Fully qualified name of the class, interface, or enum.
     */
    IObjOrientedEntity getEntity(String name);
    
    /**
     * Add a model for the class with the given name.
     * @param name Fully qualified name of the class.
     * @return the newly created class model
     */
    IClass addNewClassModel(String name);
    
    /**
     * Add a model for the interface with the given name.
     * @param name Fully qualified name of the interface.
     * @return the newly created interface model
     */
    IInterface addNewInterfaceModel(String name);
    
    /**
     * Add a model for the enum with the given name.
     * @param name Fully qualified name of the enum.
     * @return the newly created enum model
     */
    IEnum addNewEnumModel(String name);
}
