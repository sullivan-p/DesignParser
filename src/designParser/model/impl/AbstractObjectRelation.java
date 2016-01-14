package designParser.model.impl;

import designParser.model.api.IObjectRelation;

public abstract class AbstractObjectRelation implements IObjectRelation {
    private String srcName;
    private String dstName;

    public AbstractObjectRelation(String srcName, String dstName) {
        this.srcName = srcName;
        this.dstName = dstName;
    }

    @Override
    public String getSourceName() {
        return srcName;
    }

    @Override
    public String getDestinationName() {
        return dstName;
    }
}
