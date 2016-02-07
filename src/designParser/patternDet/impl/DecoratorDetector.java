package designParser.patternDet.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import designParser.model.api.IDependencyRelation;
import designParser.model.api.IDesignModel;
import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.model.impl.AccessLevel;
import designParser.model.impl.ClassModel;
import designParser.model.impl.DecoratesRelation;
import designParser.model.impl.DecoratorComponentModel;
import designParser.model.impl.DecoratorModel;
import designParser.model.impl.DesignModel;
import designParser.patternDet.api.PatternDetector;

public class DecoratorDetector extends PatternDetector {
    private Map<String, HashSet<String>> decoratorToComponents;
    private IDesignModel designModel;
    
    public DecoratorDetector() {
        super();
        decoratorToComponents = new HashMap<String, HashSet<String>>();
        
        addPrevisitMethod(DesignModel.class, (d) -> {
            designModel = (DesignModel)d;
        });
        
        List<Class<?>> concreteClassesToCheck = new ArrayList<Class<?>>();
        concreteClassesToCheck.add(ClassModel.class);
        for (Class<?> classToCheck : concreteClassesToCheck) {
            addVisitMethod(classToCheck, (model) -> {
                visitObjModel((IObject) model); 
            });
        }

        addPostvisitMethod(DesignModel.class, (d) -> {
            postvisitDesignModel((DesignModel) d); 
        });
    }

    private void visitObjModel(IObject objModel) {
        // A reference to a design model is used to check whether a potential
        // decorator extends or implements one of its constructor parameter 
        // types.
        if (designModel == null) return;
        
        // Build up a set of components that the object may decorate.
        HashSet<String> possibleComponents = new HashSet<String>();
        
        // The object may decorate one of its constructor parameter types.
        for (IMethod m : objModel.getAllMethodModels()) {
            if (m.isConstructor() && 
                (m.getAccessLevel() == AccessLevel.Public) || (m.getAccessLevel() == AccessLevel.Protected)) {        
                Collection<String> paramTypes = Arrays.asList(m.getParamTypeNames());
                possibleComponents.addAll(paramTypes);
            }
        }
   
        // The object may decorate a type if the object extends or implements
        // the type. (This assumes that a class cannot decorate itself. This may
        // not be correct.)
        possibleComponents.remove(objModel.getName());
        List<String> possibleComponentsList = new ArrayList<String>(possibleComponents);
        for (String possibleComp : possibleComponentsList) {
            if (!designModel.isSubType(objModel.getName(), possibleComp)) {
                possibleComponents.remove(possibleComp);
            }
        }
        
        // The object may decorate a type if it also has a field of that type.
        Set<String> fieldTypes = new HashSet<String>();
        for (IField field : objModel.getAllFieldModels()) {
            fieldTypes.add(field.getTypeName());
        }
        possibleComponents.retainAll(fieldTypes);
        
        // Record the components that this object decorates.
        if (!possibleComponents.isEmpty()) {
            decoratorToComponents.put(objModel.getName(), possibleComponents);
        }
    }    
    
    private void postvisitDesignModel(DesignModel d) {
        
        Set<String> allComponents = new HashSet<String>();
        
        // Decorate object models with decorator models.
        for (String decoratorName : decoratorToComponents.keySet()) {
            IObject model = d.getObjectModel(decoratorName);
            IObject decoratorModel = new DecoratorModel(model);
            d.replaceWithObjectModel(decoratorModel);
            
            // Decorate the relation arrow between the decorators and the 
            // components that they decorate.
            for (String componentName : decoratorToComponents.get(decoratorName)) {
                IDependencyRelation rltn = d.getDepRltn(decoratorName, componentName);
                IDependencyRelation decoratesRltn = new DecoratesRelation(rltn);
                d.replaceWithDepRltn(decoratesRltn);
            }
            
            allComponents.addAll(decoratorToComponents.get(decoratorName));
        }
        
        // Decorate object models with decorator component models.
        for (String componentName : allComponents) {
            IObject model = d.getObjectModel(componentName);
            IObject decoratorComponentModel = new DecoratorComponentModel(model);
            d.replaceWithObjectModel(decoratorComponentModel);            
        }
        
        // Pass another visitor over the design model to detect decorators that
        // are subclasses of already detected decorators.
        d.accept(new SubclassDecoratorDetector(decoratorToComponents));
    }
}

