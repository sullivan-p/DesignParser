package designParser.umlGen.impl;

import designParser.model.api.IClass;
import designParser.model.api.IEnum;
import designParser.model.api.IInterface;
import designParser.umlGen.api.UmlModelVisitor;

public class UmlInheritanceVisitor extends UmlModelVisitor{
    private StringBuilder sb;
    
    public UmlInheritanceVisitor() {
        sb = new StringBuilder();
    }

	public String getUmlMarkup() {
        return sb.toString();
	}

	public void visit(IClass c) {
	    if (c.getExtendedClass() != null) {
	        appendExtendsArrow(sb, c.getName(), c.getExtendedClass().getName());
	    }
	    for (IInterface i : c.getInterfaces()) {
	        appendImplementsArrow(sb, c.getName(), i.getName());
	    }
	}

	public void visit(IInterface i) {
	    for (IInterface extInterface : i.getExtendedInterfaces()) {
	        appendExtendsArrow(sb, i.getName(), extInterface.getName());
	    }
	}

	public void visit(IEnum e) {
        for (IInterface i : e.getInterfaces()) {
            appendImplementsArrow(sb, e.getName(), i.getName());
        }
    }
	
	private static void appendExtendsArrow(StringBuilder s, String subclassName, 
	        String superclassName) {
	    appendArrow(s, subclassName, superclassName, "onormal", "solid");
    }

    private static void appendImplementsArrow(StringBuilder s, String subclassName, 
            String superclassName) {
        appendArrow(s, subclassName, superclassName, "onormal", "dashed");
    }
    
	private static void appendArrow(StringBuilder s, String sourceName, 
	        String destName, String arrowHead, String style) {
        s.append(sourceName);
        s.append(" -> ");
        s.append(destName);
        s.append(" [arrowhead=\"");
        s.append(arrowHead);
        s.append("\", style=\"");
        s.append(style);
        s.append("\"];\n");    
	}
}
