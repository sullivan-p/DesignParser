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

    void addReferencesRelation(String srcName, String dstName);

    void addAssociatesWithRelation(String srcName, String dstName);

    void addImplementsRelation(String srcName, String dstName);

    void addExtendsRelation(String srcName, String dstName);
}
