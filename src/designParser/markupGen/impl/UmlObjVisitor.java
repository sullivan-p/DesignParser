package designParser.markupGen.impl;

import java.util.ArrayList;
import java.util.Collection;

import designParser.markupGen.util.UmlProcessString;
import designParser.model.api.IField;
import designParser.model.api.IMethod;
import designParser.model.api.IObject;
import designParser.model.impl.AbstractObjectModel;
import designParser.model.impl.AdapteeModel;
import designParser.model.impl.AdapterModel;
import designParser.model.impl.AdapterTargetModel;
import designParser.model.impl.ClassModel;
import designParser.model.impl.DecoratorComponentModel;
import designParser.model.impl.DecoratorModel;
import designParser.model.impl.EnumModel;
import designParser.model.impl.InterfaceModel;
import designParser.model.impl.SingletonModel;

public class UmlObjVisitor extends UmlModelVisitor {
    private StringBuilder sb;
    
    private Collection<String> currModelStereotypes;
    private String currModelColor;
    private boolean currModelFilled;
    
    private final String BLUE = "blue";
    private final String GREEN = "green";
    private final String RED = "red";
    private final String WHITE = "white";    
    private final String DEFAULT_COLOR = WHITE;
    private final boolean DEFAULT_FILL = true;
    
    private final String ABSTRACT_HEADER = "\\<\\<abstract\\>\\>";
    private final String INTERFACE_HEADER = "\\<\\<interface\\>\\>";
    private final String ENUM_HEADER = "\\<\\<enum\\>\\>";

    public UmlObjVisitor() {
        super();
        this.currModelStereotypes = new ArrayList<String>();
        this.currModelColor = DEFAULT_COLOR;
        this.currModelFilled = DEFAULT_FILL;
        
        // Set previsit methods.
        addPrevisitMethod(ClassModel.class, (m) -> {
           previsitClassModel((ClassModel) m); 
        });
        addPrevisitMethod(InterfaceModel.class, (m) -> {
            previsitInterfaceModel((InterfaceModel) m); 
        });
        addPrevisitMethod(EnumModel.class, (m) -> {
            previsitEnumModel((EnumModel) m); 
        });
        
        // Set visit methods.
        addVisitMethod(ClassModel.class, (m) -> {
            visitClassModel((ClassModel) m); 
        });
        addVisitMethod(InterfaceModel.class, (m) -> {
            visitInterfaceModel((InterfaceModel) m); 
        });
        addVisitMethod(EnumModel.class, (m) -> {
            visitEnumModel((EnumModel) m); 
        });
        addVisitMethod(SingletonModel.class, (m) -> {
            currModelStereotypes.add("\\<\\<singleton\\>\\>");
            currModelColor = BLUE;
            currModelFilled = false;
        });
        addVisitMethod(DecoratorModel.class, (m) -> {
            currModelStereotypes.add("\\<\\<decorator\\>\\>");
            currModelColor = GREEN;
        });
        addVisitMethod(DecoratorComponentModel.class, (m) -> {
            currModelStereotypes.add("\\<\\<component\\>\\>");
            currModelColor = GREEN;
        });
        addVisitMethod(AdapterModel.class, (m) -> {
            currModelStereotypes.add("\\<\\<adapter\\>\\>");
            currModelColor = RED;
        });
        addVisitMethod(AdapteeModel.class, (m) -> {
            currModelStereotypes.add("\\<\\<adaptee\\>\\>");
            currModelColor = RED;
        });
        addVisitMethod(AdapterTargetModel.class, (m) -> {
            currModelStereotypes.add("\\<\\<target\\>\\>");
            currModelColor = RED;
        });
        
        // Set postvisit methods.
        addPostvisitMethod(ClassModel.class, (m) -> {
            postvisitObjModel((ClassModel) m); 
        });
        addPostvisitMethod(InterfaceModel.class, (m) -> {
            postvisitObjModel((InterfaceModel) m); 
        });
        addPostvisitMethod(EnumModel.class, (m) -> {
            postvisitObjModel((EnumModel) m); 
        });    
        
        sb = new StringBuilder();
    }
	
	public String getUmlMarkup() {
		return sb.toString();
	}
	
	
    //--------------------------------------------------------------------------
	// Pre and postvisit methods for classes, interfaces, and enums
    //--------------------------------------------------------------------------	
	
	private void previsitClassModel(ClassModel c) {
	    String header = "";
	    if (!c.getIsConcrete()) {//        isSingletonClass = false;

	        header = ABSTRACT_HEADER;
	    }
        appendObjPrevisitStr(sb, c, header);
	}    

	private void previsitInterfaceModel(InterfaceModel i) {
	    appendObjPrevisitStr(sb, i, INTERFACE_HEADER);
	}	
	
	private void previsitEnumModel(EnumModel e) {
        appendObjPrevisitStr(sb, e, ENUM_HEADER);
	}	

	/**
	 * Call after a class, interface, or enum has been visited to finish 
	 * generating markup for the object and to reset the visitor's current
	 * object stereotypes, color, and fill style.
	 */
    private void postvisitObjModel(AbstractObjectModel model) {
        appendObjPostvisitStr(sb);
        currModelStereotypes.clear();
        currModelColor = DEFAULT_COLOR;
        currModelFilled = DEFAULT_FILL;
    }

    
    //--------------------------------------------------------------------------
    // Visit methods for design pattern annotations
    //--------------------------------------------------------------------------    
    
    private void visitClassModel(ClassModel c) {
        for (IField f : c.getAllFieldModels()) {
            String fieldSig = UmlProcessString.escapeAngleBraces(f.getSignature());
            sb.append(fieldSig);
            sb.append("\\l");
        }
        sb.append("|");     
        appendMethodDeclarations(sb, c);
    }
    
   private void visitInterfaceModel(InterfaceModel i) {     
        appendMethodDeclarations(sb, i);
    }
   
    private void visitEnumModel(EnumModel e) {
        appendMethodDeclarations(sb, e);
    }

    
    //--------------------------------------------------------------------------
    // Markup building methods
    //--------------------------------------------------------------------------    
	
	private void appendMethodDeclarations(StringBuilder s, IObject o) {
       for (IMethod m : o.getAllMethodModels()) {
            String mthdSig = UmlProcessString.escapeAngleBraces(m.getSignature());
            s.append(mthdSig);
            s.append("\\l");
        }
	}
	
	private void appendObjPrevisitStr(StringBuilder s, IObject o, String extraHeader) {
        s.append(o.getName());
        s.append(" [\n");
        s.append("shape = \"record\",\n");
        s.append("label = \"{");
        if (extraHeader != null && extraHeader != "") {
            s.append(extraHeader);
            s.append("\\n");
        }
        s.append(o.getName());
        for (String stereotype : currModelStereotypes) {
             s.append("\\n");           
             s.append(stereotype);
        }
        s.append("|");
    }

	private void appendObjPostvisitStr(StringBuilder s) {
        s.append("}\"\n");
        if (currModelFilled) {
            s.append("style = filled\n");
            s.append("fillcolor = ");
        } else {
            s.append("color = ");
        }
        s.append(currModelColor);
        s.append("\n");
        s.append("];\n");	
    }
}
