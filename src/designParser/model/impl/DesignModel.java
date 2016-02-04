package designParser.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IObject;
import designParser.visitor.api.IModelVisitor;
import pair.impl.Pair;
import designParser.model.api.IDependencyRelation;
import designParser.model.api.IDesignModel;
import designParser.model.api.IMethod;

public class DesignModel implements IDesignModel {	
    private Map<String, IObject> nameToModelMap;
    private Map<Pair<String, String>, AbstractHierarchyRelation> hierarchyRelations;
    private Map<Pair<String, String>, IDependencyRelation> dependencyRelations;

	public DesignModel(String[] names) {
	    nameToModelMap = new HashMap<String, IObject>();
	    for (String objName : names) {
	        String unqualifiedObjName = AsmProcessData.qualifiedToUnqualifiedName(objName);
	        nameToModelMap.put(unqualifiedObjName, null);
	    }
	    hierarchyRelations = new HashMap<Pair<String, String>, AbstractHierarchyRelation>();
        dependencyRelations = new HashMap<Pair<String, String>, IDependencyRelation>();
	}
		
	@Override
	public void accept(IModelVisitor visitor) {    
		visitor.previsit(this);
		for (IObject o : nameToModelMap.values()) {
		    if (o != null) {
		        o.accept(visitor);
		    }
		}
        for (AbstractHierarchyRelation r : hierarchyRelations.values()) {
            r.accept(visitor);
        }        
        for (IDependencyRelation r : dependencyRelations.values()) {
            r.accept(visitor);
        }
        visitor.postvisit(this);
	}
	
	
    //--------------------------------------------------------------------------
    // Object models
    //--------------------------------------------------------------------------
	
    @Override
    public void putClassModel(String name, boolean isConcrete) {
        putObjectModel(name, ClassModel.class, () -> { 
            return new ClassModel(name, isConcrete);
        });    
        
        // Update the class's isConcrete value, because if the class already 
        // existed, then its isConcrete value won't have been updated in the 
        // previous method call.
        ((ClassModel) nameToModelMap.get(name)).setIsConcrete(isConcrete);
    }
    
    @Override
    public void putInterfaceModel(String name) {
        putObjectModel(name, InterfaceModel.class, () -> { 
            return new InterfaceModel(name);
        });    
    }
    
    @Override
    public void putEnumModel(String name) {
        putObjectModel(name, EnumModel.class, () -> { 
            return new EnumModel(name);
        });    
    }
    
    private void putObjectModel(String name, Class<?> objType,
            Supplier<IObject> modelConstructor) {
        
        // The object should not be modeled, so do nothing.
        if (!nameToModelMap.containsKey(name)) {
            return;  
        }

        IObject existingModel = nameToModelMap.get(name);
        if (existingModel == null) {
            // Instantiate the model if no model has been created for this name.
            nameToModelMap.put(name, modelConstructor.get());
        } else if (!existingModel.getClass().isAssignableFrom(objType)) {
            // The existing object model does not have the desired type. Copy
            // the values from the existing model into a new model of the
            // correct type.
            IObject replacementModel = modelConstructor.get();
            replacementModel.setFieldModels(existingModel.getAllFieldModels());
            replacementModel.setMethodModels(existingModel.getAllMethodModels());
            nameToModelMap.put(name, replacementModel);
        }
    }
    
    
    //--------------------------------------------------------------------------
    // Field and method models
    //--------------------------------------------------------------------------
    
    @Override
    public void putFieldModel(String objName, String fieldName, 
            AccessLevel accessLevel, String fieldSig) {
        
        StringBuilder error = new StringBuilder();
        error.append("The object model to which this field belongs must ");
        error.append("be created before methods can be assigned to it.");

        getObjModelThenDo(objName, error.toString(), (objModel) -> {
            objModel.putFieldModel(fieldName, accessLevel, fieldSig);
        });
    }
    
