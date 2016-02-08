package designParser.manual.test;

import java.io.IOException;

import designParser.framework.api.DesignParserFramework;
import designParser.framework.impl.DesignParserUmlGenerator;

public class Week5AdapterUmlTest {
    public final static String CONFIG = 
            "testModels.adapter.week5.client.App " +
            "testModels.adapter.week5.client.IteratorToEnumerationAdapter " +
            "testModels.adapter.week5.lib.LinearTransformer " + 
            "java.util.Enumeration " +
            "java.util.Iterator";
            
        public static void main(String[] args) throws IOException {
            DesignParserFramework dp = new DesignParserUmlGenerator();
            String markup = dp.process(CONFIG);
            System.out.println(markup);
        }
}
