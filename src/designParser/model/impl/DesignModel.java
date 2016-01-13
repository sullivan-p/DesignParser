package designParser.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IModelVisitor;
import designParser.model.api.IObject;
import designParser.model.api.IObjectRelation;
import pair.impl.Pair;
import designParser.model.api.IDesignModel;

public class DesignModel implements IDesignModel {
//	private final String DOES_NOT_EXIST_ERROR = " does not exist in the model.";
    private final String ALREADY_EXISTS_ERROR = " already exists in the model.";
    private final String NOT_OBJ_TO_MODEL_ERROR = " is not an object that is being modeled.";
    private final String NOT_BOTH_OBJ_TO_MODEL_ERROR = "Both object must be modeled by the design model.";
	
    private Map<String, IObject> nameToModelMap;
    private Map<Pair<String, String>, IObjectRelation> relations;

	public DesignModel(String[] names) {
	    nameToModelMap = new HashMap<String, IObject>();
	    for (String objName : names) {
	        nameToModelMap.put(AsmProcessData.qualifiedToUnqualifiedName(objName), null);
	    }
	    relations = new HashMap<Pair<String, String>, IObjectRelation>();
	}
	
	@Override
	public Collection<String> getObjNamesToModel() {
	    return nameToModelMap.keySet();
	}

	@Override
    public boolean hasObjectModel(String name)  {
        return (nameToModelMap.containsKey(name) && 
                nameToModelMap.get(name) != null);
    }
    
    @Override
    public void addNewClassModel(String name, boolean isConcrete) {
        if (hasObjectModel(name)) {
            throw new IllegalArgumentException(name + ALREADY_EXISTS_ERROR);
        } else if (!nameToModelMap.containsKey(name)) {
            throw new IllegalArgumentException(name + NOT_OBJ_TO_MODEL_ERROR);
        } 
        nameToModelMap.put(name, new ClassModel(name, isConcrete));
    }
    
    @Override
    public void addNewInterfaceModel(String name) {
        if (hasObjectModel(name)) {
            throw new IllegalArgumentException(name + ALREADY_EXISTS_ERROR);
        } else if (!nameToModelMap.containsKey(name)) {
            throw new IllegalArgumentException(name + NOT_OBJ_TO_MODEL_ERROR);
        } 
        nameToModelMap.put(name, new InterfaceModel(name));
    }
    
    @Override
    public void addNewEnumModel(String name) {
        if (hasObjectModel(name)) {
            throw new IllegalArgumentException(name + ALREADY_EXISTS_ERROR);
        } else if (!nameToModelMap.containsKey(name)) {
            throw new IllegalArgumentException(name + NOT_OBJ_TO_MODEL_ERROR);
        } 
        nameToModelMap.put(name, new EnumModel(name));
    }
    
    @Override
    public void addExtendsRelation(String srcName, String dstName) {
        addRelation(srcName, dstName, (s, d) -> {
            return new ExtendsRelation(s, d);
        });    
    }
    
    @Override
    public void addImplementsRelation(String srcName, String dstName) {
        addRelation(srcName, dstName, (s, d) -> {
            return new ImplementsRelation(s, d);
        });    
    }
    
    @Override
    public void addAssociatesWithRelation(String srcName, String dstName) {
        addRelation(srcName, dstName, (s, d) -> {
            return new AssociatesWithRelation(s, d);
        });        
    }
    
    @Override
    public void addReferencesRelation(String srcName, String dstName) {
        addRelation(srcName, dstName, (s, d) -> {
            return new ReferencesRelation(s, d);
        });    
    }
    
    /**
     * Use the given source and destination object names and the given relation
     * constructor to create a new relation. Add the relation if no relation for 
     * the source to destination exists yet or if the new relation takes
     * precedence over the existing one.
     */
    private void addRelation(String srcName, String dstName, 
            BiFunction<IObject, IObject, IObjectRelation> rltnConstructor) {
        
        if (hasObjectModel(srcName) || !hasObjectModel(dstName)) {
            throw new IllegalArgumentException(NOT_BOTH_OBJ_TO_MODEL_ERROR);
        }        
        
        Pair<String, String> objNames = new Pair<String, String>(srcName, dstName);
        IObject src = nameToModelMap.get(srcName);
        IObject dst = nameToModelMap.get(dstName);
        IObjectRelation rltn = rltnConstructor.apply(src, dst);
                
        if (!relations.keySet().contains(objNames) ||
            rltn.compareTo(relations.get(objNames)) > 0) {
            relations.put(objNames, rltn);
        }
    }
    
	@Override
	public void accept(IModelVisitor visitor) {
		visitor.previsit(this);
		for (ClassModel c : getClassModels()) {
	        c.accept(visitor);
		}
        for (InterfaceModel i : getInterfaceModels()) {
            i.accept(visitor);
        }
        for (EnumModel e : getEnumModels()) {
            e.accept(visitor);
        }    
        for (IObjectRelation r : relations.values()) {
            r.accept(visitor);
        }
        visitor.postvisit(this);
	}
	
	private Collection<ClassModel> getClassModels() {
	    Collection<ClassModel> classModels = new ArrayList<ClassModel>();
	    for (IObject o : nameToModelMap.values()) {
	        if (o != null && isClassModel(o)) {
	            classModels.add((ClassModel)o);
	        }
	    }
	    return classModels;
	}
	
	private Collection<InterfaceModel> getInterfaceModels() {
        Collection<InterfaceModel> interfaceModels = new ArrayList<InterfaceModel>();
        for (IObject o : nameToModelMap.values()) {
            if (o != null && isInterfaceModel(o)) {
                interfaceModels.add((InterfaceModel)o);
            }
        }
        return interfaceModels;	    
	}
	
	private Collection<EnumModel> getEnumModels() {
        Collection<EnumModel> enumModels = new ArrayList<EnumModel>();
        for (IObject o : nameToModelMap.values()) {
            if (o != null && isEnumModel(o)) {
                enumModels.add((EnumModel)o);
            }
        }
        return enumModels;     
	}
		
	private boolean isClassModel(IObject o) {
	    return isInstanceAssignableFrom(o, ClassModel.class);
	}
	
    private boolean isInterfaceModel(IObject o) {
        return isInstanceAssignableFrom(o, InterfaceModel.class);
    }
    
    private boolean isEnumModel(IObject o) {
        return isInstanceAssignableFrom(o, EnumModel.class);
    }
    
    private <T> boolean isInstanceAssignableFrom(Object o, Class<T> c) {
        return o.getClass().isAssignableFrom(c);
    }
}
