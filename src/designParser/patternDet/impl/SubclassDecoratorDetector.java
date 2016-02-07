package designParser.patternDet.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import designParser.model.api.IDesignModel;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.model.impl.AccessLevel;
import designParser.model.impl.ClassModel;
import designParser.model.impl.DecoratorModel;
import designParser.model.impl.DesignModel;
import designParser.patternDet.api.PatternDetector;

public class SubclassDecoratorDetector extends PatternDetector {
    private IDesignModel designModel;
    private Map<String, HashSet<String>> initDecoratorToComponents;
    private Set<String> subclassDecorators;

    public SubclassDecoratorDetector(Map<String, HashSet<String>> initDecoratorToComponents) {
        super();        
        this.initDecoratorToComponents = initDecoratorToComponents;
        this.subclassDecorators = new HashSet<String>();

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
        String objName = objModel.getName();

        Set<String> constrParamTypes = new HashSet<String>();
        for (IMethod m : objModel.getAllMethodModels()) {
            if (m.isConstructor() && 
                    (m.getAccessLevel() == AccessLevel.Public) || (m.getAccessLevel() == AccessLevel.Protected)) {        
                Collection<String> paramTypes = Arrays.asList(m.getParamTypeNames());
                constrParamTypes.addAll(paramTypes);
            }
        }
        
        for (String decorator : initDecoratorToComponents.keySet()) {
            
            // Is this object a subclass of the decorator?
            if (!objName.equals(decorator) && designModel.isSubType(objName, decorator)) {
                Set<String> components = initDecoratorToComponents.get(decorator);
                components.retainAll(constrParamTypes);
                
                // Does this subclass have a constructor that takes as a
                // parameter one of the decorated component types?
                if (!components.isEmpty()) {
                    subclassDecorators.add(objName);
                    break;
                }
            }
        }
    }    
    
    private void postvisitDesignModel(DesignModel d) {
        
        // Decorate each singleton object.
        for (String name : subclassDecorators) {
            IObject model = d.getObjectModel(name);
            IObject decoratorModel = new DecoratorModel(model);
            d.replaceWithObjectModel(decoratorModel);
        }
    }
}
