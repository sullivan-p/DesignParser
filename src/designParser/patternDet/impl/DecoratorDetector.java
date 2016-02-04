package designParser.patternDet.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.model.impl.AccessLevel;
import designParser.model.impl.ClassModel;
import designParser.model.impl.DesignModel;
import designParser.model.impl.SingletonModel;
import designParser.patternDet.api.PatternDetector;
import pair.impl.Pair;

public class DecoratorDetector extends PatternDetector {
    Collection<Pair<String, String>> possibleDecorCompPairs;
    
    public DecoratorDetector() {
        super();
        possibleDecorCompPairs = new ArrayList<Pair<String, String>>();
        
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
        
      for (IMethod m : objModel.getAllMethodModels()) {
//          if (m.isConstructor() && m.getAccessLevel() == AccessLevel.Public) {        
//              m.getParamTypeNames()
//          }
      }
        
//        objModel.getAllMethodModels()
//        // Determine whether the currently visited class is a singleton.
//        boolean hasGetInstMthd = false;
//        boolean hiddenContructors = false;
//        for (IMethod m : objModel.getAllMethodModels()) {
//            if (m.getReturnTypeName().equals(objModel.getName()) &&
//                m.isStatic() && m.getAccessLevel() == AccessLevel.Public) {
//                hasGetInstMthd = true;
//            }
//            if (m.isConstructor()) {        
//                if (m.getAccessLevel() == AccessLevel.Private || m.getAccessLevel() == AccessLevel.Protected) {
//                    hiddenContructors = true;
//                } else {
//                    // Having even one public constructor breaks the singleton pattern.
//                    hiddenContructors = false;
//                    break;
//                }
//            }
//        }
//        boolean isSingletonClass = hasGetInstMthd && hiddenContructors && objModel.getIsConcrete();
//
//        if (isSingletonClass) {
//            singletonNames.add(objModel.getName());
//        }
    }    
    
    private void postvisitDesignModel(DesignModel d) {
        
//        // Decorate each singleton object.
//        for (String name : singletonNames) {
//            IObject model = d.getObjectModel(name);
//            IObject singletonModel = new SingletonModel(model);
//            d.replaceWithObjectModel(singletonModel);
//        }
    }
}
