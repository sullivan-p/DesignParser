package designParser.markupGen.impl;

import java.util.Stack;

import designParser.model.api.IMethod;
import designParser.model.api.IMethodCall;
import designParser.model.impl.MethodCall;

public abstract class SdModelVisitor extends TypesVisitor {    
    
    // Stack for storing the descriptors of methods that the visitor needs to
    // visit.
    private Stack<IMethodCall> toCall;
    private Stack<IMethod> callStack;
    private StringBuilder sb;
    
    public SdModelVisitor() {
        super();
        
        toCall = new Stack<IMethodCall>();
        callStack = new Stack<IMethod>();
        sb = new StringBuilder();

        // Set visit methods.
        addVisitMethod(MethodCall.class, (mc) -> {
            visitMethodCall((MethodCall) mc); 
        });
    }
    
    private void visitMethodCall(MethodCall mc) {
        sb.append(mc.getCalleeReturnTypeName());
        sb.append(" = ");
        sb.append(mc.getCallerClassName().toLowerCase());
        sb.append(":");
        sb.append(mc.getCalleeClassName().toLowerCase());
        sb.append(".");
        sb.append(mc.getCalleeMethodName());
        sb.append("(");
        sb.append(String.join(", ", mc.getCalleeParamTypeNames()));
        sb.append(")");
    }
    
    public abstract String getSdMarkup();
    
    public boolean hasMethodToCall() {
        return !toCall.isEmpty();
    }
    
    public void pushMethodToCall(IMethodCall mc) {
        toCall.push(mc);
    }
    
    public IMethodCall popMethodToCall() {
        return toCall.pop();
    }
}
