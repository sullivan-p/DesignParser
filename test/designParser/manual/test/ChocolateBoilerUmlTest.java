package designParser.manual.test;

import java.io.IOException;

import designParser.framework.api.DesignParserFramework;
import designParser.framework.impl.DesignParserUmlGenerator;

public class ChocolateBoilerUmlTest {
    public final static String CONFIG = 
        "testModels.singleton.ChocolateBoiler testModels.singleton.ChocolateController";
    
    public static void main(String[] args) throws IOException {
        DesignParserFramework dp = new DesignParserUmlGenerator();
        String markup = dp.process(CONFIG);
        System.out.println(markup);
    }
}
