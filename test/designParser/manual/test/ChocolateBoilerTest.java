package designParser.manual.test;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import designParser.asm.visitor.ClassDeclarationVisitor;
import designParser.asm.visitor.ClassFieldVisitor;
import designParser.asm.visitor.ClassMethodVisitor;
import designParser.asm.visitor.ModelBuilderClassVisitor;
import designParser.markupGen.api.MarkupGenerator;
import designParser.markupGen.impl.UmlGenerator;
import designParser.model.api.IDesignModel;
import designParser.model.impl.DesignModel;

public class ChocolateBoilerTest {
    public final static String MODEL_NAME = "ChocolateBoiler";
    public final static String[] OBJECT_NAMES = { 
            "testModels.singleton.ChocolateBoiler",
            "testModels.singleton.ChocolateController" };
    
    public static void main(String[] args) {
        IDesignModel designModel = new DesignModel(OBJECT_NAMES);
        
        for (String className : OBJECT_NAMES) {
            ModelBuilderClassVisitor decVisitor;
            decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, designModel);
            ModelBuilderClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor);
            ModelBuilderClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
            
            try {
                ClassReader reader = new ClassReader(className);
                reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
                designModel = methodVisitor.getModel();
            } catch (IOException e) {
                System.out.println("Could not read the class, " + className + ".");
                return;
            }
        }

        MarkupGenerator umlGenerator = new UmlGenerator(MODEL_NAME, designModel);
        System.out.println(umlGenerator.getMarkup());
    }
}
