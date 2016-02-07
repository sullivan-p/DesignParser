package designParser.framework.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import designParser.asm.visitor.ClassDeclarationVisitor;
import designParser.asm.visitor.ClassFieldVisitor;
import designParser.asm.visitor.ClassMethodVisitor;
import designParser.asm.visitor.ModelBuilderClassVisitor;
import designParser.framework.api.DesignParserFramework;
import designParser.markupGen.api.MarkupGenerator;
import designParser.markupGen.impl.UmlGenerator;
import designParser.model.api.IDesignModel;
import designParser.model.impl.DesignModel;
import designParser.patternDet.api.PatternDetector;
import designParser.patternDet.impl.DecoratorDetector;
import designParser.patternDet.impl.SingletonDetector;

public class DesignParserUmlGenerator extends DesignParserFramework {
    private final String MODEL_NAME = "UMLDiagram";

    @Override
    protected String[] parseConfig(String config) {
        return config.split(" ");
    }

    @Override
    protected IDesignModel generateDesignModel(String[] objNames) throws IOException {
        IDesignModel designModel = new DesignModel(objNames);
        for (String className : objNames) {
            ClassReader reader;
            try {
                reader = new ClassReader(className);
            } catch (IOException e) {
                throw new IOException("The class, " + className + " was not found.");
            }
            ModelBuilderClassVisitor decVisitor;
            decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, designModel);
            ModelBuilderClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor);
            ModelBuilderClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);

            reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
            designModel = methodVisitor.getModel();
        }
        return designModel;
    }

    @Override
    protected Collection<PatternDetector> getPatternDetectors() {
        Collection<PatternDetector> patternDetectors = new ArrayList<PatternDetector>();
        patternDetectors.add(new SingletonDetector());
        patternDetectors.add(new DecoratorDetector());
        return patternDetectors;
    }

    @Override
    protected MarkupGenerator getMarkupGenerator(IDesignModel designModel) {
        return new UmlGenerator(MODEL_NAME, designModel);
    }
}
