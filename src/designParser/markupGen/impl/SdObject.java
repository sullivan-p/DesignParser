package designParser.markupGen.impl;

public class SdObject {
    public String objectName;
    public boolean isPreexisting;
    
    public SdObject(String objectName, boolean isPreexisting) {
        this.objectName = objectName;
        this.isPreexisting = isPreexisting;
    }
}