    @Override
    public void putMethodModel(String objName, String methodName, 
            AccessLevel accessLevel, String retTypeName, String[] paramTypeNames, boolean isStatic) {
        
        StringBuilder error = new StringBuilder();
        error.append("The object model to which this method belongs must ");
        error.append("be created before methods can be assigned to it.");
        
        if (accessLevel == null) {
            putMethodModel(objName, methodName, retTypeName, paramTypeNames, isStatic);
        } else {
            getObjModelThenDo(objName, error.toString(), (objModel) -> {
                objModel.putMethodModel(methodName, accessLevel, retTypeName, paramTypeNames, isStatic);
            });
        }
    }    
    
    /**
     * Add a model for a method with incomplete information about the method's 
     * access level.
     */
    private void putMethodModel(String objName, String methodName, 
            String retTypeName, String[] paramTypeNames, boolean isStatic) {
        
        StringBuilder error = new StringBuilder();
        error.append("The object model to which this method belongs must ");
        error.append("be created before methods can be assigned to it.");

        getObjModelThenDo(objName, error.toString(), (objModel) -> {
            objModel.putMethodModel( methodName, retTypeName, paramTypeNames, isStatic);
        });
    }    
   
    /**
     * Get the object model by its name, and then use the object model to
     * execute the given function.
     * @param objName Unqualified name of the object.
     * @param errorMsg Error message to include in the exception if the object 
     *        cannot be found.
     * @param f Function to perform using the retrieved object model.
     */
    private void getObjModelThenDo(String objName, String errorMsg, Consumer<IObject> f) {
        
        // The object to which the method belongs should not be modeled, so do 
        // nothing.
        if (!nameToModelMap.containsKey(objName)) {
            return;  
        }
     
        if (nameToModelMap.get(objName) == null) {
            throw new IllegalArgumentException(errorMsg.toString());
        }
        
        IObject objModel = nameToModelMap.get(objName);
        f.accept(objModel);
    }
    
    
    //--------------------------------------------------------------------------
    // Method call models
    //--------------------------------------------------------------------------
   
    @Override
    public void putMethodCall(
            String callerClassName, String callerMethodName, String[] callerParamTypeNames,
            String calleeClassName, String calleeMethodName, String[] calleeParamTypeNames, 
            String calleeReturnTypeName, boolean isConstructor, boolean isStatic) {
                
        // The object to which the caller method belongs should not be modeled,
        // so do nothing.
        if (!nameToModelMap.containsKey(callerClassName)) {
            return;  
        }
        
        // The caller object should already exists.
        IObject callerObjModel = nameToModelMap.get(callerClassName);
        if (callerObjModel == null) {
            StringBuilder error = new StringBuilder();
            error.append("The object model to which the caller method belongs ");
            error.append("must be created before methods can be assigned to it.");
            throw new IllegalArgumentException(error.toString());
        }
        
        // Get a model for the method that is called.
        IMethod calleeMethodModel;
        if (nameToModelMap.containsKey(calleeClassName)) {
            IObject calleeObjModel = nameToModelMap.get(calleeClassName);
            if (calleeObjModel == null) {
                // Assume that the callee object is a concrete class. If this is
                // not the case, then it can be modified later when a particular
                // object type is specified.
                putClassModel(calleeClassName, true);
                calleeObjModel = nameToModelMap.get(calleeClassName);
            }
            calleeObjModel.putMethodModel(calleeMethodName, calleeReturnTypeName, calleeParamTypeNames, isStatic);
            calleeMethodModel = calleeObjModel.getMethodModel(calleeMethodName, calleeParamTypeNames);
        } else {
            // The model for the called method does not belong to an object 
            // model, since the object is not being modeled.
            calleeMethodModel = new MethodModel(calleeClassName, calleeMethodName, null,
                    calleeReturnTypeName, calleeParamTypeNames, isStatic);
        }

        // Add the method model to the caller method's list of method calls.
        callerObjModel.putMethodCall(callerMethodName, callerParamTypeNames, calleeMethodModel);
    }

    
    //--------------------------------------------------------------------------
    // Relation models
    //--------------------------------------------------------------------------
    
