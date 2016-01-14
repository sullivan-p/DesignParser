package designParser.model.api;

import java.util.Collection;

import designParser.model.impl.AccessLevel;

public interface IDesignModel extends ITraversable {
    Collection<String> getObjNamesToModel();
        
    /**
     * Add a model for the class with the given name.
     * @param name Unqualified name of the class.
     * @param isConcrete True if the class is concrete, false if it is abstract
     */
    public void putClassModel(String name, boolean isConcrete);
    
    /**
     * Add a model for the interface with the given name.
     * @param name Unqualified name of the interface.
     */
    public void putInterfaceModel(String name);
    
    /**
     * Add a model for the enum with the given name.
     * @param name Unqualified name of the enum.
     */
    public void putEnumModel(String name);

    public void putReferencesRelation(String srcName, String dstName);

    public void putAssociatesWithRelation(String srcName, String dstName);

    public void putImplementsRelation(String srcName, String dstName);

    public void putExtendsRelation(String srcName, String dstName);

    public void putMethodModel(String objName, String methodName, AccessLevel accessLevel, String methodSig);

    public void putFieldModel(String objName, String fieldName, AccessLevel accessLevel, String fieldSig);

    public void putMethodCall(String callerClassName, String callerMethodName,
            String calleeClassName, String calleeMethodName,
            String[] paramTypeNames, String returnTypeName, boolean isConstructor);
}
