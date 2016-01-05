package designParser.main.impl;

import java.io.IOException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import designParser.asm.visitor.ClassDeclarationVisitor;
import designParser.asm.visitor.ClassFieldVisitor;
import designParser.asm.visitor.ClassMethodVisitor;
import designParser.asm.visitor.ModelBuilderClassVisitor;

public class DesignParser {
    private final static String[] CLASS_NAMES = { "appLauncher.AppLauncher" };
    
    /**
     * Reads in a list of Java Classes and reverse engineers their design.
     *
     * @param args:
     *            the names of the classes, separated by spaces. For example:
     *            java DesignParser java.lang.String
     *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        for (String className : CLASS_NAMES) {
            // ASM's ClassReader does the heavy lifting of parsing the compiled
            // Java class
            ClassReader reader = new ClassReader(className);
            // make class declaration visitor to get superclass and interfaces
            ModelBuilderClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5);
            // DECORATE declaration visitor with field visitor
            ModelBuilderClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor);
            // DECORATE field visitor with method visitor
            ModelBuilderClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
            // TODO: add more DECORATORS here in later milestones to accomplish
            // specific tasks
            // Tell the Reader to use our (heavily decorated) ClassVisitor to
            // visit the class
            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
        }
    }
}