    @Override
    public void putExtendsRelation(String srcName, String dstName) {
        putHierarchyRelation(srcName, dstName, () -> {
            return new ExtendsRelation(srcName, dstName);
        });    
    }
    
    @Override
    public void putImplementsRelation(String srcName, String dstName) {
        putHierarchyRelation(srcName, dstName, () -> {
            return new ImplementsRelation(srcName, dstName);
        });    
    }
    
    @Override
    public void putAssociatesWithRelation(String srcName, String dstName) {
        putDependencyRelation(srcName, dstName, () -> {
            return new AssociatesWithRelation(srcName, dstName);
        });        
    }
    
    @Override
    public void putReferencesRelation(String srcName, String dstName) {
        putDependencyRelation(srcName, dstName, () -> {
            return new ReferencesRelation(srcName, dstName);
        });    
    }
    
    /**
     * Use the given source and destination object names and the given 
     * hierarchy relation constructor to create a new hierarchy relation. 
     * Map the source-destination name pair to the new relation.
     */
    private void putHierarchyRelation(String srcName, String dstName, 
            Supplier<AbstractHierarchyRelation> rltnConstructor) {
        
        // One or both objects should be modeled, so do nothing.
        if (!nameToModelMap.containsKey(srcName) || !nameToModelMap.containsKey(dstName)) {
            return;  
        }
        
        Pair<String, String> objNames = new Pair<String, String>(srcName, dstName);
        AbstractHierarchyRelation rltn = rltnConstructor.get();
        hierarchyRelations.put(objNames, rltn);
    }
    
    /**
     * Use the given source and destination object names and the given 
     * dependency relation constructor to create a new dependency relation. 
     * Add the relation if no relation for the source to destination exists yet 
     * or if the new relation takes precedence over the existing one.
     */
    private void putDependencyRelation(String srcName, String dstName, 
            Supplier<AbstractDependencyRelation> rltnConstructor) {
        
        // One or both objects should be modeled, so do nothing.
        if (!nameToModelMap.containsKey(srcName) || !nameToModelMap.containsKey(dstName)) {
            return;  
        }     
        
        Pair<String, String> objNames = new Pair<String, String>(srcName, dstName);
        AbstractDependencyRelation rltn = rltnConstructor.get();
        
        if (!dependencyRelations.keySet().contains(objNames) ||
            rltn.compareTo(dependencyRelations.get(objNames)) > 0) {
            dependencyRelations.put(objNames, rltn);
        }
    }

    
    //--------------------------------------------------------------------------
    // Methods for allowing pattern detectors to get and replace objects models
    //--------------------------------------------------------------------------
    
    @Override
    public IObject getObjectModel(String name) {
        return nameToModelMap.get(name);
    }
 
    @Override
    public IDependencyRelation getDepRltn(String srcName, String dstName) {
        return dependencyRelations.get(new Pair<String, String>(srcName, dstName));
    }   
    
    @Override
    public Collection<IDependencyRelation> getDepRltnsForSrc(String srcName) {
        Collection<IDependencyRelation> rltns = new ArrayList<IDependencyRelation>();
        for (Pair<String, String> namePair : dependencyRelations.keySet()) {
            if (namePair.getFirst().equals(srcName)) {
                rltns.add(dependencyRelations.get(namePair));
            }
        }
        return rltns;
    }
    
    @Override
    public void replaceWithObjectModel(IObject model) {
        String name = model.getName();
        if (nameToModelMap.containsKey(name)) {
            nameToModelMap.put(name, model);
        }
    }

    @Override
    public void replaceWithDepRltn(IDependencyRelation r) {
        Pair<String, String> names = new Pair<String, String>(r.getSourceName(), r.getDestinationName());
        if (dependencyRelations.containsKey(names)) {
            dependencyRelations.put(names, r);
        }
    }
}
