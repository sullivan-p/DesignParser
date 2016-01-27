package designParser.asm.visitor;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import designParser.asm.util.AsmProcessData;
import designParser.model.api.IDesignModel;

public class MethodCodeVisitor extends MethodVisitor {
    private IDesignModel model;
    
    // Descriptors for the method which calls the methods that this visitor will
    // traverse.
    private String callerClassName;
    private String callerMethodName;
    private String[] callerParamTypeNames;
    
    private int currLineNum;
    
    private Map<Integer, String> localTypes;
    private Map<String, String> fieldTypes;
    
    private String lastRetType;  // The last type that was returned by a method.
    private int lastRetLineNum;  // The last line on which a method executed.

    // The first type in a sequence of consecutive variable loads.
    private String firstLoadedType;  
    // The line number on which the variable was loaded.
    private int firstLoadedLineNum;

    public MethodCodeVisitor(IDesignModel m, String callerClassName, String callerMethodName, 
            String[] callerParamTypeNames, int api, MethodVisitor mv) {
        super(api, mv);
        this.model = m;
        this.callerClassName = callerClassName;
        this.callerMethodName = callerMethodName;
        this.callerParamTypeNames = callerParamTypeNames;
        this.localTypes = new HashMap<Integer, String>();
        this.fieldTypes = new HashMap<String, String>();
    }
    
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        boolean isConstructor = name.equals(AsmProcessData.CONSTRUCTOR_NAME);
        
        String calleeClassName = AsmProcessData.qualifiedToUnqualifiedName(
                AsmProcessData.convertAsmToJavaName(owner));
        String[] calleeParamTypesNames = AsmProcessData.prettyParamTypesFromMthdDesc(desc);
        String calleeReturnTypeName = AsmProcessData.prettyRetTypeFromMthdDesc(desc);
        String calleeMethodName;
        
        // Handle the special case for constructor methods.
        if (isConstructor) {
            calleeMethodName = calleeClassName;
            calleeReturnTypeName = calleeClassName;
        } else {
            calleeMethodName = name;
        }
        
        // Save the return type name so that it can be associated with a local variable.
        lastRetType = calleeReturnTypeName;
        lastRetLineNum = currLineNum;

        
        // Use the object on which the method is called to determine which object implements
        // the method.
        if (opcode != Opcodes.INVOKESTATIC && !isConstructor &&
            firstLoadedType != null && firstLoadedLineNum == currLineNum) {
            calleeClassName = firstLoadedType;
        }
        
        // Reset the first type in the sequence of consecutive variable loads.
        firstLoadedType = null;
        
        // Do not add calls to the Object superconstructor that are made from 
        // within other constructors.
        if (callerClassName.equals(callerMethodName) && !callerClassName.equals("Object") &&
            isConstructor && calleeClassName.equals("Object")) {
            return;
        }
        
        model.putMethodCall(callerClassName, callerMethodName, callerParamTypeNames, 
                calleeClassName, calleeMethodName, calleeParamTypesNames, calleeReturnTypeName, isConstructor);
    }    
    
    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        String type = AsmProcessData.prettyRetTypeFromMthdDesc(desc);
        switch (opcode) {
            case Opcodes.GETSTATIC:  // 178
            case Opcodes.GETFIELD:   // 180
                setFirstLoadedType(type);
                break;               
            case Opcodes.PUTSTATIC:  // 179
            case Opcodes.PUTFIELD:   // 181
                fieldTypes.put(name, type);
                firstLoadedType = null;
                break;
            default:
                break;
        }
    }
    
    @Override
    public void visitTypeInsn(int opcode, String type) {
        type = AsmProcessData.qualifiedToUnqualifiedName(
                AsmProcessData.convertAsmToJavaName(type));
        switch (opcode) {
            case Opcodes.NEW:  // 187
                lastRetType = type;
                lastRetLineNum = currLineNum;
            case Opcodes.ANEWARRAY:  // 189
            case Opcodes.INSTANCEOF:  // 193
            case Opcodes.CHECKCAST:  // 192
                firstLoadedType = null;
                break;
            default:
                break;
        }
    }    
    
    @Override
    public void visitVarInsn(int opcode, int var) {
        switch (opcode) {
            case Opcodes.ILOAD:  // 21 - Load integer.
            case Opcodes.LLOAD:  // 22
            case Opcodes.FLOAD:  // 23
            case Opcodes.DLOAD:  // 24
                firstLoadedType = null;
                break;
            case Opcodes.ALOAD:  // 25 - Load object.
                setFirstLoadedType(localTypes.get(var));
                break;
            case Opcodes.ISTORE:  // 54 - Store integer.
            case Opcodes.LSTORE:  // 55
            case Opcodes.FSTORE:  // 56
                firstLoadedType = null;
                break;
            case Opcodes.ASTORE:  // 58 - Store object.
                // Record the type of the stored variable.
                if (currLineNum == lastRetLineNum && !localTypes.containsKey(opcode)) {
                    localTypes.put(var, lastRetType);
                }
                firstLoadedType = null;
                break;
            case Opcodes.RET:  // 169
                break;
            default:
                break;
        }
    }    
    
    @Override
    public void visitLineNumber(int line, Label start) {
        currLineNum = line;
    }
    
    private void setFirstLoadedType(String type) {
        if (firstLoadedType == null) {
            firstLoadedType = type;
            firstLoadedLineNum = currLineNum;
        }
    }
}
