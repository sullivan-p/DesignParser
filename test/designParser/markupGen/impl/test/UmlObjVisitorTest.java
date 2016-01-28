package designParser.markupGen.impl.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import designParser.asm.visitor.ClassDeclarationVisitor;
import designParser.asm.visitor.ClassFieldVisitor;
import designParser.asm.visitor.ClassMethodVisitor;
import designParser.asm.visitor.ModelBuilderClassVisitor;
import designParser.markupGen.impl.UmlModelVisitor;
import designParser.markupGen.impl.UmlObjVisitor;
import designParser.model.api.IDesignModel;
import designParser.model.impl.DesignModel;

public class UmlObjVisitorTest {
    public static final String EAGER_SINGLETON = "testModels.singleton.EagerSingleton";
    public static final String LAZY_SINGLETON = "testModels.singleton.LazySingleton";
    public static final String CHOCO_BOILER_SINGLETON = "testModels.singleton.ChocolateBoiler";
    public static final String ALMOST_SINGLETON = "testModels.singleton.AlmostSingleton";
    public static final String STEREOTYPE = "\\<\\<singleton\\>\\>";

    @Test
    public void testEager() throws IOException {
        testIsSingleton(EAGER_SINGLETON, true);
    }

    @Test
    public void testLazy() {
        testIsSingleton(LAZY_SINGLETON, true);
    }

    @Test
    public void testChocolate() {
        testIsSingleton(CHOCO_BOILER_SINGLETON, true);
    }

    @Test
    public void testAlmost() {
        testIsSingleton(ALMOST_SINGLETON, false);
    }
    
    private void testIsSingleton(String objName, boolean expected) {
        UmlModelVisitor v = new UmlObjVisitor();
        try {
            IDesignModel m = getDesignModel(objName);
            m.accept(v);
            assertEquals(expected, v.getUmlMarkup().indexOf(STEREOTYPE) > -1);
        } catch (IOException e) {
            fail("Could not read object, " + objName + ".");
        }
    }
    
    /**
     * Get a model for the specified object.
     */
    private IDesignModel getDesignModel(String objName) throws IOException {
        String[] modelInput = { objName };
        IDesignModel designModel = new DesignModel(modelInput);
        
        ModelBuilderClassVisitor decVisitor;
        decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, designModel);
        ModelBuilderClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor);
        ModelBuilderClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
        
        ClassReader reader = new ClassReader(objName);
        reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
        designModel = methodVisitor.getModel();

        return designModel;
    }
}
