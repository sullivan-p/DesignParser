package designParser.model.impl;

import java.util.ArrayList;
import java.util.List;

import designParser.model.api.IClass;
import designParser.model.api.IEnum;
import designParser.model.api.IInterface;
import designParser.model.api.IDesignModel;
import designParser.model.visitor.IModelVisitor;

public class DesignModel implements IDesignModel {
    private List<IClass> classModels;
    private List<IInterface> interfaceModels;
    private List<IEnum> enumModels;
	private final String DOES_NOT_EXIST_ERROR = " does not exist in the model.";
	private final String ALREADY_EXISTS_ERROR = " already exists in the model.";

	public DesignModel() {
        classModels = new ArrayList<IClass>();
        interfaceModels = new ArrayList<IInterface>();
        enumModels = new ArrayList<IEnum>();
	}

	@Override
    public boolean hasEntity(String name)  {
        return (hasInterfaceModel(name) || 
                hasEnumModel(name) ||
                hasClassModel(name));
    }
	
    @Override
    public boolean hasClassModel(String name) {
        for (IClass c : classModels) {
            if (c.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasInterfaceModel(String name) {
        for (IInterface i : interfaceModels) {
            if (i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasEnumModel(String name) {
        for (IEnum e : enumModels) {
            if (e.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public IClass getClassModel(String name) throws IllegalArgumentException {
        for (IClass c : classModels) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        throw new IllegalArgumentException(name + DOES_NOT_EXIST_ERROR);
    }

    @Override
    public IInterface getInterfaceModel(String name) throws IllegalArgumentException {
        for (IInterface i : interfaceModels) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        throw new IllegalArgumentException(name + DOES_NOT_EXIST_ERROR);
    }
    
    @Override
    public IEnum getEnumModel(String name) throws IllegalArgumentException {
        for (IEnum e : enumModels) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        throw new IllegalArgumentException(name + DOES_NOT_EXIST_ERROR);
    }
    
    @Override
    public IClass addNewClassModel(String name, boolean isConcrete) {
        if (hasClassModel(name)) {
            throw new IllegalArgumentException(name + ALREADY_EXISTS_ERROR);
        }
        
        IClass classModel = new ClassModel(name, isConcrete);
        classModels.add(classModel);
        return classModel;
    }

    @Override
    public IInterface addNewInterfaceModel(String name) {
        if (hasInterfaceModel(name)) {
            throw new IllegalArgumentException(name + ALREADY_EXISTS_ERROR);
        }
        
        IInterface interfaceModel = new InterfaceModel(name);
        interfaceModels.add(interfaceModel);
        return interfaceModel;
    }
    
    @Override
    public IEnum addNewEnumModel(String name) {
        if (hasEnumModel(name)) {
            throw new IllegalArgumentException(name + ALREADY_EXISTS_ERROR);
        }        
        
        IEnum enumModel = new EnumModel(name);
        enumModels.add(enumModel);
        return enumModel;
    }    
    
	@Override
	public void accept(IModelVisitor visitor) {
		visitor.previsit(this);
		for (IClass c : classModels) {
		    c.accept(visitor);
		}
        for (IInterface i : interfaceModels) {
            i.accept(visitor);
        }
        for (IEnum e : enumModels) {
            e.accept(visitor);
        }    
        visitor.postvisit(this);
	}
}
