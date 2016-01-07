package designParser.umlGen.impl;

import designParser.model.api.IClass;
import designParser.model.api.IEnum;
import designParser.model.api.IField;
import designParser.model.api.IInterface;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
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
	
	public void previsit(IClass c) {
	    String header;
	    if (c.getIsConcrete()) {
	        header = "";
	    } else {
	        header = "\\<\\<abstract\\>\\>";
	    }
        appendObjPrevisitStr(sb, c, header);
	}    

	public void previsit(IInterface i) {
	    appendObjPrevisitStr(sb, i, "\\<\\<interface\\>\\>");
	}	
	
	public void previsit(IEnum e) {
        appendObjPrevisitStr(sb, e, "\\<\\<enum\\>\\>");
	}	
	
	public void visit(IClass c) {
	    sb.append("|");
	}

	public void visit(IMethod m) {
	    String signature = UmlProcessString.escapeAngleBraces(m.getMethodSignature());
	    sb.append(signature);
	    sb.append("\\l");
	}

	public void visit(IField f) {
        String signature = UmlProcessString.escapeAngleBraces(f.getSignature());
        sb.append(signature);
        sb.append("\\l");
	}
	
	public void postvisit(IClass c) {
	    appendObjPostvisitStr(sb);
	}

	public void postvisit(IInterface i) {
	    appendObjPostvisitStr(sb);
	}

	public void postvisit(IEnum e) {
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
