package designParser.markupGen.impl;

import designParser.markupGen.api.MarkupGenerator;
import designParser.model.api.IDesignModel;

public class SdGenerator implements MarkupGenerator {
    private IDesignModel model; 
    private StringBuilder stringBuilder;
    private SdObjectVisitor objVisitor;
    private SdMessageVisitor msgVisitor;

    public SdGenerator(IDesignModel model, String mthdClassName, String mthdName,
            String[] mthdParamTypeNames, int callDepth) {
        this.model = model;        
        objVisitor = new SdObjectVisitor(mthdClassName, mthdName, mthdParamTypeNames, callDepth);
        msgVisitor = new SdMessageVisitor(mthdClassName, mthdName, mthdParamTypeNames, callDepth);
        stringBuilder = new StringBuilder();
    }

    @Override
    public String getMarkup() {
        model.accept(objVisitor);
        model.accept(msgVisitor);
        stringBuilder.append(objVisitor.getSdMarkup());
        stringBuilder.append('\n');
        stringBuilder.append(msgVisitor.getSdMarkup());
        return stringBuilder.toString();
    }
}
