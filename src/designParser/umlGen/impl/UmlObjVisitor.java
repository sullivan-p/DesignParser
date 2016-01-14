package designParser.umlGen.impl;

import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.model.impl.ClassModel;
import designParser.model.impl.EnumModel;
import designParser.model.impl.InterfaceModel;
import designParser.umlGen.api.UmlModelVisitor;
import designParser.umlGen.util.UmlProcessString;

public class UmlObjVisitor extends UmlModelVisitor {
    private StringBuilder sb;

    public UmlObjVisitor() {
        sb = new StringBuilder();
    }
	
	public String getUmlMarkup() {
		return sb.toString();
	}
	
	public void previsit(ClassModel c) {
	    String header;
	    if (c.getIsConcrete()) {
	        header = "";
	    } else {
	        header = "\\<\\<abstract\\>\\>";
	    }
        appendObjPrevisitStr(sb, c, header);
	}    

	public void previsit(InterfaceModel i) {
	    appendObjPrevisitStr(sb, i, "\\<\\<interface\\>\\>");
	}	
	
	public void previsit(EnumModel e) {
        appendObjPrevisitStr(sb, e, "\\<\\<enum\\>\\>");
	}	
	
	public void visit(ClassModel c) {
	    sb.append("|");
	}

	public void visit(IMethod m) {
	    String signature = UmlProcessString.escapeAngleBraces(m.getSignature());
	    sb.append(signature);
	    sb.append("\\l");
	}

	public void visit(IField f) {
        String signature = UmlProcessString.escapeAngleBraces(f.getSignature());
        sb.append(signature);
        sb.append("\\l");
	}
	
	public void postvisit(ClassModel c) {
	    appendObjPostvisitStr(sb);
	}

	public void postvisit(InterfaceModel i) {
	    appendObjPostvisitStr(sb);
	}

	public void postvisit(EnumModel e) {
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
