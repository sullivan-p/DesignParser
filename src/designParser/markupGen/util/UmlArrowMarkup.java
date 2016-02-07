package designParser.markupGen.util;

public final class UmlArrowMarkup {
    private final static String OPEN_ARROWHEAD = "onormal";
    private final static String V_ARROWHEAD = "vee";
    private final static String SOLID_LINE = "solid";
    private final static String DASHED_LINE = "dashed";
    
    private UmlArrowMarkup() {}

    public static String getExtendsArrow(String subclassName, String superclassName, String label) {
        return getArrow(subclassName, superclassName, OPEN_ARROWHEAD, SOLID_LINE, label);
    }

    public static String getImplementsArrow(String subclassName, String superclassName, String label) {
        return getArrow(subclassName, superclassName, OPEN_ARROWHEAD, DASHED_LINE, label);
    }
    
    public static String getReferencesArrow(String subclassName, String superclassName, String label) {
        return getArrow(subclassName, superclassName, V_ARROWHEAD, DASHED_LINE, label);
    }

    public static String getAssociatesArrow(String subclassName, String superclassName, String label) {
        return getArrow(subclassName, superclassName, V_ARROWHEAD, SOLID_LINE, label);
    }
    
    private static String getArrow(String sourceName, String destName, 
            String arrowHead, String style, String label) {
        StringBuilder sb = new StringBuilder();
        sb.append(sourceName);
        sb.append(" -> ");
        sb.append(destName);
        sb.append(" [arrowhead=\"");
        sb.append(arrowHead);
        sb.append("\", style=\"");
        sb.append(style);
        if (label != null && label != "") {
            sb.append("\", label=\"");
            sb.append(label);
        }
        sb.append("\"];\n");    
        return sb.toString();
    }
}
