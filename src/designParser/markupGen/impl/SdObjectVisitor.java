package designParser.markupGen.impl;

import java.util.ArrayList;
import java.util.List;
import designParser.model.impl.MethodModel;

public class SdObjectVisitor extends SdModelVisitor {
    private StringBuilder sb;
    private List<SdObject> objs;

    public SdObjectVisitor(String targetMthdClass, String targetMthd, 
            String[] targetMthdParamTypes, int maxCallDepth) {
        super();
        this.maxCallDepth = maxCallDepth;
        this.targetMethodClassName = targetMthdClass;
        this.targetMethodName = targetMthd;
        this.targetMethodParamTypeNames = targetMthdParamTypes;
        
        // Set visit method.
        addVisitMethod(MethodModel.class, (m) -> {
            visitMethodModel((MethodModel) m); 
        });
        
        sb = new StringBuilder();
        objs = new ArrayList<SdObject>();
        objs.add(new SdObject(this.targetMethodClassName, true));
    }
        
    @Override
    public String getSdMarkup() {
        for (SdObject sdObject : objs) {
            if (!sdObject.isPreexisting) {
                sb.append('/');
            }
            sb.append(getObjDeclaration(sdObject.objectName));
            sb.append('\n');
        }       
        return sb.toString();
    }
    
    private void visitMethodModel(MethodModel m) {
        
        // Don't add the object name if it already exists.
        for (SdObject sdObject : objs) {
            if (m.getObjectName().equals(sdObject.objectName)) {
                return;
            }
        }
        
        // If an object type is used before it is created, then there is a
        // pre-existing instance of the object.
        objs.add(new SdObject(m.getObjectName(), !m.isConstructor()));
    }
    
    private String getObjDeclaration(String typeName) {
        String instanceName = typeToInstanceName(typeName);
        return instanceName + ":" + typeName + "[a]";
    }
}
