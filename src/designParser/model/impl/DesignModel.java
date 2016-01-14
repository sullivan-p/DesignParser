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
//	private final String DOES_NOT_EXIST_ERROR = " does not exist in the model.";
//    private final String ALREADY_EXISTS_ERROR = " already exists in the model.";
//    private final String NOT_OBJ_TO_MODEL_ERROR = " is not an object that is being modeled.";
//    private final String NOT_BOTH_OBJ_TO_MODEL_ERROR = "Both object must be modeled by the design model.";
	
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
	public void accept(IModelVisitor visitor) {
		visitor.previsit(this);
		for (IObject o : nameToModelMap.values()) {
		    if (o != null) {
		        o.accept(visitor);
		    }
		}
//		for (ClassModel c : getClassModels()) {
//	        c.accept(visitor);
//		}
//        for (InterfaceModel i : getInterfaceModels()) {
//            i.accept(visitor);
//        }
//        for (EnumModel e : getEnumModels()) {
//            e.accept(visitor);
//        }    
        for (AbstractHierarchyRelation r : hierarchyRelations.values()) {
            r.accept(visitor);
        }        
        for (AbstractDependencyRelation r : dependencyRelations.values()) {
            r.accept(visitor);
        }

        visitor.postvisit(this);
	}

    @Override
    public void putMethodModel(String objName, String methodName, 
            AccessLevel accessLevel, String methodSig) {
        
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
        objModel.putMethodModel(methodName, accessLevel, methodSig);
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
//	private Collection<ClassModel> getClassModels() {
//	    Collection<ClassModel> classModels = new ArrayList<ClassModel>();
//	    for (IObject o : nameToModelMap.values()) {
//	        if (o != null && isClassModel(o)) {
//	            classModels.add((ClassModel)o);
//	        }
//	    }
//	    return classModels;
//	}
//	
//	private Collection<InterfaceModel> getInterfaceModels() {
//        Collection<InterfaceModel> interfaceModels = new ArrayList<InterfaceModel>();
//        for (IObject o : nameToModelMap.values()) {
//            if (o != null && isInterfaceModel(o)) {
//                interfaceModels.add((InterfaceModel)o);
//            }
//        }
//        return interfaceModels;	    
//	}
//	
//	private Collection<EnumModel> getEnumModels() {
//        Collection<EnumModel> enumModels = new ArrayList<EnumModel>();
//        for (IObject o : nameToModelMap.values()) {
//            if (o != null && isEnumModel(o)) {
//                enumModels.add((EnumModel)o);
//            }
//        }
//        return enumModels;     
//	}
		
//	private boolean isClassModel(IObject o) {
//	    return isInstanceAssignableFrom(o, ClassModel.class);
//	}
//	
//    private boolean isInterfaceModel(IObject o) {
//        return isInstanceAssignableFrom(o, InterfaceModel.class);
//    }
//    
//    private boolean isEnumModel(IObject o) {
//        return isInstanceAssignableFrom(o, EnumModel.class);
//    }
//    
//    private <T> boolean isInstanceAssignableFrom(Object o, Class<T> c) {
//        return o.getClass().isAssignableFrom(c);
//    }
}
