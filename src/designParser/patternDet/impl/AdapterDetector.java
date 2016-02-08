package designParser.patternDet.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import designParser.model.api.IDependencyRelation;
import designParser.model.api.IDesignModel;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.model.impl.AbstractHierarchyRelation;
import designParser.model.impl.AccessLevel;
import designParser.model.impl.AdapteeModel;
import designParser.model.impl.AdapterModel;
import designParser.model.impl.AdapterTargetModel;
import designParser.model.impl.AdaptsRelation;
import designParser.model.impl.ClassModel;
import designParser.model.impl.DesignModel;
import designParser.model.impl.ImplementsRelation;
import designParser.patternDet.api.PatternDetector;

public class AdapterDetector extends PatternDetector {
    private IDesignModel designModel;
    private HashMap<String, HashSet<String>> adapterToAdaptees;
    private HashMap<String, HashSet<String>> adapterToTargets;
    
    public AdapterDetector() {
        super();
        this.adapterToAdaptees = new HashMap<String, HashSet<String>>();
        this.adapterToTargets = new HashMap<String, HashSet<String>>();
        
        addPrevisitMethod(DesignModel.class, (d) -> {
            designModel = (DesignModel)d;
        });
        
        addVisitMethod(ClassModel.class, (model) -> {
            visitObjModel((IObject) model); 
        });
        
        addVisitMethod(ImplementsRelation.class, (rltn) -> {
            visitImplementsRltn((ImplementsRelation) rltn); 
        });

        addPostvisitMethod(DesignModel.class, (d) -> {
            postvisitDesignModel((DesignModel) d); 
        });
    }

    /**
     * Determine if the object might be an adapter for one or more adaptee, and
     * record the adapter and adaptees.
     */
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
        
        // Clean up the constructor parameters. Remove the empty string if it is
        // is present, and remove type parameters from types.
        constrParamTypes.remove("");
        for (String typeName : constrParamTypes) {
            int typeParamStart = typeName.indexOf('<');
            if (typeParamStart > -1) {
                constrParamTypes.remove(typeName);
                constrParamTypes.add(typeName.substring(0, typeParamStart));
            }
        }
                
        for (String constrParamType : constrParamTypes) {
            IObject paramTypeModel = designModel.getObjectModel(constrParamType);
            if (isAdapterAdapteeRltn(objModel, paramTypeModel)) {
                if (!adapterToAdaptees.containsKey(objName)) {
                    adapterToAdaptees.put(objName, new HashSet<String>());
                }
                HashSet<String> adaptees = adapterToAdaptees.get(objName);
                adaptees.add(constrParamType);
            }
        }
    }    
    
    private boolean isAdapterAdapteeRltn(IObject potentialAdapter, IObject potentialAdaptee) {
        
        // Get all methods called from within the potential adapter.
        Set<IMethod> adapterMthdCalls = new HashSet<IMethod>();
        for (IMethod m : potentialAdapter.getAllMethodModels()) {
            adapterMthdCalls.addAll(m.getMethodCalls());
        }
        
        // Get the public methods of the potential adaptee.
        Set<String> adapteePubMthds = new HashSet<String>();
        for (IMethod m : potentialAdaptee.getAllMethodModels()) {
            if (m.getAccessLevel() == AccessLevel.Public) {
                adapteePubMthds.add(m.getSignature());
            }
        }
        
        // Get the potential adaptee methods that are called from within the
        // potential adapter.
        Set<String> callsToAdaptee = new HashSet<String>();
        for (IMethod m : adapterMthdCalls) {
            if (adapteePubMthds.contains(m.getSignature()) &&
                designModel.isSubType(m.getObjectName(), potentialAdaptee.getName())) {
                callsToAdaptee.add(m.getSignature());
            }
        }
        
        return callsToAdaptee.size() >= ((double)adapteePubMthds.size()) * 0.5 ;
    }
    
    /**
     * Map each potential adapter to the interfaces that it targets.
     */
    private void visitImplementsRltn(ImplementsRelation rltn) {
        String srcName = rltn.getSourceName();
        
        // Only consider relations with sources that have already been
        // identified as potential adapters.
        if (!adapterToAdaptees.containsKey(srcName)) return;
        
        if (!adapterToTargets.containsKey(srcName)) {
            adapterToTargets.put(srcName, new HashSet<String>());
        }
        HashSet<String> targets = adapterToTargets.get(srcName);
        targets.add(rltn.getDestinationName());
    }
    
    private void postvisitDesignModel(DesignModel d) {
        
        // Make the key sets of adapterToAdaptees and adapterToTargets match.
        // Do not consider an object to be an adapter if it does not implement a
        // modeled interface.
        HashSet<String> potentialAdapters = new HashSet<String>();
        potentialAdapters.addAll(adapterToAdaptees.keySet());
        for (String adapterName : potentialAdapters) {
            if (!adapterToTargets.containsKey(adapterName)) {
                adapterToAdaptees.remove(adapterName);
            }
        }
        
        for (String adapterName : adapterToAdaptees.keySet()) {
            
            // Decorate adapter.
            IObject unannotatedAdapter = d.getObjectModel(adapterName);
            IObject adapterModel = new AdapterModel(unannotatedAdapter);
            d.replaceWithObjectModel(adapterModel);
            
            Set<String> adapteeNames = adapterToAdaptees.get(adapterName);
            for (String adapteeName : adapteeNames) {
                
                // Decorate adaptee.
                IObject unannotatedAdaptee = d.getObjectModel(adapteeName);
                IObject adapteeModel = new AdapteeModel(unannotatedAdaptee);
                d.replaceWithObjectModel(adapteeModel);
                
                // Decorate relation.
                IDependencyRelation rltn = d.getDepRltn(adapterName, adapteeName);
                IDependencyRelation adaptsRltn = new AdaptsRelation(rltn);
                d.replaceWithDepRltn(adaptsRltn);
            }
        }
        
        // Decorate targets.
        HashSet<String> allTargets = new HashSet<String>();
        for (HashSet<String> targetSet : adapterToTargets.values()) {
            allTargets.addAll(targetSet);
        }
        for (String target : allTargets) {
            IObject model = d.getObjectModel(target);
            IObject targetModel = new AdapterTargetModel(model);
            d.replaceWithObjectModel(targetModel);
        }
    }
}
