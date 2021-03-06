package designParser.asm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.objectweb.asm.Opcodes;

import designParser.model.impl.AccessLevel;
import designParser.model.impl.ArrayModel;
import designParser.model.impl.PrimitiveBooleanDataType;
import designParser.model.impl.PrimitiveByteDataType;
import designParser.model.impl.PrimitiveCharDataType;
import designParser.model.impl.PrimitiveDoubleDataType;
import designParser.model.impl.PrimitiveFloatDataType;
import designParser.model.impl.PrimitiveIntDataType;
import designParser.model.impl.PrimitiveLongDataType;
import designParser.model.impl.PrimitiveShortDataType;

public final class AsmProcessData {    
    public final static String CONSTRUCTOR_NAME = "<init>";
    
    private AsmProcessData() {}
    
    /**
     * Given an ASM name, generate and return a Java qualified name.
     */
    public static String convertAsmToJavaName(String asmName) {
        return asmName.replace("/", ".");
    }    
    
    /**
     * Given the qualified name of a Java class, interface, or enum, return the
     * unqualified name of that class, interface, or enum.
     */
    public static String qualifiedToUnqualifiedName(String name) {
        StringTokenizer st = new StringTokenizer(name, ".");
        String unqualifiedName = "";
        while (st.hasMoreTokens()) {
            unqualifiedName = st.nextToken();
        }
        return unqualifiedName;
    }
    
    /**
     * Examine the opcode and return true if it indicates that the ClassVisitor 
     * is visiting an interface, false otherwise.
     */
    public static boolean isInterface(int opcode) {
        int bitmask = Opcodes.ACC_INTERFACE;
        return (opcode & bitmask) != 0;
    }

    /**
     * Examine the opcode and return true if it indicates that the ClassVisitor 
     * is visiting an enum, false otherwise.
     */
    public static boolean isEnum(int opcode) {
        int bitmask = Opcodes.ACC_ENUM;
        return (opcode & bitmask) != 0;    
    }
    
    /**
     * Examine the opcode and return true if it indicates that the ClassVisitor 
     * is visiting an abstract class, false otherwise.
     */
    public static boolean isAbstract(int opcode) {
        int bitmask = Opcodes.ACC_ABSTRACT;
        return (opcode & bitmask) != 0;    
    }
    
    /**
     * Examine the opcode and return the corresponding access level.
     */
    public static AccessLevel getAccessLevel(int opcode) {
        if ((opcode & Opcodes.ACC_PUBLIC) != 0) {
            return AccessLevel.Public;
        } else if ((opcode & Opcodes.ACC_PROTECTED) != 0) {
            return AccessLevel.Protected;
        } else if ((opcode & Opcodes.ACC_PRIVATE) != 0) {
            return AccessLevel.Private;
        } else {
            return AccessLevel.Default;
        }
    }
    
    public static boolean isStatic(int opcode) {
        return (opcode & Opcodes.ACC_STATIC) == Opcodes.ACC_STATIC;
    }
    
    public static String prettyRetTypeFromMthdDesc(String mthdDescriptor) {
        int paramsEnd = mthdDescriptor.indexOf(')');
        String returnSubStr = mthdDescriptor.substring(paramsEnd+1);
        return AsmProcessData.getPrettyTypeNames(returnSubStr);    
    }
    
    public static String[] prettyParamTypesFromMthdDesc(String mthdDescriptor) {
        int paramsEnd = mthdDescriptor.indexOf(')');
        String paramsSubStr = mthdDescriptor.substring(1, paramsEnd);
        String prettyParamTypes = AsmProcessData.getPrettyTypeNames(paramsSubStr);
        return prettyParamTypes.split(", ");
    }
    
    public static String getMethodSignature(String className, String methodName, String descriptor) {      
        String prettyParamTypes = String.join(", ", prettyParamTypesFromMthdDesc(descriptor));
        String prettyReturnType = prettyRetTypeFromMthdDesc(descriptor);
        
        // Special case for constructor methods.
        if (methodName.equals("<init>")) {
            String objName = className;
            methodName = AsmProcessData.qualifiedToUnqualifiedName(objName);
            return methodName + "(" + prettyParamTypes + ")";
        }
        
        return prettyReturnType + " " + methodName + "(" + prettyParamTypes + ")";
    }
    
