package designParser.manual.test;

import java.io.IOException;

import designParser.framework.api.DesignParserFramework;
import designParser.framework.impl.DesignParserUmlGenerator;

public class Week2DecoratorUmlTest {
    public final static String CONFIG = 
        "testModels.decorator.week2.DecryptionInputStream " +
        "testModels.decorator.week2.EncryptionOutputStream " +
        "testModels.decorator.week2.IDecryption " +
        "testModels.decorator.week2.IEncryption " +
        "testModels.decorator.week2.SubstitutionCipher " +
        "testModels.decorator.week2.TextEditorApp " +
        "java.io.InputStream " +
        "java.io.OutputStream " +
        "java.io.FilterInputStream " +
        "java.io.FilterOutputStream";
        
        public static void main(String[] args) throws IOException {
            DesignParserFramework dp = new DesignParserUmlGenerator();
            String markup = dp.process(CONFIG);
            System.out.println(markup);
        }
}
