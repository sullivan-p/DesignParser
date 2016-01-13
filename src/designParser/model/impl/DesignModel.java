package designParser.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IModelVisitor;
import designParser.model.api.IObject;
import designParser.model.api.IDesignModel;

public class DesignModel implements IDesignModel {
	private final String DOES_NOT_EXIST_ERROR = " does not exist in the model.";
    private final String ALREADY_EXISTS_ERROR = " already exists in the model.";
    private final String NOT_OBJ_TO_MODEL_ERROR = " is not an object that is being modeled.";
	
    private Map<String, IObject> nameToModelMap;

	public DesignModel(String[] names) {
	    this.nameToModelMap = new HashMap<String, IObject>();
	    for (String objName : names) {
	        nameToModelMap.put(AsmProcessData.qualifiedToUnqualifiedName(objName), null);
	    };
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
	
//    @Override
//    public boolean hasClassModel(String name) {
//        return hasObject(name) && isClassModel(nameToModelMap.get(name));
//    }
//    
//    @Override
//    public boolean hasInterfaceModel(String name) {
//        return hasObject(name) && isInterfaceModel(nameToModelMap.get(name));
//    }
//    
//    @Override
//    public boolean hasEnumModel(String name) {
//        return hasObject(name) && isEnumModel(nameToModelMap.get(name));
//    }
    
//    @Override
//    public InterfaceModel getClassModel(String name) throws IllegalArgumentException {
//        if (!hasInterfaceModel(name)) {
//            throw new IllegalArgumentException(name + DOES_NOT_EXIST_ERROR);
//        }
//        return (InterfaceModel)nameToModelMap.get(name);
//    }
//    
//    @Override
//    public InterfaceModel getInterfaceModel(String name) throws IllegalArgumentException {
//        if (!hasInterfaceModel(name)) {
//            throw new IllegalArgumentException(name + DOES_NOT_EXIST_ERROR);
//        }
//        return (InterfaceModel)nameToModelMap.get(name);
//    }
//    
//    @Override
//    public EnumModel getEnumModel(String name) throws IllegalArgumentException {
//        if (!hasEnumModel(name)) {
//            throw new IllegalArgumentException(name + DOES_NOT_EXIST_ERROR);
//        }
//        return (EnumModel)nameToModelMap.get(name);
//    }
    
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
