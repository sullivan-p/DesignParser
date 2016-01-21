package designParser.model.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IModelVisitor;
import designParser.model.api.IObject;
import pair.impl.Pair;
import designParser.model.api.IDesignModel;

public class DesignModel implements IDesignModel {	
    private Map<String, IObject> nameToModelMap;
    private Map<Pair<String, String>, AbstractHierarchyRelation> hierarchyRelations;
    private Map<Pair<String, String>, AbstractDependencyRelation> dependencyRelations;

	public DesignModel(String[] names) {
	    nameToModelMap = new HashMap<String, IObject>();
	    for (String objName : names) {
	        String unqualifiedObjName = AsmProcessData.qualifiedToUnqualifiedName(objName);
	        nameToModelMap.put(unqualifiedObjName, null);
	    }
	    hierarchyRelations = new HashMap<Pair<String, String>, AbstractHierarchyRelation>();
        dependencyRelations = new HashMap<Pair<String, String>, AbstractDependencyRelation>();
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
        for (AbstractDependencyRelation r : dependencyRelations.values()) {
            r.accept(visitor);
        }
        visitor.postvisit(this);
	}
	
	@Override
	public Collection<String> getObjNamesToModel() {
	    return nameToModelMap.keySet();
	}
    
    @Override
    public void putClassModel(String name, boolean isConcrete) {
        putObjectModel(name, () -> { 
            return new ClassModel(name, isConcrete);
        });    
    }
    
    @Override
    public void putInterfaceModel(String name) {
        putObjectModel(name, () -> { 
            return new InterfaceModel(name);
        });    
    }
    
    @Override
    public void putEnumModel(String name) {
        putObjectModel(name, () -> { 
            return new EnumModel(name);
        });    
    }
    
    private void putObjectModel(String name, 
            Supplier<IObject> modelConstructor) {
        
        // The object should not be modeled, so do nothing.
        if (!nameToModelMap.containsKey(name)) {
            return;  
        }
        
        // Instantiate the model if no model has been created for this name.
        if (nameToModelMap.get(name) == null) {
            nameToModelMap.put(name, modelConstructor.get());
        }        
    }
    
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
    
    @Override
    public void putMethodModel(String objName, String methodName, 
            AccessLevel accessLevel, String retTypeName, String[] paramTypeNames) {
        
        // The object to which the method belongs should not be modeled, so do 
        // nothing.
        if (!nameToModelMap.containsKey(objName)) {
            return;  
        }
     
        if (nameToModelMap.get(objName) == null) {
            StringBuilder error = new StringBuilder();
            error.append("The object model to which this method belongs must ");
            error.append("be created before methods can be assigned to it.");
            throw new IllegalArgumentException(error.toString());
        }
        
        IObject objModel = nameToModelMap.get(objName);
        objModel.putMethodModel(objName, methodName, accessLevel, retTypeName, paramTypeNames);
    }
    
    @Override
    public void putFieldModel(String objName, String fieldName, 
            AccessLevel accessLevel, String fieldSig) {
        
        // The object to which the field belongs should not be modeled, so do 
        // nothing.
        if (!nameToModelMap.containsKey(objName)) {
            return;  
        }
     
        if (nameToModelMap.get(objName) == null) {
            StringBuilder error = new StringBuilder();
            error.append("The object model to which this field belongs must ");
            error.append("be created before fields can be assigned to it.");
            throw new IllegalArgumentException(error.toString());
        }
        
        IObject objModel = nameToModelMap.get(objName);
        objModel.putFieldModel(fieldName, accessLevel, fieldSig);
    }
    
    @Override
    public void putMethodCall(
            String callerClassName, String callerMethodName, String[] callerParamTypeNames,
            String calleeClassName, String calleeMethodName,
            String[] calleeParamTypeNames, String calleeReturnTypeName, boolean isConstructor) {
        
        // The object to which the caller method belongs should not be modeled,
        // so do nothing.
        if (!nameToModelMap.containsKey(callerClassName)) {
            return;  
        }
        
        if (nameToModelMap.get(callerClassName) == null) {
            StringBuilder error = new StringBuilder();
            error.append("The class model to which the caller method belongs ");
            error.append("must be created before methods can be assigned to it.");
            throw new IllegalArgumentException(error.toString());
        }
        
        IObject objModel = nameToModelMap.get(callerClassName);
        if (!objModel.getClass().isAssignableFrom(ClassModel.class)) {
            StringBuilder error = new StringBuilder();
            error.append("The object model to which the caller method must ");
            error.append("be a class model.");
            throw new IllegalArgumentException(error.toString());
        }
        
        ClassModel classModel = (ClassModel) nameToModelMap.get(callerClassName);
        classModel.putMethodCall(callerClassName, callerMethodName, callerParamTypeNames,
            calleeClassName, calleeMethodName, calleeParamTypeNames, calleeReturnTypeName, 
            isConstructor);
    }
}
