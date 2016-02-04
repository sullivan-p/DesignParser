package designParser.framework.api;

import java.io.IOException;
import java.util.Collection;

import designParser.markupGen.api.MarkupGenerator;
import designParser.model.api.IDesignModel;
import designParser.patternDet.api.PatternDetector;

public abstract class DesignParserFramework {
    
    public final String process(String config) throws IOException {
        String[] objNames = parseConfig(config);
        IDesignModel designModel = generateDesignModel(objNames);
        for (PatternDetector p : getPatternDetectors()) {
            designModel.accept(p);
        }
        MarkupGenerator markupGenerator = getMarkupGenerator(designModel); 
        String markup = markupGenerator.getMarkup();
        return markup;
    }

    protected abstract String[] parseConfig(String config);
    
    protected abstract IDesignModel generateDesignModel(String[] objNames) throws IOException;
    
    protected abstract Collection<PatternDetector> getPatternDetectors();

    protected abstract MarkupGenerator getMarkupGenerator(IDesignModel designModel);
}