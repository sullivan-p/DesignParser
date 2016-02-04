package designParser.markupGen.impl;

import designParser.visitor.impl.TypesVisitor;

public abstract class SdModelVisitor extends TypesVisitor {    
    public abstract String getSdMarkup();
    
    public static String typeToInstanceName(String typeName) {
        if (typeName.length() == 0) {
            return typeName;
        }
      
        char first = typeName.charAt(0);
        char lowerFirst = Character.toLowerCase(first);
        return lowerFirst + typeName.substring(1);        
    }
}
