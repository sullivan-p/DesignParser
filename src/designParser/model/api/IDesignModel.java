package designParser.model.api;

import java.util.Collection;

public interface IDesignModel extends ITraversable {
    Collection<String> getObjNamesToModel();

    /**
     * Return true if the model contains a class, interface, or enum with the
     * given name.
     * @param name Unqualified name of the class, interface, or enum.
     */
    boolean hasObjectModel(String name);
    
//    /**
//     * Return true if the model contains a model of the class with the given 
//     * name, false otherwise.
//     * @param name Fully qualified name of the class.
//     */
//    boolean hasClassModel(String name);
//    
//    /**
//     * Return true if the model contains a model of the interface with the given 
//     * name, false otherwise.
//     * @param name Fully qualified name of the interface.
//     */
//    boolean hasInterfaceModel(String name);
//    
//    /**
//     * Return true if the model contains a model of the enum with the given 
//     * name, false otherwise.
//     * @param name Fully qualified name of the enum.
//     */
//    boolean hasEnumModel(String name);
        
//    /**
//     * Return the model for the class with the given name.
//     * @param name Fully qualified name of the class.
//     */
//    ClassModel getClassModel(String name) throws IllegalArgumentException;
//    
//    /**
//     * Return the model for the interface with the given name.
//     * @param name Fully qualified name of the interface.
//     */
//    InterfaceModel getInterfaceModel(String name) throws IllegalArgumentException;
//    
//    /**
//     * Return the model for the enum with the given name.
//     * @param name Fully qualified name of the enum.
//     */
//    EnumModel getEnumModel(String name) throws IllegalArgumentException;
    
    /**
     * Add a model for the class with the given name.
     * @param name Unqualified name of the class.
     * @param isConcrete True if the class is concrete, false if it is abstract
     */
    void addNewClassModel(String name, boolean isConcrete);
    
    /**
     * Add a model for the interface with the given name.
     * @param name Unqualified name of the interface.
     */
    void addNewInterfaceModel(String name);
    
    /**
     * Add a model for the enum with the given name.
     * @param name Unqualified name of the enum.
     */
    void addNewEnumModel(String name);
}