    public static String getMethodSignature(String className, String methodName, String descriptor, AccessLevel al) {      
        return al.toUmlString() + " " + getMethodSignature(className, methodName, descriptor);
    }
    
    
    /**
     * Return the names of all data types that are described in the descriptor.
     * If a type is a generic type, include type parameter names as well.
     * FIXME: This method returns "void" in the set when passed a descriptor for 
     * a void method and returns "[]" in the set when passed a descriptor that 
     * includes an array.
     */
    public static Set<String> getTypeNamesFromDescriptor(String descriptor) {       
        Set<String> typeNames = new HashSet<String>();
        
        StringTokenizer st = new StringTokenizer(descriptor, "()<>;");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.charAt(0) == 'L') {
                typeNames.add(qualifiedToUnqualifiedName(
                        convertAsmToJavaName(token.substring(1))));
            } else {
                for (int charIdx = 0; charIdx < token.length(); ++charIdx) {
                    char c = token.charAt(charIdx);
                    if (c == 'L') {
                        typeNames.add(qualifiedToUnqualifiedName(
                                convertAsmToJavaName(token.substring(charIdx))));
                        break;
                    }
                    typeNames.add(getSymbolToTypeNameMap().get(c));
                }
            }
        }

        return typeNames;
    } 
    
    /**
     * Return a formated string that contains the names of the types described 
     * by the given type descriptor.
     */
    public static String getPrettyTypeNames(String typeDescriptor) {
        
        // Handle the special case of the * type param, which is not followed by
        // a semicolon like other type params.
        //typeDescriptor = typeDescriptor.replace("*", "*;>;");
        
        final int NORMAL = 0;
        final int READING_SPECIAL = 1;
        final int INVALID = 2;
        
        ArrayList<String> typeNames = new ArrayList<String>();
        String typeName = "";
        int dimCount = 0;
        int state = NORMAL;
        
        for (int charIdx = 0; charIdx < typeDescriptor.length(); ++charIdx) {
            char c = typeDescriptor.charAt(charIdx);
            switch (state) {
            case NORMAL:
                if (c == '[') {
                    dimCount++;
                } else if (getSymbolToTypeNameMap().containsKey(c)) {
                    typeName = getSymbolToTypeNameMap().get(c);
                    for (int i = 0; i < dimCount; ++i) {
                        typeName += "[]";
                    }
                    dimCount = 0;
                    typeNames.add(typeName);
                    typeName = "";
                } else if (c == 'L' || c == 'T') {  
                    // Note: Treating regular object types (L) and generic types
                    // (T) the same may cause problems.
                    state = READING_SPECIAL;
                } else if (c == '-') {
                    // Do nothing.
                    // Note: This may cause problems.
                } else {
                    state = INVALID;
                }
                break;
            case READING_SPECIAL:
                if (c == '>') {
                    state = INVALID;
                } else if (c == ';') {
                    typeName = qualifiedToUnqualifiedName(convertAsmToJavaName(typeName));
                    for (int i = 0; i < dimCount; ++i) {
                        typeName += "[]";
                    }
                    dimCount = 0;
                    typeNames.add(typeName);
                    typeName = "";
                    state = NORMAL;
                } else if (c == '<') {
                    int closingIdx = typeDescriptor.substring(charIdx).indexOf('>') + charIdx;
                    
                    // Extract the type params (without the enclosing brackets),
                    // and get the pretty-printed string of the type params.
                    String typeParams = typeDescriptor.substring(charIdx+1, closingIdx);
                    String prettyTypeParams = getPrettyTypeNames(typeParams);
                    typeName = qualifiedToUnqualifiedName(convertAsmToJavaName(typeName)) + 
                            "<" + prettyTypeParams + ">";
                    for (int i = 0; i < dimCount; ++i) {
                        typeName += "[]";
                    }
                    dimCount = 0;
                    typeNames.add(typeName);
                    typeName = "";
                    
                    // Skip the type params and the semicolon that follows.
                    charIdx = closingIdx + 1;  
                    
                    state = NORMAL;
                } else {
                    typeName += c;
                }
                break;
            default:
                throw new IllegalArgumentException();
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for (int nameIdx = 0; nameIdx < typeNames.size(); ++nameIdx) {
            sb.append(typeNames.get(nameIdx));
            if (nameIdx < typeNames.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    private static HashMap<Character, String> getSymbolToTypeNameMap() {
        HashMap<Character, String> symbolToDataType = new HashMap<Character, String>();
        symbolToDataType.put('Z', (new PrimitiveBooleanDataType()).getName());
        symbolToDataType.put('C', (new PrimitiveCharDataType()).getName());
        symbolToDataType.put('B', (new PrimitiveByteDataType()).getName());
        symbolToDataType.put('S', (new PrimitiveShortDataType()).getName());
        symbolToDataType.put('I', (new PrimitiveIntDataType()).getName());
        symbolToDataType.put('F', (new PrimitiveFloatDataType()).getName());
        symbolToDataType.put('J', (new PrimitiveLongDataType()).getName());
        symbolToDataType.put('D', (new PrimitiveDoubleDataType()).getName());
        symbolToDataType.put('V', "void");
        symbolToDataType.put('*', "*");
        symbolToDataType.put('[', (new ArrayModel()).getName());
        return symbolToDataType;
    }
}
