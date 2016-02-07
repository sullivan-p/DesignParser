package designParser.model.api;

import designParser.model.impl.AccessLevel;

public interface IDesignModel extends ITraversable {
        
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

    public boolean isSubType(String subName, String superName);

    public void putMethodModel(String objName, String methodName, AccessLevel accessLevel, String retTypeName,
            String[] paramTypeNames, boolean isStatic);
    
    public void putFieldModel(String objName, String fieldName, AccessLevel accessLevel, String fieldSig);

    public void putMethodCall(String callerClassName, String callerMethodName, String[] callerParamTypeNames,
            String calleeClassName, String calleeMethodName, String[] calleeParamTypeNames, String calleeReturnTypeName,
            boolean isConstructor, boolean isStatic);
    
    public IObject getObjectModel(String name);
    
    public IDependencyRelation getDepRltn(String srcName, String dstName);
    
//    public Collection<IDependencyRelation> getDepRltnsForSrc(String srcName);
    
    /**
     * Add the given object model to the design model if another object model 
     * with the same name already exists. Replaces the old object model with the
     * same name.
     */
    public void replaceWithObjectModel(IObject model);
    
    /**
     * Add the given dependency relation to the design model if another 
     * dependency relation with the same source and destination names already
     * exists. Replaces the old dependency relation with the same name.
     */
    public void replaceWithDepRltn(IDependencyRelation r);
}
