package designParser.model.impl;

import java.util.ArrayList;
import java.util.List;

import designParser.model.api.IClass;
import designParser.model.api.IEnum;
import designParser.model.api.IInterface;
import designParser.model.api.IModel;
import designParser.model.api.IObjOrientedEntity;
import designParser.model.visitor.IModelVisitor;

public class Model implements IModel {
	private List<IObjOrientedEntity> entities;
	private final String DOES_NOT_EXIST_ERROR = " does not exist in the model.";
	private final String ALREADY_EXISTS_ERROR = " already exists in the model.";

	public Model() {
		entities = new ArrayList<IObjOrientedEntity>();
	}

    @Override
    public boolean hasEntity(String name) {
        for (IObjOrientedEntity e : entities) {
            if (e.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IObjOrientedEntity getEntity(String name) throws IllegalArgumentException {
        for (IObjOrientedEntity e : entities) {
            if (e.getName().equals(name)) {
                return e;
            }
        }         
        throw new IllegalArgumentException(name + DOES_NOT_EXIST_ERROR);
    }

    @Override
    public IClass addNewClassModel(String name) {
        if (hasEntity(name)) {
            throw new IllegalArgumentException(name + ALREADY_EXISTS_ERROR);
        }
        
        IClass classModel = new ClassModel(name);
        entities.add(classModel);
        return classModel;
    }

    @Override
    public IInterface addNewInterfaceModel(String name) {
        if (hasEntity(name)) {
            throw new IllegalArgumentException(name + ALREADY_EXISTS_ERROR);
        }
        
        IInterface interfaceModel = new InterfaceModel(name);
        entities.add(interfaceModel);
        return interfaceModel;
    }
    
    @Override
    public IEnum addNewEnumModel(String name) {
        if (hasEntity(name)) {
            throw new IllegalArgumentException(name + ALREADY_EXISTS_ERROR);
        }        
        
        IEnum enumModel = new EnumModel(name);
        entities.add(enumModel);
        return enumModel;
    }    
    
	@Override
	public void accept(IModelVisitor visitor) {
		// TODO Auto-generated method stub
	}
}
