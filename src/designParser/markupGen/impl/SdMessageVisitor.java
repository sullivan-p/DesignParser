package designParser.markupGen.impl;

import java.util.Collection;
import java.util.Stack;
import java.util.TreeSet;

import designParser.model.impl.ClassModel;
import designParser.model.impl.MethodModel;

public class SdMessageVisitor extends SdModelVisitor {
    private StringBuilder sb;
    
    private Stack<String> callerInstances;
    private Collection<String> constructedInstances;

    public SdMessageVisitor(String targetMthdClass, String targetMthd, 
            String[] targetMthdParamTypes, int maxCallDepth) {
        super();
        this.maxCallDepth = maxCallDepth;
        this.targetMethodClassName = targetMthdClass;
        this.targetMethodName = targetMthd;
        this.targetMethodParamTypeNames = targetMthdParamTypes;
        
        // Set visit methods.
        addVisitMethod(MethodModel.class, (m) -> {
            visitMethodModel((MethodModel) m); 
        });
        addPostvisitMethod(MethodModel.class, (m) -> {
            postvisitMethodModel((MethodModel) m); 
        });
        
        sb = new StringBuilder();
        this.callerInstances = new Stack<String>();
        this.constructedInstances = new TreeSet<String>();
    }
        
    @Override
    public String getSdMarkup() {
        return sb.toString();
    }
    
    private void visitMethodModel(MethodModel m) {
        
        // If there is no caller instance name, then this is the target method,
        // and it is not displayed in the sequence diagram.
        
        if (!callerInstances.isEmpty()) {
            String callerInstanceName = callerInstances.peek();
            String calleeInstanceName = typeToInstanceName(m.getObjectName());
            sb.append(callerInstanceName);
            sb.append(':');
            sb.append(calleeInstanceName);
            sb.append('.');
            if (m.isConstructor() && !constructedInstances.contains(calleeInstanceName)) {
                sb.append("new");
                constructedInstances.add(calleeInstanceName);
            } else {
                sb.append(m.getAbbrevSignature());
            }
            sb.append('\n');
        }
        
        // Push the current instance name onto the callerInstances stack, so 
        // that the methods called by this method cand see what object called
        // them.
        callerInstances.push(typeToInstanceName(m.getObjectName()));
    }
    
    private void postvisitMethodModel(MethodModel m) {
        // The markup for this method and its method calls has been generated,
        // so restore the old callerInstance of this method's caller.
        callerInstances.pop();
    }
}
