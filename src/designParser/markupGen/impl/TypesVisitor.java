package designParser.markupGen.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import designParser.markupGen.api.IModelVisitor;
import designParser.model.api.ITraversable;

public class TypesVisitor implements IModelVisitor {
    private Map<Class<?>, Consumer<ITraversable>> typeNameToPrevisitMethod;
    private Map<Class<?>, Consumer<ITraversable>> typeNameToVisitMethod;
    private Map<Class<?>, Consumer<ITraversable>> typeNameToPostvisitMethod;
    
    public TypesVisitor() {
        typeNameToPrevisitMethod = new  HashMap<Class<?>, Consumer<ITraversable>>();
        typeNameToVisitMethod = new  HashMap<Class<?>, Consumer<ITraversable>>();
        typeNameToPostvisitMethod = new  HashMap<Class<?>, Consumer<ITraversable>>();
    }
    
    @Override
    public void previsit(ITraversable t) {
        doVisit(t, typeNameToPrevisitMethod);
    }

    @Override
    public void visit(ITraversable t) {
        doVisit(t, typeNameToVisitMethod);
    }

    @Override
    public void postvisit(ITraversable t) {
        doVisit(t, typeNameToPostvisitMethod);
    }
    
    protected void addPrevisitMethod(Class<?> c, Consumer<ITraversable> mthd) {
        typeNameToPrevisitMethod.put(c, mthd);
    }

    protected void addVisitMethod(Class<?> c, Consumer<ITraversable> mthd) {
        typeNameToVisitMethod.put(c, mthd);
    }    
    
    protected void addPostvisitMethod(Class<?> c, Consumer<ITraversable> mthd) {
        typeNameToPostvisitMethod.put(c, mthd);
    }    
    
    /**
     * Execute a visit from the given mapping of types to visit methods. Do
     * nothing if a method is not defined for the class of the given 
     * ITraversable object.
     * @param t The object to visit
     * @param typeToMethod Mapping of types to visit methods
     */
    private void doVisit(ITraversable t, Map<Class<?>, Consumer<ITraversable>> typeToMethod) {
        Class<?> tClass = t.getClass();
        if (typeToMethod.containsKey(tClass)) {
            Consumer<ITraversable> mthd = typeToMethod.get(tClass);
            if (mthd != null) {
                mthd.accept(t);
            }
        }
    }
}
