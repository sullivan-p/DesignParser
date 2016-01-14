package designParser.model.api;

import java.util.Collection;

import designParser.model.impl.AccessLevel;

public interface IDesignModel extends ITraversable {
    Collection<String> getObjNamesToModel();

//    /**
//     * Return true if the model contains a class, interface, or enum with the
//     * given name.
//     * @param name Unqualified name of the class, interface, or enum.
//     */
//    boolean hasObjectModel(String name);
        
    /**
     * Add a model for the class with the given name.
     * @param name Unqualified name of the class.
     * @param isConcrete True if the class is concrete, false if it is abstract
     */
    void putClassModel(String name, boolean isConcrete);
    
    /**
     * Add a model for the interface with the given name.
     * @param name Unqualified name of the interface.
     */
    void putInterfaceModel(String name);
    
    /**
     * Add a model for the enum with the given name.
     * @param name Unqualified name of the enum.
     */
    void putEnumModel(String name);

    void putReferencesRelation(String srcName, String dstName);

    void putAssociatesWithRelation(String srcName, String dstName);

    void putImplementsRelation(String srcName, String dstName);

    void putExtendsRelation(String srcName, String dstName);

    void putMethodModel(String objName, String methodName, AccessLevel accessLevel, String methodSig);

    void putFieldModel(String objName, String fieldName, AccessLevel accessLevel, String fieldSig);
}
