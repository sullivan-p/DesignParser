package designParser.markupGen.impl;

import designParser.markupGen.util.UmlProcessString;
import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.model.impl.ClassModel;
import designParser.model.impl.EnumModel;
import designParser.model.impl.FieldModel;
import designParser.model.impl.InterfaceModel;
import designParser.model.impl.MethodModel;

public class UmlObjVisitor extends UmlModelVisitor {
    private StringBuilder sb;

    public UmlObjVisitor() {
        super();
        
        // Set previsit methods.
        addPrevisitMethod(ClassModel.class, (c) -> {
           previsitClassModel((ClassModel) c); 
        });
        addPrevisitMethod(InterfaceModel.class, (i) -> {
            previsitInterfaceModel((InterfaceModel) i); 
        });
        addPrevisitMethod(EnumModel.class, (e) -> {
            previsitEnumModel((EnumModel) e); 
        });
        
        // Set visit methods.
        addVisitMethod(ClassModel.class, (c) -> {
            visitClassModel((ClassModel) c); 
        });
        addVisitMethod(MethodModel.class, (i) -> {
            visitIMethod((MethodModel) i); 
        });
        addVisitMethod(FieldModel.class, (f) -> {
            visitIField((FieldModel) f); 
        });
        
        // Set postvisit methods.
        addPostvisitMethod(ClassModel.class, (c) -> {
            postvisitClassModel((ClassModel) c); 
        });
        addPostvisitMethod(InterfaceModel.class, (i) -> {
            postvisitInterfaceModel((InterfaceModel) i); 
        });
        addPostvisitMethod(EnumModel.class, (e) -> {
            postvisitEnumModel((EnumModel) e); 
        });    
        
        sb = new StringBuilder();
    }
	
	public String getUmlMarkup() {
		return sb.toString();
	}
	
	private void previsitClassModel(ClassModel c) {
	    String header;
	    if (c.getIsConcrete()) {
	        header = "";
	    } else {
	        header = "\\<\\<abstract\\>\\>";
	    }
        appendObjPrevisitStr(sb, c, header);
	}    

	private void previsitInterfaceModel(InterfaceModel i) {
	    appendObjPrevisitStr(sb, i, "\\<\\<interface\\>\\>");
	}	
	
	private void previsitEnumModel(EnumModel e) {
        appendObjPrevisitStr(sb, e, "\\<\\<enum\\>\\>");
	}	
	
	private void visitClassModel(ClassModel c) {
	    sb.append("|");
	}

	private void visitIMethod(IMethod m) {
	    String signature = UmlProcessString.escapeAngleBraces(m.getSignature());
	    sb.append(signature);
	    sb.append("\\l");
	}

	private void visitIField(IField f) {
        String signature = UmlProcessString.escapeAngleBraces(f.getSignature());
        sb.append(signature);
        sb.append("\\l");
	}
	
	private void postvisitClassModel(ClassModel c) {
	    appendObjPostvisitStr(sb);
	}

	private void postvisitInterfaceModel(InterfaceModel i) {
	    appendObjPostvisitStr(sb);
	}

	private void postvisitEnumModel(EnumModel e) {
	    appendObjPostvisitStr(sb);
	}
	
	private static void appendObjPrevisitStr(StringBuilder s, IObject o,
	        String extraHeader) {
        s.append(o.getName());
        s.append(" [\n");
        s.append("shape = \"record\",\n");
        s.append("label = \"{");
        if (extraHeader != null && extraHeader != "") {
            s.append(extraHeader);
            s.append("\\n");
        }
        s.append(o.getName());
        s.append("|");
    }

	private static void appendObjPostvisitStr(StringBuilder s) {
        s.append("}\"\n");
        s.append("];\n");	
    }
}
